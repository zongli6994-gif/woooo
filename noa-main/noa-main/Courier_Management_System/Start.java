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
                // After successful login, present two choices:
                //  - File a complaint
                //  - Start courier 
                JDialog options = new JDialog(frame, "Welcome", true);
                options.setSize(360, 200);
                options.setLayout(new BorderLayout(10, 10));
                options.getContentPane().setBackground(Color.BLACK);

                JLabel msg = new JLabel("Choose an action:", SwingConstants.CENTER);
                msg.setForeground(Color.GREEN);
                msg.setFont(new Font("Courier", Font.BOLD, 14));
                options.add(msg, BorderLayout.NORTH);

                JPanel btnPanel = new JPanel(new GridLayout(1, 2, 12, 12));
                btnPanel.setBackground(Color.BLACK);

                JButton complainBtn = new JButton("File Complaint");
                JButton startOrderBtn = new JButton("Start Order");
                JButton[] optBtns = {complainBtn, startOrderBtn};
                for (JButton b : optBtns) {
                    b.setBackground(Color.BLACK);
                    b.setForeground(Color.GREEN);
                    b.setFocusPainted(false);
                    b.setFont(new Font("Courier", Font.BOLD, 13));
                    btnPanel.add(b);
                }

                // Open complaint window then workflow
                complainBtn.addActionListener(ev -> {
                    options.dispose();
                    showComplaintWindow(r, c, z);
                });

                // Directly start the workflow (product selection -> info -> payment)
                startOrderBtn.addActionListener(ev -> {
                    options.dispose();
                    Workflow mw = new Workflow(r, c, z);
                    mw.open();
                });

                options.add(btnPanel, BorderLayout.CENTER);
                
                options.setLocationRelativeTo(null);
                options.setVisible(true);
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
