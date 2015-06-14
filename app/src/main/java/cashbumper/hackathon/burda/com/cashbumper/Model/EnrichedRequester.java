package cashbumper.hackathon.burda.com.cashbumper.Model;

public class EnrichedRequester extends Requester {
    public EnrichedRequester(String id, double latitude, double longitude, int amount, int range, String transactionId, String cardNumber, String expiryMonth, String expiryYear, String cvc, int distance, int duration) {
        super(id, latitude, longitude, amount, range, transactionId, cardNumber, expiryMonth, expiryYear, cvc);
        this.distance = distance;
        this.duration = duration;
    }

    int distance;
    int duration;
}
