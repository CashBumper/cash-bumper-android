package cashbumper.hackathon.burda.com.cashbumper;

import org.json.JSONObject;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class Parser {

    public static String getIdFromJSONObject(JSONObject object){
        try{
            String id = object.getString("id");
            return id;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
