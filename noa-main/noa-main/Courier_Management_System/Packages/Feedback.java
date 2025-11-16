package Packages;
import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
public class Feedback  {
    private Registration registration;
    private Information information;
    private Payment payment;
    private static final String FEEDBACK_FILE = "feedback.txt";

    public Feedback(Registration registration, Information information, Payment payment) {
        this.registration = registration;
        this.information = information;
        this.payment = payment;
    }

    public String FB() {
        // check previous
        if (registration == null ||
                information == null || information.productName == null || information.productName.isEmpty() ||
                payment == null || payment.getPaymentMethod() == null) {

            JOptionPane.showMessageDialog(null,
                    "❌ Please complete registration, courier info, and payment before giving feedback.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    JDialog dialog = new JDialog((java.awt.Frame) null, "Customer Feedback", true);
    dialog.setSize(400, 250);
    dialog.setLayout(new GridLayout(3, 1, 10, 10));
    dialog.getContentPane().setBackground(Color.BLACK);

    JLabel label = new JLabel("Please give feedback about our Courier System:");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setForeground(Color.GREEN);
    label.setFont(new java.awt.Font("Courier", java.awt.Font.BOLD, 14));

    String[] feedbackOptions = {"Excellent", "Very Good", "Good", "Not Bad", "Bad", "Disappointed"};
    JComboBox<String> feedbackBox = new JComboBox<>(feedbackOptions);
    feedbackBox.setBackground(Color.BLACK);
    feedbackBox.setForeground(Color.GREEN);

    JButton submitButton = new JButton("Submit Feedback");
    submitButton.setBackground(Color.BLACK);
    submitButton.setForeground(Color.GREEN);

    submitButton.addActionListener(e -> {
        String feedbackText = (String) feedbackBox.getSelectedItem();
        saveFeedback(feedbackText);
        double avg = calculateAverageFeedback();
        JOptionPane.showMessageDialog(dialog, "✅ Thank you for your feedback: " + feedbackText
            + "\nCurrent average rating: " + String.format("%.2f", avg));
        generatePOSPDF(feedbackText, avg);
        dialog.dispose();
    });

    dialog.add(label);
    dialog.add(feedbackBox);
    dialog.add(submitButton);
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
    return null;
    }
    // save feed in file
    private void saveFeedback(String feedback) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            bw.write(feedback);
            bw.newLine();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
    // avg calc
    private double calculateAverageFeedback() {
        int sum = 0, count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                sum += mapFeedbackToScore(line.trim());
                count++;
            }
        } catch (IOException ex) { ex.printStackTrace(); }
        return count == 0 ? 0 : (double) sum / count;
    }
    // num based on review
    private int mapFeedbackToScore(String feedback) {
        return switch (feedback) {
            case "Excellent" -> 5;
            case "Very Good" -> 4;
            case "Good" -> 3;
            case "Not Bad" -> 2;
            case "Bad" -> 1;
            case "Disappointed" -> 0;
            default -> 0;
        };
    }
    //pdf
    private void generatePOSPDF(String feedbackText, double avgRating) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = "OrderHistory_" + timestamp + ".pdf";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.COURIER, 14, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.COURIER, 10, com.itextpdf.text.Font.NORMAL);
            Paragraph header = new Paragraph("MR COURIER MANAGEMENT SYSTEM\n", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("======================================================================================\n", normalFont));
// cus detail
            if (registration != null) {
                document.add(new Paragraph("Customer   : " + registration.getName(), normalFont));
                document.add(new Paragraph("Contact    : " + registration.getContactNo(), normalFont));
                document.add(new Paragraph("Email      : " + registration.getEmail(), normalFont));
                document.add(new Paragraph("Address    : " + registration.getAddress(), normalFont));
            } else {
                document.add(new Paragraph("Customer Info: [Unavailable]", normalFont));
            }

            document.add(new Paragraph("--------------------------------------------------------------------------------------\n", normalFont));
// delivery detail
            if (information != null) {
                document.add(new Paragraph("Destination: " + information.a, normalFont));
                document.add(new Paragraph("Product    : " + information.productName, normalFont));
                // Show quantity for item-based products, weight for others
                String key = information.productName != null ? information.productName.toLowerCase() : "";
                if (key.contains("electronic") || key.contains("cosmetic") || key.contains("cosmetics") || key.contains("garment") || key.contains("garments") || key.contains("book") || key.contains("medicine") || key.contains("furniture")) {
                    document.add(new Paragraph("Amount     : " + information.quantity, normalFont));
                } else {
                    document.add(new Paragraph("Weight     : " + information.b + " KG", normalFont));
                }
                document.add(new Paragraph("Bill       : " + information.T + " TK", normalFont));
            } else {
                document.add(new Paragraph("Courier Info: [Unavailable]", normalFont));
            }

            if (payment != null) {
                document.add(new Paragraph("Payment    : " + payment.getPaymentMethod(), normalFont));
            } else {
                document.add(new Paragraph("Payment    : [Unavailable]", normalFont));
            }

            document.add(new Paragraph("--------------------------------------------------------------------------------------\n", normalFont));
// feed,rate,time
            document.add(new Paragraph("Feedback       : " + feedbackText, normalFont));
            document.add(new Paragraph("Average Rating : " + String.format("%.2f", avgRating), normalFont));

            LocalDateTime dateNow = LocalDateTime.now();
            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedTimestamp = dateNow.format(dtFormat);
            document.add(new Paragraph("Time           : " + formattedTimestamp + "\n", normalFont));

            document.add(new Paragraph("======================================================================================\n", normalFont));

            Paragraph f = new Paragraph("THANK YOU FOR CHOOSING MR COURIER", headerFont);
            f.setAlignment(Element.ALIGN_CENTER);
            document.add(f);

            document.close();
// pdf auto open
            File pdfFile = new File(fileName);
            if (pdfFile.exists() && Desktop.isDesktopSupported()) Desktop.getDesktop().open(pdfFile);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error generating PDF:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}
