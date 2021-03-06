package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cashbumper.hackathon.burda.com.cashbumper.BaseActivity;
import cashbumper.hackathon.burda.com.cashbumper.Model.EnrichedGiver;
import cashbumper.hackathon.burda.com.cashbumper.Model.EnrichedRequester;
import cashbumper.hackathon.burda.com.cashbumper.Model.Giver;
import cashbumper.hackathon.burda.com.cashbumper.R;
import cashbumper.hackathon.burda.com.cashbumper.Requests.RequestFactory;
import cashbumper.hackathon.burda.com.cashbumper.Saver;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

/**
 * Created by laurentmeyer on 13/06/15.
 */
public class MapFragment extends BaseFragment implements com.google.android.gms.maps.OnMapReadyCallback {

    MapView mapView;
    GoogleMap map;
    Location current;
    boolean isRequester;

    Timer t;
    TimerTask tt;

    /**
     * Not to use any extended API
     */
    HashMap<String, String> markerMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_layout, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        isRequester = getArguments().getBoolean("isRequester");

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);

        markerMap = new HashMap<>();

        // Updates the location and zoom of the MapView
        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    private void centerMapOnMyLocation() {
        Log.d("MapFragment", "location:" + current);
        if (current != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(current.getLatitude(), current.getLongitude()), 13));

            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String uuid = markerMap.get(marker.getTitle());
                    Log.d("MapFragment", uuid);
                    acceptRequest(uuid);
                }
            });

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(current.getLatitude(), current.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                   // generateRandomsPoints();
                }

                @Override
                public void onCancel() {

                }
            });

        }
    }

    private void acceptRequest(String requesterId) {
        String giverId = Saver.getInstance().getId();

        BaseActivity b = (BaseActivity) getActivity();
        b.executeRequest(RequestFactory.acceptRequest(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                Log.d("MapFragment", "object:" + object);
                tt.cancel();
                t.cancel();
                callbacks.startBump();
            }
        }, requesterId, giverId));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MapFragment", "Map there");
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        SmartLocation.with(getActivity()).location()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        current = location;
                        Log.d("MapFragment", "current:" + current);
                        centerMapOnMyLocation();
                        launchMarkerRequest(isRequester);

                    }
                });
    }

    private void launchMarkerRequest(final boolean isRequester) {
        final Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {

                Log.d("MapFragment", "object:" + object);

                markerMap.clear();

                map.clear();

                try {
                    if (isRequester) {
                        JSONArray array = object.getJSONArray("givers");
                        for (int i = 0; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            String id = object.getString("id");
                            double latitude = object.getDouble("latitude");
                            double longitude = object.getDouble("longitude");
                            int amount = object.getInt("amount");
                            int range = object.getInt("range");
                            String sepa = object.getString("sepa");
                            int distance = object.getInt("distance");
                            int duration = object.getInt("duration");
                            EnrichedGiver giver = new EnrichedGiver(id, latitude, longitude, amount, range, sepa, distance, duration);
                            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);
                            Bitmap b2 = Bitmap.createScaledBitmap(b, b.getWidth() / 4, b.getHeight() / 4, false);
                            map.addGroundOverlay(new GroundOverlayOptions().image(BitmapDescriptorFactory.fromBitmap(b2)).position(new LatLng(latitude, longitude), 300f, 300f));
                        }
                    } else {
                        JSONArray array = object.getJSONArray("requesters");
                        for (int i = 0; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            String id = object.getString("id");
                            double latitude = object.getDouble("latitude");
                            double longitude = object.getDouble("longitude");
                            int amount = object.getInt("amount");
                            int range = object.getInt("range");
                            String transactionId = object.getString("transaction_id");
                            String cardNumber = object.getString("card_number");
                            String expiryMonth = object.getString("expiry_month");
                            String expiryYear = object.getString("expiry_year");
                            String cvc = object.getString("cvc");
                            int distance = object.getInt("distance");
                            int duration = object.getInt("duration");
                            EnrichedRequester requester = new EnrichedRequester(id, latitude, longitude, amount, range, transactionId, cardNumber, expiryMonth, expiryYear, cvc, distance, duration);
                            map.addMarker(new MarkerOptions().title(amount + " euros").position(new LatLng(latitude, longitude)).snippet(duration / 60 + " min\n" + distance + " m"));
                            markerMap.put(amount + " euros", id);
                        }
                    }

                } catch (Exception e) {
                }
            }
        };

        final Response.Listener<JSONObject> acceptedListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {

                Log.d("MapFragment", "giver:" + object);

                try {
                    String id = object.getString("id");
                    double latitude = object.getDouble("latitude");
                    double longitude = object.getDouble("longitude");
                    int amount = object.getInt("amount");
                    int range = object.getInt("range");
                    String sepa = object.getString("sepa");

                    Giver giver = new Giver(id, latitude, longitude, amount, range, sepa);
                    Button b = (Button) getView().findViewById(R.id.bump_button);
                    b.setVisibility(View.VISIBLE);
                    tt.cancel();
                    t.cancel();
                    map.clear();
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callbacks.startBump();
                        }

                    });
                } catch (Exception e) {}
            }
        };

        final BaseActivity b = (BaseActivity) getActivity();

        t = new Timer();
        tt = new TimerTask() {
            @Override
            public void run() {
                if (isRequester){
                    b.executeRequest(RequestFactory.findGiversAround(listener, current.getLatitude(), current.getLongitude(), Saver.getInstance().getId()));
                    b.executeRequest(RequestFactory.getRequesterTransactionGiver(acceptedListener, Saver.getInstance().getId()));
                }
                else{
                    b.executeRequest(RequestFactory.findRequestersAround(listener, current.getLatitude(), current.getLongitude(), Saver.getInstance().getId()));
                }
            };
        };
        t.schedule(tt,0, 2000);
    }

    private ArrayList<GroundOverlay> generateRandomsPoints() {
        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        LatLng up = bounds.northeast;
        LatLng down = bounds.southwest;

        ArrayList<GroundOverlay> markers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            double start = down.latitude;
            double end = up.latitude;
            double random = new Random().nextDouble();
            double lat = start + (random * (end - start));
            start = down.longitude;
            end = up.longitude;
            double random1 = new Random().nextDouble();
            double lng = start + (random1 * (end - start));
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);
            Bitmap b2 = Bitmap.createScaledBitmap(b, b.getWidth() / 4, b.getHeight() / 4, false);
            GroundOverlay g = map.addGroundOverlay(new GroundOverlayOptions().image(BitmapDescriptorFactory.fromBitmap(b2)).position(new LatLng(lat, lng), 300f, 300f));
            markers.add(g);
        }

        return markers;
    }

}