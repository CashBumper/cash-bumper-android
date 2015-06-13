package cashbumper.hackathon.burda.com.cashbumper.Listeners;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class ErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.d("ErrorListener", "volleyError:" + volleyError);
    }
}
