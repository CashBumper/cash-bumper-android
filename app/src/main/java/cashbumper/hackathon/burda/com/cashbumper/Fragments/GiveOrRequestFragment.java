package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import cashbumper.hackathon.burda.com.cashbumper.R;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class GiveOrRequestFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.give_or_request_layout, null);
        ImageButton provide = (ImageButton) v.findViewById(R.id.provide);
        provide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.startGiverConfig();
            }
        });
        ImageButton request = (ImageButton) v.findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.startRequesterConfig();
            }
        });

        return v;
    }
}
