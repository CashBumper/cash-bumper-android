package cashbumper.hackathon.burda.com.cashbumper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import cashbumper.hackathon.burda.com.cashbumper.Fragments.BumpFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.GiveOrRequestFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.GiverMoneyFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.MapFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.RequesterConfig;
import cashbumper.hackathon.burda.com.cashbumper.Interfaces.FragmentCallbacks;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class MainActivity extends BaseActivity implements FragmentCallbacks{

    GiverMoneyFragment GMFragment;
    ProgressBar b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GMFragment = new GiverMoneyFragment();
        setContentView(R.layout.main_layout);
        startSplashScreen();
    }

    public void add(View v){
        GMFragment.add(v);
    }

    public void delete(View v){
        GMFragment.delete(v);
    }

    public void confirm(View v){
        GMFragment.confirm(v);
    }

    @Override
    public void startSplashScreen() {
        clearBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new GiveOrRequestFragment()).addToBackStack("grf").commit();
    }

    @Override
    public void startGiverConfig() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, GMFragment).addToBackStack("gmf").commit();
    }

    @Override
    public void startRequesterConfig() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new RequesterConfig()).addToBackStack("rc").commit();
    }


    @Override
    public void startMap(boolean isRequester) {
        Bundle b = new Bundle();
        b.putBoolean("isRequester", isRequester);
        MapFragment mf = new MapFragment();
        mf.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mf).addToBackStack("map").commit();
    }

    @Override
    public void startBump() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BumpFragment()).addToBackStack("bump").commit();
        Log.d("MainActivity", "StartBump");
    }

    private void clearBackStack(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

}
