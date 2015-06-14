package cashbumper.hackathon.burda.com.cashbumper;

import android.os.Bundle;
import android.view.View;

import cashbumper.hackathon.burda.com.cashbumper.Fragments.GiveOrRequestFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.GiverMoneyFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.MapFragment;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class MainActivity extends BaseActivity {

    GiverMoneyFragment GMfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        GMfragment = new GiverMoneyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, GMfragment).commit();
    }

    public void add(View v){
        GMfragment.add(v);
    }

    public void delete(View v){
        GMfragment.delete(v);
    }

    public void confirm(View v){
        GMfragment.confirm(v);
    }

}
