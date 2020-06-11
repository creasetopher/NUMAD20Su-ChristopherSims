package com.example.numad20su_christophersims;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
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

import static android.Manifest.permission.READ_PHONE_STATE;
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
        getPermissions();
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_fetch_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LocationTracker locationTracker = new LocationTracker();
                userLocation = locationTracker.fetchLocation();
                if(userLocation != null) {
                    String locationString = String.format("Longitude: %f | Latitude: %f", userLocation.getLongitude(), userLocation.getLatitude());
                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
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

                else {
                    Log.v("!", "whfarsbdbt");
                    Location userLocation = locationTracker.getUserLocation();
                    String locationString = String.format("Longitude: %f | Latitude: %f", userLocation.getLongitude(), userLocation.getLatitude());
                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
                    locationTextView.setText(locationString);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


        public Location fetchLocation() throws SecurityException {
            Location location = null;
            try {

                LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, new LocationListener() {
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


                boolean hasCellular = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                System.out.println(hasCellular);
                boolean hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (locationManager != null) {

                    if (hasCellular) {

                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Log.println(Log.ERROR, "!", "Cell!!!!!!!!");

                    }

                    else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
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
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.println(Log.ERROR, "!", "gps!!!!!!!!");

                    }

                    if (location != null) {
                        Log.println(Log.ERROR, "!", "got to location1!!!");

                        double lat = location.getLatitude();

                        double lon = location.getLongitude();

                        Location locationObj = new Location("");
                        locationObj.setLatitude(lat);
                        locationObj.setLongitude(lon);
                        return locationObj;
                    }
                }
            }

            catch(Exception e) {
                Log.println(Log.ERROR, "!", "Raised from getLocation call");
               e.printStackTrace();
            }

            Log.println(Log.ERROR, "!", "GOT LOC!!!!!!!!");

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
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Got Location Permissions!!!", Toast.LENGTH_SHORT).show();
                    this.userLocation = this.locationTracker.fetchLocation();
                    Location userLocation = locationTracker.getUserLocation();
                    String locationString = String.format("Longitude: %f | Latitude: %f", userLocation.getLongitude(), userLocation.getLatitude());
                    final TextView locationTextView =  (TextView)getView().findViewById(R.id.location_text_view);
                    locationTextView.setText(locationString);
                } else {
                    Toast.makeText(getContext(), "No Access to Location Data :(", Toast.LENGTH_SHORT).show();
                }
        }
    }



}

