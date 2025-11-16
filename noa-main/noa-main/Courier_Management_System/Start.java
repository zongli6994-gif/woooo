import Packages.*;
import javax.swing.*;
import java.awt.*;

public class Start {

    public static void main(String[] args) {
        // Main frame for login/register
        JFrame frame = new JFrame("MR World Wide Courier Service");
        frame.setSize(500, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //  login/register buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));  // 2 buttons stacked vertically
        panel.setBackground(Color.BLACK);


        JLabel welcome = new JLabel("<html><center>-----------------------------------------------------------------------------------------<br>WELCOME TO MR WORLD WIDE COURIER SERVICE<br>-----------------------------------------------------------------------------------------</center></html>");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setForeground(Color.GREEN);
        welcome.setFont(new Font("Courier", Font.BOLD, 16));
        welcome.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));

        // Login and Register buttons
        JButton loginBtn = new JButton("Log In");              // For existing users
        JButton registerBtn = new JButton("Register Customer"); // For new users
        JButton[] buttons = {loginBtn, registerBtn};


        for (JButton btn : buttons) {
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.GREEN);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Courier", Font.BOLD, 14));
            panel.add(btn);
        }

        //  objects
        Registration r = new Registration();  // User authentication & registration
        Information c = new Information();    // Order
        Payment z = new Payment(c);           // Payment

        // log in
        loginBtn.addActionListener(e -> {
            boolean ok = r.LogIn();
            if (ok) {

                showComplaintWindow(r, c, z);
            }
        });

        // register  →  workflow
        registerBtn.addActionListener(e -> {
            r.SignUp();
            Workflow mw = new Workflow(r, c, z);
            mw.open();  // Open workflow
        });

        // Assemble main window
        frame.add(welcome, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);  // Show window
    }


     //Show complain panel window

    private static void showComplaintWindow(Registration registration, Information information, Payment payment) {
        // Complaint window frame
        JFrame complaintFrame = new JFrame("File a Complaint");
        complaintFrame.setSize(700, 500);
        complaintFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close only this window
        complaintFrame.setLocationRelativeTo(null);  // Center on screen
        complaintFrame.setBackground(Color.BLACK);

        // Complain panel with callback to proceed after submit/skip
        Complain complaintPanel = new Complain(registration, () -> {
            complaintFrame.dispose();  // Close complaint window
            
            // Open workflow
            Workflow mw = new Workflow(registration, information, payment);
            mw.open();
        });
        complaintFrame.add(complaintPanel);
        complaintFrame.setVisible(true);
    }
}
