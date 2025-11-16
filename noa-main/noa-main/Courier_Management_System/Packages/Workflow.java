package Packages;

import javax.swing.*;
import java.awt.*;

public class Workflow {
    private final Registration registration;
    private final Information information;
    private final Payment payment;

    private JFrame frame;
    private CardLayout cards;
    private JPanel cardPanel;

    private InfoPanel infoPanel;              // Step 2: Courier information entry panel
    private PaymentPanel paymentPanel;        // Step 3: Payment method and amount panel

    // Store reference
    public Workflow(Registration registration, Information information, Payment payment) {
        this.registration = registration;
        this.information = information;
        this.payment = payment;
    }

    public void open() {

        frame = new JFrame("MR Courier service");
        frame.setSize(560, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);

        // card panel for switching
        cards = new CardLayout();
        cardPanel = new JPanel(cards);  // holds all workflow panels
        cardPanel.setBackground(Color.BLACK);

        // 1.product select
        ProductSelection prodPanel = new ProductSelection(product -> {
            SwingUtilities.invokeLater(() -> {
                infoPanel.showForProduct(product);
                cards.show(cardPanel, "info");
            });
        });

        // 2.courier info
        InfoPanel infoPanelLocal = new InfoPanel(information, () -> onInfoSubmitted());
        
        // 3.payment panel
        PaymentPanel paymentPanelLocal = new PaymentPanel(information, payment, () -> onPaymentCompleted(), () -> cards.show(cardPanel, "info"));

        // references
        this.infoPanel = infoPanelLocal;
        this.paymentPanel = paymentPanelLocal;

        // add workflow panel to cardlayout

        cardPanel.add(prodPanel, "product");      // 1: Product selection
        cardPanel.add(infoPanelLocal, "info");    // 2: Courier info
        cardPanel.add(paymentPanelLocal, "payment");  // 3: Payment

        frame.add(cardPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void onInfoSubmitted() {
        
        // Check if user went back to product selection
        if (information.productName == null) {
            cards.show(cardPanel, "product");  // show step 1 again
            return;  // Exit
        }
        

        // Product details complete
        paymentPanel.refresh();
        cards.show(cardPanel, "payment");    // step 3 (payment panel)
    }

    private void onPaymentCompleted() {

        DeliveryPanel del = new DeliveryPanel(
            registration,      // User data
            information,       // Order data
            payment,          // Payment data
            () -> frame.dispose()  // When done, close workflow
        );
        
        // add delivery panel to card container
        cardPanel.add(del, "delivery");

        cards.show(cardPanel, "delivery");
    }
}
