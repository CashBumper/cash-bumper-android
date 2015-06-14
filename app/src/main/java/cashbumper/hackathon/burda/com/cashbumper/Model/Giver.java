package cashbumper.hackathon.burda.com.cashbumper.Model;

public class Giver extends User {
    public Giver(String id, double latitude, double longitude, int amount, int range, String sepa) {
        super(id, latitude, longitude, amount, range);
        this.sepa = sepa;
    }

    String sepa;
}
