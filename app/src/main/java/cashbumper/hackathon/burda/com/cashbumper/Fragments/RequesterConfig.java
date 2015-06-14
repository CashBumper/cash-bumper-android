package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.text.DecimalFormat;

import cashbumper.hackathon.burda.com.cashbumper.BaseActivity;
import cashbumper.hackathon.burda.com.cashbumper.R;
import cashbumper.hackathon.burda.com.cashbumper.Requests.RequestFactory;
import cashbumper.hackathon.burda.com.cashbumper.Saver;

/**
 * Created by laurentmeyer on 14/06/15.
 */
public class RequesterConfig extends BaseFragment {
    private int amount = 0;

    private int progressToSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.requester_config_layout, null);
        Button b = (Button) v.findViewById(R.id.button_confirm);
        EditText te = (EditText) v.findViewById(R.id.amount);
        te.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SeekBar bar = (SeekBar) v.findViewById(R.id.seekbar);
        final TextView rangeIndicator = (TextView) v.findViewById(R.id.rangeIndicator);

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressToSend = progress * 100 + 100;

                if (progress < 9) {
                    rangeIndicator.setText(progress * 100 + 100 + " m");
                } else {

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(1);
                    double toBeRounded = progress / 10.0 + 0.1;
                    String display = df.format(toBeRounded);
                    rangeIndicator.setText(display + " km");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 0) {
                    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                Log.d("RequesterConfig", "object:" + object);
                                String id = object.getString("requester_id");
                                Log.d("RequesterConfig", id);
                                Saver.getInstance().setId(id);
                            } catch (Exception e) {

                            }
                        }
                    };
                    BaseActivity activity = (BaseActivity) getActivity();
                    activity.executeRequest(RequestFactory.createRequesterSession(listener, "4111111111111111", "01", "2016", "999", amount, progressToSend));
                    callbacks.startMap(true);
                }
            }
        });
        return v;
    }
}
