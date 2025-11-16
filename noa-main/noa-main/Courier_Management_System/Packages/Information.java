package Packages;
import javax.swing.*;
import java.awt.*;

public class Information extends BillReport {
    String a;              // Destination
    int b;                 // Amount
    int T;                 // Total bill
    String productName;    // Product name
    int quantity = 1;      // quantity for item-based products
    String bookGenre;      // genre when product is a book

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public void sendInfo() {
        if (productName == null || productName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ Please select a product first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    // Modal dialog setup
    JDialog dialog = new JDialog((Frame) null, "Courier Information", true);
    dialog.setSize(400, 300);
    dialog.setLayout(new GridLayout(5, 1, 10, 10));
    dialog.getContentPane().setBackground(Color.BLACK);
    dialog.setForeground(Color.GREEN);
        // Destination input
        JLabel labelDest = new JLabel("Enter Destination Address:");
        labelDest.setForeground(Color.GREEN);
        labelDest.setFont(new Font("Courier", Font.BOLD, 14));

        JTextField destField = new JTextField();
        destField.setBackground(Color.BLACK);
        destField.setForeground(Color.GREEN);
        destField.setFont(new Font("Courier", Font.PLAIN, 14));

        // Weight input
        JLabel labelWeight = new JLabel("Enter Product Weight (KG):");
        labelWeight.setForeground(Color.GREEN);
        labelWeight.setFont(new Font("Courier", Font.BOLD, 14));

        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
        JComponent spinnerEditor = weightSpinner.getEditor();
        if (spinnerEditor instanceof JSpinner.DefaultEditor) {
            JTextField spinnerText = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
            spinnerText.setBackground(Color.BLACK);
            spinnerText.setForeground(Color.GREEN);
            spinnerText.setFont(new Font("Courier", Font.PLAIN, 14));
        }

        // Submit button
        JButton submitButton = new JButton("Submit Information");
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.GREEN);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Courier", Font.BOLD, 14));

        // Action on button click
    submitButton.addActionListener(e -> {
        a = destField.getText().trim();
        b = (int) weightSpinner.getValue();

        if (a.isEmpty()) {
        JOptionPane.showMessageDialog(dialog, "❌ Please enter a valid destination!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
        }

        calculateTotal();

        JOptionPane.showMessageDialog(dialog,
            "✅ Information Submitted!\n\n" +
                "Product: " + productName + "\n" +
                "Destination: " + a + "\n" +
                "Weight: " + b + " KG\n" +
                "Total Bill: " + T + " TK only (BDT) with delivery charge",
            "Confirmation",
            JOptionPane.INFORMATION_MESSAGE);

        dialog.dispose();
    });

        // Add components
        dialog.add(labelDest);
        dialog.add(destField);
        dialog.add(labelWeight);
        dialog.add(weightSpinner);
        dialog.add(submitButton);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void calculateTotal() {
        if (productName == null) {
            T = 0;
            return;
        }
        String key = productName.toLowerCase();

        java.util.function.Predicate<String[]> containsAny = (arr) -> {
            for (String s : arr) {
                if (key.contains(s)) return true;
            }
            return false;
        };

        // electronics pricing
        if (containsAny.test(new String[]{"electronic", "electronic -", "laptop", "mobile", "phone", "tablet", "camera", "headphone"})) {
            int pricePerItem;
            if (key.contains("laptop")) pricePerItem = 40000;
            else if (key.contains("mobile") || key.contains("phone")) pricePerItem = 20000;
            else if (key.contains("tablet")) pricePerItem = 15000;
            else if (key.contains("camera")) pricePerItem = 25000;
            else if (key.contains("headphone")) pricePerItem = 2000;
            else pricePerItem = 20000;
            T = (quantity * pricePerItem) + 100;

        // books
        } else if (key.contains("book")) {
            int pricePerBook = 200;
            if (bookGenre != null) {
                String g = bookGenre.toLowerCase();
                if (g.contains("fiction")) pricePerBook = 250;
                else if (g.contains("non")) pricePerBook = 300;
                else if (g.contains("academic")) pricePerBook = 500;
                else if (g.contains("comic") || g.contains("comics")) pricePerBook = 150;
                else pricePerBook = 200;
            }
            T = (quantity * pricePerBook) + 100;

        // cosmetics
        } else if (containsAny.test(new String[]{"cosmetic", "cosmetics", "foundation", "lipstick", "mascara", "eyeliner", "blush", "highlighter", "eyeshadow"})) {
            int pricePerItem = 400;
            if (key.contains("foundation")) pricePerItem = 800;
            else if (key.contains("lipstick")) pricePerItem = 300;
            else if (key.contains("mascara")) pricePerItem = 350;
            else if (key.contains("eyeliner")) pricePerItem = 200;
            else if (key.contains("blush")) pricePerItem = 250;
            else if (key.contains("highlighter")) pricePerItem = 450;
            else if (key.contains("eyeshadow")) pricePerItem = 350;
            T = (quantity * pricePerItem) + 100;

        // garments
        } else if (containsAny.test(new String[]{"garment", "garments", "shirt", "jeans", "trousers", "jacket", "hoodie", "shorts", "sweater"})) {
            int pricePerItem = 800;
            if (key.contains("shirt")) pricePerItem = 700;
            else if (key.contains("jeans")) pricePerItem = 1200;
            else if (key.contains("trouser") || key.contains("trousers")) pricePerItem = 900;
            else if (key.contains("jacket")) pricePerItem = 2500;
            else if (key.contains("hoodie")) pricePerItem = 1500;
            else if (key.contains("shorts")) pricePerItem = 500;
            else if (key.contains("sweater")) pricePerItem = 1200;
            T = (quantity * pricePerItem) + 100;

        // furniture
        } else if (containsAny.test(new String[]{"furniture", "bed", "chair", "table", "sofa", "bookshelf", "dressing", "dining"})) {
            int pricePerItem = 10000;
            if (key.contains("bed")) pricePerItem = 30000;
            else if (key.contains("chair")) pricePerItem = 2000;
            else if (key.contains("table")) pricePerItem = 7000;
            else if (key.contains("sofa")) pricePerItem = 25000;
            else if (key.contains("bookshelf")) pricePerItem = 8000;
            else if (key.contains("dressing")) pricePerItem = 15000;
            else if (key.contains("dining")) pricePerItem = 35000;
            T = (quantity * pricePerItem) + 200; // larger delivery handled separately

        // medicine
        } else if (key.contains("medicine")) {
            int pricePerUnit = 50;
            T = (quantity * pricePerUnit) + 50;


        } else {
            T = (b * 100) + 1000;
        }
    }

    public int T() {
        return T;
    }

    public void setQuantity(int q) { this.quantity = q; }
    public int getQuantity() { return quantity; }
    public void setBookGenre(String g) { this.bookGenre = g; }
    public String getBookGenre() { return bookGenre; }
}
