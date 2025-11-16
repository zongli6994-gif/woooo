package Packages;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class Registration {

    private String name;
    private String address;
    private String contactNo;
    private String email;
    private String gender;
    private String password;

    // file to save user data
    private static final String USER_FILE = "users.txt";


    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getContactNo() { return contactNo; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public void setEmail(String email) { this.email = email; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPassword(String password) { this.password = password; }

    // sign up
    public void SignUp() {

    JDialog dialog = new JDialog((Frame) null, "Customer Registration", true);
    dialog.setSize(400, 500);

    // gridLayout
    dialog.setLayout(new GridLayout(9, 2, 10, 10));
    dialog.getContentPane().setBackground(Color.BLACK);
        Font labelFont = new Font("Courier", Font.BOLD, 14);
        Font fieldFont = new Font("Courier", Font.PLAIN, 14);

        // input field for user data
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();

        JTextField[] textFields = {nameField, genderField, addressField, contactField, emailField, passwordField, confirmField};
        for (JTextField tf : textFields) {
            tf.setBackground(Color.BLACK);
            tf.setForeground(Color.GREEN);
            tf.setFont(fieldFont);
        }

        // sign up button
        JButton submitButton = new JButton("Sign Up");
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.GREEN);
        submitButton.setFont(labelFont);
        submitButton.setFocusPainted(false);

        // labels
        JLabel[] labels = {
                new JLabel("Name:"), new JLabel("Gender:"), new JLabel("Address:"),
                new JLabel("Contact No:"), new JLabel("Email:"), new JLabel("Password:"),
                new JLabel("Confirm Password:")
        };


        for (JLabel lbl : labels) {
            lbl.setForeground(Color.GREEN);
            lbl.setFont(labelFont);
        }

        // add labels in frame
    dialog.add(labels[0]); dialog.add(nameField);
    dialog.add(labels[1]); dialog.add(genderField);
    dialog.add(labels[2]); dialog.add(addressField);
    dialog.add(labels[3]); dialog.add(contactField);
    dialog.add(labels[4]); dialog.add(emailField);
    dialog.add(labels[5]); dialog.add(passwordField);
    dialog.add(labels[6]); dialog.add(confirmField);
    dialog.add(new JLabel()); dialog.add(submitButton); // empty label for spacing

        // button action
        submitButton.addActionListener(e -> {

            String tempPass = new String(passwordField.getPassword());
            String retype = new String(confirmField.getPassword());

            // basic password checks
            if (tempPass.length() < 8) {
                JOptionPane.showMessageDialog(dialog, "❌ Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!tempPass.equals(retype)) {
                JOptionPane.showMessageDialog(dialog, "❌ Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // collect and trim input values for validation
            String name = nameField.getText().trim();
            String gender = genderField.getText().trim();
            String address = addressField.getText().trim();
            String contact = contactField.getText().trim();
            String email = emailField.getText().trim();

            // name required
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "❌ Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // gender
            if (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
                JOptionPane.showMessageDialog(dialog, "❌ Gender must be either 'Male' or 'Female'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // address - should not contain numbers
            if (address.isEmpty() || address.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(dialog, "❌ Address is required and must not contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // contact 11 digits
            if (!contact.matches("^01[3-9]\\d{8}$")) {
                JOptionPane.showMessageDialog(dialog, "❌ Contact number must be valid and exactly 11 digits (01X-XXXXXXXX format).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // email
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
            if (email.isEmpty() || !email.matches(emailRegex)) {
                JOptionPane.showMessageDialog(dialog, "❌ Please enter a valid email address that ends with .com.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // all validations passed -> store input values
            setName(name);
            setGender(gender);
            setAddress(address);
            setContactNo(contact);
            setEmail(email);
            setPassword(tempPass);

            // save user to file
            saveUser();

            JOptionPane.showMessageDialog(dialog,
                    "🎉 Registration completed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // close registration dialog
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    // 1. LOGIN  an existing customer
    public boolean LogIn() {

        JDialog dialog = new JDialog((Frame) null, "Customer Log In", true);
        dialog.setSize(320, 220);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.getContentPane().setBackground(Color.BLACK);

        // Fonts
        Font labelFont = new Font("Courier", Font.BOLD, 14);
        Font fieldFont = new Font("Courier", Font.PLAIN, 14);

        // Create fields for name and password
        JTextField nameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();


        JTextField[] fields = {nameField, passwordField};
        for (JTextField f : fields) {
            f.setBackground(Color.BLACK);
            f.setForeground(Color.GREEN);
            f.setFont(fieldFont);
        }

        // Create and style labels
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.GREEN);
        nameLabel.setFont(labelFont);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.GREEN);
        passLabel.setFont(labelFont);

        // Create and style login button
        JButton loginBtn = new JButton("Log In");
        loginBtn.setBackground(Color.BLACK);
        loginBtn.setForeground(Color.GREEN);
        loginBtn.setFont(labelFont);
        loginBtn.setFocusPainted(false);

        // Add all components to the dialog
        dialog.add(nameLabel); dialog.add(nameField);
        dialog.add(passLabel); dialog.add(passwordField);
        dialog.add(new JLabel()); dialog.add(loginBtn);

        final boolean[] success = {false};


        loginBtn.addActionListener(e -> {
            String uname = nameField.getText().trim(); // get entered name
            String pass = new String(passwordField.getPassword()); // get entered password

            // validate by checking the user file
            if (checkUser(uname, pass)) {
                JOptionPane.showMessageDialog(dialog, "✅ Login Successful!");
                success[0] = true;
                dialog.dispose(); // close login dialog
            } else {
                JOptionPane.showMessageDialog(dialog, "❌ Invalid Name or Password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return success[0];
    }


    // SAVE USER
    private void saveUser() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            // Save in one line: name|gender|address|contact|email|password
            writer.write(name + "|" + gender + "|" + address + "|" + contactNo + "|" + email + "|" + password);
            writer.newLine(); // move to next line for next user
        } catch (IOException e) {
            e.printStackTrace(); // print error if file writing fails
        }
    }

    // check user
    private boolean checkUser(String uname, String pass) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Split each line into parts using "|" separator
                String[] parts = line.split("\\|");

                // Compare username and password
                if (parts[0].equals(uname) && parts[5].equals(pass)) {
                    // If found, load the data into current object
                    setName(parts[0]);
                    setGender(parts[1]);
                    setAddress(parts[2]);
                    setContactNo(parts[3]);
                    setEmail(parts[4]);
                    setPassword(parts[5]);
                    return true; // valid user found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // no user matched
    }}

