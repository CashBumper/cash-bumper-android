package cashbumper.hackathon.burda.com.cashbumper.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Random;

import cashbumper.hackathon.burda.com.cashbumper.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_layout, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        isRequester = getArguments().getBoolean("isRequester");

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);

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

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(current.getLatitude(), current.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    generateRandomsPoints();
                }

                @Override
                public void onCancel() {

                }
            });

        }
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
                    }
                });
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