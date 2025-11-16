package Packages;

public class Payment {
    
    private int payment;
    private String paymentMethod;
    private Information info;

    public Payment(Information info) {
        this.info = info;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }


    public void recordPayment(String method, int amount) {
        this.paymentMethod = method;  // Store payment method
        this.payment = amount;         // Store amount paid
    }
    
}
