package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cashbumper.hackathon.burda.com.cashbumper.R;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class GiveOrRequestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.give_or_request_layout, null);
        return v;
    }
}
