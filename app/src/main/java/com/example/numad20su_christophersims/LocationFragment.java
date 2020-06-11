package com.example.numad20su_christophersims;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment {
    final int REQUEST_PERMISSION_LOCATION = 1;
    Location userLocation;
    LocationTracker locationTracker = new LocationTracker();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPermissions();

        view.findViewById(R.id.button_fetch_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LocationTracker locationTracker = new LocationTracker();

                if(userLocation != null) {
                    String locationString = String.format("Longitude: %f | Latitude: %f", userLocation.getLongitude(), userLocation.getLatitude());
                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
                    locationTextView.setText(locationString);

                }
                else {
                   getPermissions();
                    userLocation = locationTracker.fetchLocation();
                    String locationString = String.format("Longitude: %f | Latitude: %f", userLocation.getLongitude(), userLocation.getLatitude());
                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
                    locationTextView.setText(locationString);

                }
            }
        });

    }

    public class LocationTracker {
        final int REQUEST_PERMISSION_LOCATION = 1;
        Location userLocation;



        public Location getUserLocation() {
            return this.userLocation;
        }

        private void getPermissions() {
            try {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, this.REQUEST_PERMISSION_LOCATION);
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_PERMISSION_LOCATION);
                }

//                else {
//                    Log.v("!", "whfarsbdbt");
//                    Location userLocation = locationTracker.getUserLocation();
//                    String locationString = String.format("Longitude: %f | Latitude: %f", userLocation.getLongitude(), userLocation.getLatitude());
//                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
//                    locationTextView.setText(locationString);
//                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Location fetchLocation() throws SecurityException {
            Location location = null;
            try {
                LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                String provider = locationManager.getBestProvider(new Criteria(), true);
                location = locationManager.getLastKnownLocation(provider);

                if(location == null) {
                    locationManager.requestLocationUpdates(provider, 2000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
                    location = locationManager.getLastKnownLocation(provider);
                }
                return location;

            }

            catch(Exception e) {
               e.printStackTrace();
            }


            return null;
        }

    }

    public void getPermissions() {
        try {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, this.REQUEST_PERMISSION_LOCATION);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_PERMISSION_LOCATION);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {


            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Got Location Permissions!!!", Toast.LENGTH_SHORT).show();
                    this.userLocation = this.locationTracker.fetchLocation();

//                    Location userLocation = locationTracker.getUserLocation();
//                    String locationString = String.format("Longitude: %f | Latitude: %f", this.userLocation.getLongitude(), this.userLocation.getLatitude());
//                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
//                    locationTextView.setText(locationString);
                } else {
                    Toast.makeText(getContext(), "No Access to Location Data :(", Toast.LENGTH_SHORT).show();
                }
        }
    }



}

