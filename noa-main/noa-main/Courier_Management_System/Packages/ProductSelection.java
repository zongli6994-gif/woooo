package Packages;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ProductSelection extends JPanel {

    public ProductSelection(Consumer<String> onSelected) {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);


        Font labelFont = new Font("Courier", Font.BOLD, 14);
        Font fieldFont = new Font("Courier", Font.PLAIN, 14);

        // label
        JLabel productLabel = new JLabel("Select product to courier:", SwingConstants.CENTER);
        productLabel.setForeground(Color.GREEN);
        productLabel.setFont(labelFont);
        add(productLabel, BorderLayout.NORTH);


        String[] products = {"Medicine", "Cosmetics", "Electronic Device", "Garments Product", "Furniture Product", "Book Materials", "Others"};
        JComboBox<String> productBox = new JComboBox<>(products);
        productBox.setBackground(Color.BLACK);
        productBox.setForeground(Color.GREEN);
        productBox.setFont(fieldFont);


        JPanel center = new JPanel(new FlowLayout());
        center.setBackground(Color.BLACK);
        center.add(productBox);
        add(center, BorderLayout.CENTER);


        JButton next = new JButton("Next: Courier Info");
        next.setBackground(Color.BLACK);
        next.setForeground(Color.GREEN);
        next.setFont(labelFont);
        next.setFocusPainted(false);
        add(next, BorderLayout.SOUTH);


        next.addActionListener(e -> {

            String selected = (String) productBox.getSelectedItem();
            

            if ("Others".equals(selected)) {
                String custom = JOptionPane.showInputDialog(
                    SwingUtilities.getWindowAncestor(this),
                    "Enter the name of the product:",
                    "Custom Product",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (custom == null || custom.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Product name required.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                selected = custom.trim();
            }
            // Pass selected product name back to Workflow
            onSelected.accept(selected);
        });
    }
}
