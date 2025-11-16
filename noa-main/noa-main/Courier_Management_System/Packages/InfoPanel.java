package Packages;

import javax.swing.*;
import java.awt.*;
public class InfoPanel extends JPanel {
    private Information information;
    private Runnable onSubmitted;
    private String currentProduct;
    private JPanel dynamicArea;
    public InfoPanel(Information information, Runnable onSubmitted) {
        this.information = information;
        this.onSubmitted = onSubmitted;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        JLabel title = new JLabel("Courier Information", SwingConstants.CENTER);
        title.setForeground(Color.GREEN);
        title.setFont(new Font("Courier", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        dynamicArea = new JPanel();
        dynamicArea.setBackground(Color.BLACK);
        add(dynamicArea, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(Color.BLACK);
//button
        JButton submit = new JButton("Submit Info");
        JButton back = new JButton("Back");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.GREEN);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.GREEN);
        bottom.add(back);
        bottom.add(submit);
        add(bottom, BorderLayout.SOUTH);
        back.addActionListener(e -> {

            information.productName = null;
            onSubmitted.run();
        });

        submit.addActionListener(e -> {
            if (!applyInputsToModel()) return;
            onSubmitted.run();
        });
    }
    public void showForProduct(String product) {
        this.currentProduct = product;
        rebuildUIForProduct(product);
    }

    private void rebuildUIForProduct(String product) {
        dynamicArea.removeAll();
        dynamicArea.setLayout(new BorderLayout(10,10));


        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Courier", Font.BOLD, 16);
        Font fieldFont = new Font("Courier", Font.PLAIN, 16);

        JLabel destLabel = new JLabel("Destination Address:"); destLabel.setForeground(Color.GREEN); destLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.weightx = 0.0; left.add(destLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField destField = new JTextField(24);
        destField.setBackground(Color.BLACK); destField.setForeground(Color.GREEN); destField.setFont(fieldFont);
        destField.setPreferredSize(new Dimension(360, 30));
        left.add(destField, gbc);

        gbc.gridy++;

        final String key = product.toLowerCase();

        // common holder for quantity/weight controls
        JComponent primaryControl = null;
        JSpinner qtySpinner = null;
        JComboBox<String> deviceBox = null;
        JComboBox<String> genreBox = null;
        JComboBox<String> cosBox = null;
        JComboBox<String> garBox = null;
        JComboBox<String> furnBox = null;

        if (key.contains("medicine")) {
            JLabel amtLabel = new JLabel("Amount (units):"); amtLabel.setForeground(Color.GREEN); amtLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(amtLabel, gbc);
            gbc.gridx = 1;
            JSpinner amtSpinner = new JSpinner(new SpinnerNumberModel(1,1,100,1));
            JComponent amtEditor = ((JSpinner.DefaultEditor)amtSpinner.getEditor());
            amtEditor.setFont(fieldFont);
            ((JSpinner.DefaultEditor)amtSpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)amtSpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            amtSpinner.setPreferredSize(new Dimension(140, 30));
            primaryControl = amtSpinner; left.add(amtSpinner, gbc);
            dynamicArea.putClientProperty("weightSpinner", amtSpinner);

        } else if (key.contains("electronic")) {
            JLabel deviceLabel = new JLabel("Device:"); deviceLabel.setForeground(Color.GREEN); deviceLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(deviceLabel, gbc);
            gbc.gridx = 1;
            String[] devices = {"Laptop","Mobile Phone","Tablet","Camera","Headphones"};
            deviceBox = new JComboBox<>(devices); deviceBox.setBackground(Color.BLACK); deviceBox.setForeground(Color.GREEN); deviceBox.setFont(fieldFont);
            deviceBox.setPreferredSize(new Dimension(300, 30));
            left.add(deviceBox, gbc);

            gbc.gridy++;
            JLabel qtyLabel = new JLabel("Quantity (1-5):"); qtyLabel.setForeground(Color.GREEN); qtyLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(qtyLabel, gbc);
            gbc.gridx = 1;
            qtySpinner = new JSpinner(new SpinnerNumberModel(1,1,5,1));
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            qtySpinner.setPreferredSize(new Dimension(120, 30));
            left.add(qtySpinner, gbc);
            dynamicArea.putClientProperty("deviceBox", deviceBox);
            dynamicArea.putClientProperty("qtySpinner", qtySpinner);
            dynamicArea.putClientProperty("weightSpinner", new JSpinner(new SpinnerNumberModel(1,1,500,1)));

        } else if (key.contains("book")) {
            JLabel genreLabel = new JLabel("Genre:"); genreLabel.setForeground(Color.GREEN); genreLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(genreLabel, gbc);
            gbc.gridx = 1;
            String[] genres = {"Fiction","Non-Fiction","Academic","Comics","Other"};
            genreBox = new JComboBox<>(genres); genreBox.setBackground(Color.BLACK); genreBox.setForeground(Color.GREEN); genreBox.setFont(fieldFont);
            genreBox.setPreferredSize(new Dimension(300, 30));
            left.add(genreBox, gbc);
            gbc.gridy++;
            JLabel qtyLabel = new JLabel("Quantity:"); qtyLabel.setForeground(Color.GREEN); qtyLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(qtyLabel, gbc);
            gbc.gridx = 1; qtySpinner = new JSpinner(new SpinnerNumberModel(1,1,20,1));
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            qtySpinner.setPreferredSize(new Dimension(120, 30));
            left.add(qtySpinner, gbc);
            dynamicArea.putClientProperty("bookGenreBox", genreBox);
            dynamicArea.putClientProperty("qtySpinner", qtySpinner);
            dynamicArea.putClientProperty("weightSpinner", new JSpinner(new SpinnerNumberModel(1,1,500,1)));

        } else if (key.contains("cosmetic")) {
            JLabel cosLabel = new JLabel("Item:"); cosLabel.setForeground(Color.GREEN); cosLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(cosLabel, gbc);
            gbc.gridx = 1;
            String[] cosmetics = {"Foundation","Lipstick","Mascara","Eyeliner","Blush","Highlighter","Eyeshadow"};
            cosBox = new JComboBox<>(cosmetics); cosBox.setBackground(Color.BLACK); cosBox.setForeground(Color.GREEN); cosBox.setFont(fieldFont);
            cosBox.setPreferredSize(new Dimension(300, 30));
            left.add(cosBox, gbc);
            gbc.gridy++;
            JLabel qtyLabel = new JLabel("Quantity:"); qtyLabel.setForeground(Color.GREEN); qtyLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(qtyLabel, gbc);
            gbc.gridx = 1; qtySpinner = new JSpinner(new SpinnerNumberModel(1,1,10,1));
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            qtySpinner.setPreferredSize(new Dimension(120, 30));
            left.add(qtySpinner, gbc);
            dynamicArea.putClientProperty("cosBox", cosBox);
            dynamicArea.putClientProperty("qtySpinner", qtySpinner);
            dynamicArea.putClientProperty("weightSpinner", new JSpinner(new SpinnerNumberModel(1,1,500,1)));

        } else if (key.contains("garment")) {
            JLabel garLabel = new JLabel("Item:"); garLabel.setForeground(Color.GREEN); garLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(garLabel, gbc);
            gbc.gridx = 1;
            String[] garments = {"Shirt","Jeans","Trousers","Jacket","Hoodie","Shorts","Sweater"};
            garBox = new JComboBox<>(garments); garBox.setBackground(Color.BLACK); garBox.setForeground(Color.GREEN); garBox.setFont(fieldFont);
            garBox.setPreferredSize(new Dimension(300, 30));
            left.add(garBox, gbc);
            gbc.gridy++;
            JLabel qtyLabel = new JLabel("Quantity:"); qtyLabel.setForeground(Color.GREEN); qtyLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(qtyLabel, gbc);
            gbc.gridx = 1; qtySpinner = new JSpinner(new SpinnerNumberModel(1,1,10,1));
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            qtySpinner.setPreferredSize(new Dimension(120, 30));
            left.add(qtySpinner, gbc);
            dynamicArea.putClientProperty("garBox", garBox);
            dynamicArea.putClientProperty("qtySpinner", qtySpinner);
            dynamicArea.putClientProperty("weightSpinner", new JSpinner(new SpinnerNumberModel(1,1,500,1)));
        } else if (key.contains("furniture")) {
            JLabel furnLabel = new JLabel("Item:"); furnLabel.setForeground(Color.GREEN); furnLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(furnLabel, gbc);
            gbc.gridx = 1;
            String[] furns = {"Bed","Chair","Table","Sofa","Bookshelf","Dressing Table","Dining Set"};
            furnBox = new JComboBox<>(furns); furnBox.setBackground(Color.BLACK); furnBox.setForeground(Color.GREEN); furnBox.setFont(fieldFont);
            furnBox.setPreferredSize(new Dimension(300, 30));
            left.add(furnBox, gbc);

            gbc.gridy++;
            JLabel qtyLabel = new JLabel("Quantity:"); qtyLabel.setForeground(Color.GREEN); qtyLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(qtyLabel, gbc);
            gbc.gridx = 1; qtySpinner = new JSpinner(new SpinnerNumberModel(1,1,5,1));
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)qtySpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            qtySpinner.setPreferredSize(new Dimension(120, 30));
            left.add(qtySpinner, gbc);
            dynamicArea.putClientProperty("furnBox", furnBox);
            dynamicArea.putClientProperty("qtySpinner", qtySpinner);
            dynamicArea.putClientProperty("weightSpinner", new JSpinner(new SpinnerNumberModel(1,1,500,1)));

        } else {
            // fallback: weight-based
            JLabel weightLabel = new JLabel("Enter Product Weight (KG):"); weightLabel.setForeground(Color.GREEN); weightLabel.setFont(labelFont);
            gbc.gridx = 0; left.add(weightLabel, gbc);
            gbc.gridx = 1;
            JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(1,1,500,1));
            ((JSpinner.DefaultEditor)weightSpinner.getEditor()).getTextField().setBackground(Color.BLACK);
            ((JSpinner.DefaultEditor)weightSpinner.getEditor()).getTextField().setForeground(Color.GREEN);
            weightSpinner.setPreferredSize(new Dimension(140, 30));
            left.add(weightSpinner, gbc);
            dynamicArea.putClientProperty("weightSpinner", weightSpinner);
        }

        dynamicArea.putClientProperty("destField", destField);
        dynamicArea.add(left, BorderLayout.CENTER);
        dynamicArea.revalidate(); dynamicArea.repaint();
    }

    private boolean applyInputsToModel() {
        JTextField destField = (JTextField) dynamicArea.getClientProperty("destField");
        JSpinner weightSpinner = (JSpinner) dynamicArea.getClientProperty("weightSpinner");
        JSpinner qtySpinner = (JSpinner) dynamicArea.getClientProperty("qtySpinner");
        @SuppressWarnings("unchecked") JComboBox<String> bookGenreBox = (JComboBox<String>) dynamicArea.getClientProperty("bookGenreBox");
        @SuppressWarnings("unchecked") JComboBox<String> deviceBox = (JComboBox<String>) dynamicArea.getClientProperty("deviceBox");
        @SuppressWarnings("unchecked") JComboBox<String> cosBox = (JComboBox<String>) dynamicArea.getClientProperty("cosBox");
        @SuppressWarnings("unchecked")JComboBox<String> garBox = (JComboBox<String>) dynamicArea.getClientProperty("garBox");
        @SuppressWarnings("unchecked")JComboBox<String> furnBox = (JComboBox<String>) dynamicArea.getClientProperty("furnBox");

        if (destField == null || weightSpinner == null) return false;
        String dest = destField.getText();
        if (dest == null || dest.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Destination required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        information.a = dest;
        int bVal = ((Number) weightSpinner.getValue()).intValue();
        information.b = bVal;

        final String key = this.currentProduct.toLowerCase();
        if (key.contains("medicine")) {
            int amt = (int) weightSpinner.getValue();
            information.quantity = amt;
            information.setProductName(this.currentProduct);
        } else if (key.contains("electronic")) {
            if (deviceBox == null || qtySpinner == null) return false;
            String device = (String) deviceBox.getSelectedItem();
            int qty = (int) qtySpinner.getValue();
            information.setProductName("Electronic - " + device);
            information.quantity = qty;
        } else if (key.contains("book")) {
            if (bookGenreBox == null || qtySpinner == null) return false;
            String genre = (String) bookGenreBox.getSelectedItem();
            int qty = (int) qtySpinner.getValue();
            information.setProductName(this.currentProduct);
            information.quantity = qty;
            information.setBookGenre(genre);
        } else if (key.contains("cosmetic")) {
            if (cosBox == null || qtySpinner == null) return false;
            String item = (String) cosBox.getSelectedItem();
            int qty = (int) qtySpinner.getValue();
            information.setProductName("Cosmetics - " + item);
            information.quantity = qty;
        } else if (key.contains("garment") || key.contains("garments")) {
            if (garBox == null || qtySpinner == null) return false;
            String item = (String) garBox.getSelectedItem();
            int qty = (int) qtySpinner.getValue();
            information.setProductName("Garments - " + item);
            information.quantity = qty;
        } else if (key.contains("furniture")) {
            if (furnBox == null || qtySpinner == null) return false;
            String item = (String) furnBox.getSelectedItem();
            int qty = (int) qtySpinner.getValue();
            information.setProductName("Furniture - " + item);
            information.quantity = qty;

        } else {

            information.setProductName(this.currentProduct);
        }

        information.calculateTotal();
        return true;
    }
}
