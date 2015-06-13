package cashbumper.hackathon.burda.com.cashbumper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import cashbumper.hackathon.burda.com.cashbumper.Model.User;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class BaseActivity extends FragmentActivity {
    protected FragmentActivity activity;

    static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();
        RequestManager.init(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

    protected void executeRequest(Request<?> request) {
        Log.d("BaseActivity", "Request sent");
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}