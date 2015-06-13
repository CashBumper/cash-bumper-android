package cashbumper.hackathon.burda.com.cashbumper;

import android.os.Bundle;

import cashbumper.hackathon.burda.com.cashbumper.Fragments.GiveOrRequestFragment;
import cashbumper.hackathon.burda.com.cashbumper.Fragments.MapFragment;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new GiveOrRequestFragment()).commit();
    }


}
