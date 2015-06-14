package cashbumper.hackathon.burda.com.cashbumper.Model;

public class User {
    String id;

    public User(String id, double latitude, double longitude, int amount, int range) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.amount = amount;
        this.range = range;
    }

    double latitude;
    double longitude;
    int amount;
    int range;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
