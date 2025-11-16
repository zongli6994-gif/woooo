package Packages;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Complain extends JPanel {
    private Registration registration;
    private Runnable onSkip;

    public Complain(Registration registration, Runnable onSkip) {
        this.registration = registration;
        this.onSkip = onSkip;

        // panel
        setLayout(new BorderLayout(12, 12));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        // title
        JLabel title = new JLabel("File a Complaint", SwingConstants.CENTER);
        title.setForeground(Color.GREEN);
        title.setFont(new Font("Courier", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.BOTH;   
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        Font labelFont = new Font("Courier", Font.BOLD, 14);
        Font fieldFont = new Font("Courier", Font.PLAIN, 14);


        JLabel instrLabel = new JLabel("Please describe your complaint about the courier service:");
        instrLabel.setForeground(Color.GREEN);
        instrLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weighty = 0.0;
        center.add(instrLabel, gbc);

        // text area
        JTextArea complaintArea = new JTextArea(8, 50);
        complaintArea.setBackground(Color.BLACK);
        complaintArea.setForeground(Color.GREEN);
        complaintArea.setFont(fieldFont);
        complaintArea.setLineWrap(true);
        complaintArea.setWrapStyleWord(true);
        complaintArea.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));

        JScrollPane scrollPane = new JScrollPane(complaintArea);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        center.add(scrollPane, gbc);

        add(center, BorderLayout.CENTER);  // Add entire form to middle of panel

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        bottom.setBackground(Color.BLACK);

        // skip
        JButton skipBtn = new JButton("Skip (No Complaint)");
        skipBtn.setBackground(Color.BLACK);
        skipBtn.setForeground(Color.GREEN);
        skipBtn.setFont(labelFont);
        skipBtn.setPreferredSize(new Dimension(180, 40));

        //submit
        JButton submitBtn = new JButton("Submit Complaint");
        submitBtn.setBackground(Color.BLACK);
        submitBtn.setForeground(Color.GREEN);
        submitBtn.setFont(labelFont);
        submitBtn.setPreferredSize(new Dimension(180, 40));

        bottom.add(skipBtn);
        bottom.add(submitBtn);
        add(bottom, BorderLayout.SOUTH);

        // skip action
        skipBtn.addActionListener(e -> onSkip.run());  //callback to move to workflow

        // submit action
        submitBtn.addActionListener(e -> {
            String complaint = complaintArea.getText().trim();

            if (complaint.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this, 
                    "Please enter a complaint or click Skip.", 
                    "Empty Complaint", 
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // save complain
            if (storeComplaint(complaint)) {

                JOptionPane.showMessageDialog(
                    this, 
                    "Thank you for your feedback!\nYour complaint has been recorded.", 
                    "Complaint Submitted", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                onSkip.run();  // Callback to proceed to main workflow
            } else {
                JOptionPane.showMessageDialog(
                    this, 
                    "Error saving complaint. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    private boolean storeComplaint(String complaint) {
        try {
            String filename = "complaints.txt";
            String userName = registration.getName();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            

            String entry = String.format("User: %s\nComplaint: %s\nTime: [%s]\n%s\n", userName , complaint , timestamp,   "=".repeat(60));

            FileWriter fw = new FileWriter(filename, true);
            fw.write(entry);
            fw.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

