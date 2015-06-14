package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import cashbumper.hackathon.burda.com.cashbumper.BaseActivity;
import cashbumper.hackathon.burda.com.cashbumper.R;
import cashbumper.hackathon.burda.com.cashbumper.Requests.RequestFactory;

/**
 * Created by laurentmeyer on 14/06/15.
 */
public class GiverMoneyFragment extends BaseFragment {

    TextView sum;

    int sumInt = 0;
    int progressToSend = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.giver_money_layout, null);
        sum = (TextView) v.findViewById(R.id.sum);
        sumInt = Integer.parseInt(((String) sum.getText()).replace(" €", ""));
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
        return v;
    }

    public void add(View v) {
        int howMuch = Integer.parseInt((String) v.getTag());
        View parent = (View) v.getParent();
        TextView tv = (TextView) parent.findViewWithTag("number");
        tv.setText(Integer.parseInt((String) tv.getText()) + 1 + "");
        sum.setText(sumInt + howMuch + " €");
        sumInt = sumInt + howMuch;
    }

    public void delete(View v) {
        int howMuch = Integer.parseInt((String) v.getTag());
        View parent = (View) v.getParent();
        TextView tv = (TextView) parent.findViewWithTag("number");
        if (Integer.parseInt((String) tv.getText()) - 1 >= 0) {
            tv.setText(Integer.parseInt((String) tv.getText()) - 1 + "");
            sum.setText((sumInt - howMuch) + " €");
            sumInt = sumInt - howMuch;

        }
    }

    public void confirm(View v) {
        // TODO Fake account data but we should have the sum and then get the id of the session
        Log.d("GiverMoneyFragment", "In confirm");
        if (sumInt > 0) {
            ((BaseActivity) getActivity()).executeRequest(RequestFactory.buildGiverSession(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject object) {
                    try {
                        String id = object.getString("requester_id");
                        String requestId = object.getString("transaction_id");

                        // TODO Submit that to new model classes
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, sumInt, progressToSend, null));

            //TODO: move that in the success and start a loading process
            callbacks.startList();

        } else {
            Toast.makeText(getActivity(), "Please set an amount to exchange", Toast.LENGTH_SHORT);
        }


    }
}
