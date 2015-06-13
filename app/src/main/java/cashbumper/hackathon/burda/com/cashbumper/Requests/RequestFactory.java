package cashbumper.hackathon.burda.com.cashbumper.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import cashbumper.hackathon.burda.com.cashbumper.Listeners.ErrorListener;

/**
 * Created by laurentmeyer on 13/06/15.
 */

public class RequestFactory {

    public static JsonObjectRequest buildSessionRequest(Response.Listener<JSONObject> listener){
        return new SessionRequest(Request.Method.POST, "xxx/create_session", listener);
    }

    private static class SessionRequest extends JsonObjectRequest {
        public SessionRequest(int method, String url, Response.Listener<JSONObject> listener) {
            super(method, url, listener, new ErrorListener());
        }


    }
}
