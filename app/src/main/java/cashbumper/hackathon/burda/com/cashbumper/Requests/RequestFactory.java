package cashbumper.hackathon.burda.com.cashbumper.Requests;

import android.graphics.Point;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import cashbumper.hackathon.burda.com.cashbumper.Listeners.ErrorListener;

/**
 * Created by laurentmeyer on 13/06/15.
 */

public class RequestFactory {

    static String serverBaseUrl = "http://172.20.19.51:5000/";

    enum STATES{
        SEEN, ACCEPTED, REJECTED
    }


    public static JsonObjectRequest buildRequesterSession(Response.Listener<JSONObject> listener, String account, int amount){
        return new BaseRequest(Request.Method.POST, serverBaseUrl + "create_requester_session?"+"amount="+amount+"&account="+account, listener);
    }

    public static JsonObjectRequest buildFindGiversRequest(Response.Listener<JSONObject> listener, double lat, double lng, int range, String id){
        return new BaseRequest(Request.Method.GET, serverBaseUrl +"find_givers_around?lat="+lat+"&lng="+lng+"&range="+range+"&id="+id, listener);
    }

    public static JsonObjectRequest buildRequesterBumpRequest(Response.Listener<JSONObject> listener, String idMe, String idTransaction){
        return new BaseRequest(Request.Method.POST, serverBaseUrl+"requester_bump?id="+idMe+"&token="+idTransaction, listener);
    }

    public static JsonObjectRequest buildGiverBumpRequest(Response.Listener<JSONObject> listener, String idMe, String idTransaction){
        return new BaseRequest(Request.Method.POST, serverBaseUrl+"giver_bump?id="+idMe+"&token="+idTransaction, listener);
    }

    public static JsonObjectRequest buildGiverSession(Response.Listener<JSONObject> listener, int maxAmount, int maxRange, String sepa){
        return new BaseRequest(Request.Method.POST, serverBaseUrl+"create_giver_session?maxAmount="+maxAmount+"&maxRange="+maxRange+"&sepa="+sepa, listener);
    }

    public static JsonObjectRequest buildFindRequesterRequest(Response.Listener<JSONObject> listener, double lat, double lng, String id){
        return new BaseRequest(Request.Method.GET, serverBaseUrl+"find_requesters_around?lat="+lat+"&lng="+lng+"&id="+id, listener);
    }

    public static JsonObjectRequest buildRequestStatusSetterRequest(Response.Listener<JSONObject> listener, STATES s, String id, String request){
        return new BaseRequest(Request.Method.POST, serverBaseUrl+buildUrlFromState(s)+"?id="+id+"&request_id="+request, listener);
    }

    private static String buildUrlFromState(STATES s){
        switch (s){
            case SEEN:
                return "saw_request";
            case ACCEPTED:
                return "accept_request";
            case REJECTED:
                return "decline_request";
            default:
                return null;
        }
    }



    private static class BaseRequest extends JsonObjectRequest {
        public BaseRequest(int method, String url, Response.Listener<JSONObject> listener) {
            super(method, url, listener, new ErrorListener());
        }
    }
}
