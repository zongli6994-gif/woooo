package Packages;

import javax.swing.*;
import java.awt.*;

 class PaymentPanel extends JPanel {
    private Information information;
    private Payment payment;
    private Runnable onPaid;
    private Runnable onBack;

    private JLabel billLabel;
    private JComboBox<String> methodBox;
    private JTextField amountField;
    private JLabel productLabel;
    private JLabel qtyLabel;

    public PaymentPanel(Information information, Payment payment, Runnable onPaid, Runnable onBack) {
        this.information = information;
        this.payment = payment;
        this.onPaid = onPaid;
        this.onBack = onBack;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        Font labelFont = new Font("Courier", Font.BOLD, 16);
        Font fieldFont = new Font("Courier", Font.PLAIN, 16);

        billLabel = new JLabel("Your total bill is: 0 TK", SwingConstants.CENTER);
        billLabel.setForeground(Color.GREEN); billLabel.setFont(new Font("Courier", Font.BOLD, 18));
        productLabel = new JLabel("Product: -"); productLabel.setForeground(Color.GREEN); productLabel.setFont(labelFont);
        qtyLabel = new JLabel("Quantity: -"); qtyLabel.setForeground(Color.GREEN); qtyLabel.setFont(labelFont);

        JLabel methodLabel = new JLabel("Select Payment Method:"); methodLabel.setForeground(Color.GREEN); methodLabel.setFont(labelFont);
        String[] methods = {"Bkash","Nogod","Rocket","Cash"};
        methodBox = new JComboBox<>(methods); methodBox.setBackground(Color.BLACK); methodBox.setForeground(Color.GREEN); methodBox.setFont(fieldFont);
        methodBox.setPreferredSize(new Dimension(320, 34));

        JLabel amountLabel = new JLabel("Enter Payment Amount:"); amountLabel.setForeground(Color.GREEN); amountLabel.setFont(labelFont);
        amountField = new JTextField(); amountField.setBackground(Color.BLACK); amountField.setForeground(Color.GREEN); amountField.setFont(fieldFont);
        amountField.setPreferredSize(new Dimension(320, 34));

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0)); btns.setBackground(Color.BLACK);
        JButton back = new JButton("Back"); back.setBackground(Color.BLACK); back.setForeground(Color.GREEN);
        JButton payBtn = new JButton("Pay Now"); payBtn.setBackground(Color.BLACK); payBtn.setForeground(Color.GREEN);
        btns.add(back); btns.add(payBtn);

        // Center align components and add vertical spacing for clarity
        productLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qtyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        billLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        methodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        methodBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        btns.setAlignmentX(Component.CENTER_ALIGNMENT);

        methodBox.setMaximumSize(new Dimension(340, 36));
        amountField.setMaximumSize(new Dimension(340, 36));

        back.setPreferredSize(new Dimension(120, 36));
        payBtn.setPreferredSize(new Dimension(140, 36));

        add(productLabel);
        add(Box.createRigidArea(new Dimension(0,6)));
        add(qtyLabel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(billLabel);
        add(Box.createRigidArea(new Dimension(0,12)));
        add(methodLabel);
        add(Box.createRigidArea(new Dimension(0,6)));
        add(methodBox);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(amountLabel);
        add(Box.createRigidArea(new Dimension(0,6)));
        add(amountField);
        add(Box.createRigidArea(new Dimension(0,12)));
        add(btns);

        back.addActionListener(e -> onBack.run());

        payBtn.addActionListener(e -> {
            String method = (String) methodBox.getSelectedItem();
            int expected = information.T();
            int paid;
            try { paid = Integer.parseInt(amountField.getText().trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid input. Payment must be a number.", "Error", JOptionPane.ERROR_MESSAGE); return; }
            if (paid == expected) {
                payment.recordPayment(method, paid);
                JOptionPane.showMessageDialog(this, "Payment Completed using " + method + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                onPaid.run();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid payment amount! Please pay the full amount: " + expected + " TK", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void refresh() {
        int expected = information.T();
        billLabel.setText("Your total bill is: " + expected + " TK");

        String prod = (information.productName == null ? "-" : information.productName);
        productLabel.setText("Product: " + prod);
        qtyLabel.setText("Quantity: " + information.getQuantity());
        amountField.setText("");
    }
}
