package Packages;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class DeliveryPanel extends JPanel {
    private Registration registration;
    private Information information;
    private Payment payment;
    private Runnable onFinish;

    public DeliveryPanel(Registration registration, Information information, Payment payment, Runnable onFinish) {
        this.registration = registration;
        this.information = information;
        this.payment = payment;
        this.onFinish = onFinish;

        setLayout(new BorderLayout()); setBackground(Color.BLACK);
        Font labelFont = new Font("Courier", Font.BOLD, 14);

        int days = ThreadLocalRandom.current().nextInt(1,8);
        JLabel label = new JLabel("Your product will reach the destination in " + days + " day" + (days>1?"s.":"."));
        label.setHorizontalAlignment(SwingConstants.CENTER); label.setForeground(Color.GREEN); label.setFont(labelFont);
        add(label, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout()); btns.setBackground(Color.BLACK);
        JButton feedbackBtn = new JButton("Feedback"); feedbackBtn.setBackground(Color.BLACK); feedbackBtn.setForeground(Color.GREEN);
        JButton finishBtn = new JButton("Finish"); finishBtn.setBackground(Color.BLACK); finishBtn.setForeground(Color.GREEN);
        btns.add(feedbackBtn); btns.add(finishBtn);
        add(btns, BorderLayout.SOUTH);

        feedbackBtn.addActionListener(e -> {
            Feedback fb = new Feedback(registration, information, payment);
            fb.FB();
        });

        finishBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you! ", "Done", JOptionPane.INFORMATION_MESSAGE);
            onFinish.run();
        });
    }
}
