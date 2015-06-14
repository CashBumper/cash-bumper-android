package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cashbumper.hackathon.burda.com.cashbumper.BaseActivity;
import cashbumper.hackathon.burda.com.cashbumper.R;
import cashbumper.hackathon.burda.com.cashbumper.Requests.RequestFactory;

/**
 * Created by laurentmeyer on 14/06/15.
 */
public class GiverMoneyFragment extends BaseFragment {

    TextView sum;

    int sumInt = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.giver_money_layout, null);
        sum = (TextView) v.findViewById(R.id.sum);
        sumInt = Integer.parseInt(((String) sum.getText()).replace(" €", ""));
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
        // TODO Range

        ((BaseActivity)getActivity()).executeRequest(RequestFactory.buildGiverSession(new Response.Listener<JSONObject>() {
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
        }, sumInt, 0, null));


    }
}
