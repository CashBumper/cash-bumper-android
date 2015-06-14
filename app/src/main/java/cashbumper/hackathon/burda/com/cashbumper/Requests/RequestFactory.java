package cashbumper.hackathon.burda.com.cashbumper.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import cashbumper.hackathon.burda.com.cashbumper.Listeners.ErrorListener;

public class RequestFactory {

    static String serverBaseUrl = "http://172.20.19.51:5002/";

    public static JsonObjectRequest createRequesterSession(Response.Listener<JSONObject> listener,
            String cardNumber, String expiryMonth, String expiryYear, String cvc, int amount, int range){
        return new BaseRequest(Request.Method.POST,
                serverBaseUrl + "create_requester_session?" +
                        "card_number="  + cardNumber  + "&" +
                        "expiry_month=" + expiryMonth + "&" +
                        "expiry_year="  + expiryYear  + "&" +
                        "cvc="          + cvc         + "&" +
                        "amount="       + amount      + "&" +
                        "range="        + range,
                listener);
    }

    public static JsonObjectRequest createGiverSession(Response.Listener<JSONObject> listener,
            int amount, int range, String sepa) {
        return new BaseRequest(Request.Method.POST,
                serverBaseUrl + "create_giver_session?" +
                        "amount=" + amount + "&" +
                        "range="  + range  + "&" +
                        "sepa="   + sepa,
                listener);
    }

    public static JsonObjectRequest findGiversAround(Response.Listener<JSONObject> listener,
            double lat, double lng, String requesterId) {
        return new BaseRequest(Request.Method.GET,
                serverBaseUrl + "find_givers_around?" +
                        "lat="          + lat         + "&" +
                        "lng="          + lng         + "&" +
                        "requester_id=" + requesterId,
                         listener);
    }

    public static JsonObjectRequest findRequestersAround(Response.Listener<JSONObject> listener,
            double lat, double lng, String giverId) {
        return new BaseRequest(Request.Method.GET,
                serverBaseUrl + "find_requesters_around?" +
                        "lat="      + lat    + "&" +
                        "lng="      + lng    + "&" +
                        "giver_id=" + giverId,
                listener);
    }

    public static JsonObjectRequest acceptRequest(Response.Listener<JSONObject> listener,
            String requesterId, String giverId) {
        return new BaseRequest(Request.Method.GET,
                serverBaseUrl + "accept_request?" +
                        "requester_id=" + requesterId + "&" +
                        "giver_id="     + giverId,
                listener);
    }

    public static JsonObjectRequest getRequesterTransactionGiver(Response.Listener<JSONObject> listener, String requesterId) {
        return new BaseRequest(Request.Method.GET,
                serverBaseUrl + "get_requester_transaction_giver?requester_id=" + requesterId, listener);
    }

    public static JsonObjectRequest bump(Response.Listener<JSONObject> listener, String requesterId, String transactionId) {
        return new BaseRequest(Request.Method.POST,
                serverBaseUrl + "bump?requester_id=" + requesterId+"&transaction_id="+transactionId,listener);
    }

    private static class BaseRequest extends JsonObjectRequest {
        public BaseRequest(int method, String url, Response.Listener<JSONObject> listener) {
            super(method, url, listener, new ErrorListener());
        }
    }
}
