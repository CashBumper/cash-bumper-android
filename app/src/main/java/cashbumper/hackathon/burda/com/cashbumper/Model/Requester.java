package cashbumper.hackathon.burda.com.cashbumper.Model;

public class Requester extends User {
    public Requester(String id, double latitude, double longitude, int amount, int range, String transactionId, String cardNumber, String expiryMonth, String expiryYear, String cvc) {
        super(id, latitude, longitude, amount, range);
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvc = cvc;
    }

    String transactionId;
    String cardNumber;
    String expiryMonth;
    String expiryYear;
    String cvc;
}
