package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import cashbumper.hackathon.burda.com.cashbumper.Interfaces.FragmentCallbacks;

/**
 * Created by laurentmeyer on 14/06/15.
 */
public class BaseFragment extends Fragment {

    FragmentCallbacks callbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (FragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement the callbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}
