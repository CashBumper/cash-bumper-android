package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cashbumper.hackathon.burda.com.cashbumper.BaseActivity;
import cashbumper.hackathon.burda.com.cashbumper.R;
import cashbumper.hackathon.burda.com.cashbumper.Requests.RequestFactory;
import cashbumper.hackathon.burda.com.cashbumper.Saver;

/**
 * Created by laurentmeyer on 14/06/15.
 */
public class BumpFragment extends BaseFragment {
    ImageView iv;
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bump_layout, null);
        tv = (TextView) v.findViewById(R.id.bump_text);
        iv = (ImageView) v.findViewById(R.id.image_bump);
        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vasYFrankie();
                return true;
            }
        });
        return v;

    }

    private void vasYFrankie() {
        ((BaseActivity)getActivity()).executeRequest(RequestFactory.bump(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                iv.setImageResource(R.drawable.done);
                tv.setText("Transaction Done!");
                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        Saver.getInstance().clear();
                        callbacks.startSplashScreen();
                    }
                };
                t.schedule(tt, 5000);

            }
        }, Saver.getInstance().getTransactionId(), null));
    }
}
