package cashbumper.hackathon.burda.com.cashbumper.Model;

public class EnrichedGiver extends Giver {
    int distance;
    int duration;

    public EnrichedGiver(String id, double latitude, double longitude, int amount, int range, String sepa, int distance, int duration) {
        super(id, latitude, longitude, amount, range, sepa);
        this.distance = distance;
        this.duration = duration;
    }
}
