package cashbumper.hackathon.burda.com.cashbumper;

/**
 * Created by laurentmeyer on 14/06/15.
 */
public class Saver {
    String id;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String transactionId;
    private static Saver instance;

    public static Saver getInstance() {
        if (instance == null) {
            instance = new Saver();
        }
        return instance;
    }

}
