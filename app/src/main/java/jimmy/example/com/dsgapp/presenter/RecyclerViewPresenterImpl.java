package jimmy.example.com.dsgapp.presenter;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jimmy.example.com.dsgapp.MainActivity;
import jimmy.example.com.dsgapp.R;
import jimmy.example.com.dsgapp.model.Constants;
import jimmy.example.com.dsgapp.model.Response;
import jimmy.example.com.dsgapp.model.VenuesItem;
import jimmy.example.com.dsgapp.network.RetrofitApi;
import jimmy.example.com.dsgapp.network.RetrofitClient;
import jimmy.example.com.dsgapp.view.RecyclerViewListener;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Jinming on 1/26/18.
 */

public class RecyclerViewPresenterImpl implements RecyclerViewPresenter {
    private RecyclerViewListener viewListener;
    private List<VenuesItem> list;
    private MyAdapter myAdapter;
    private RetrofitApi retrofitApi;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location myLocation;
    private LocationCallback mLocationCallback;
    public static boolean mRequestingLocationUpdates = false;

    private AddressResultReceiver mResultReceiver;

    private static final String TAG = "RecyclerPresenterImpl";

    public RecyclerViewPresenterImpl(RecyclerViewListener listener) {
        this.viewListener = listener;
        this.list = new ArrayList<>();
        this.retrofitApi = RetrofitClient.getInstance().create(RetrofitApi.class);
        this.mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient((Context) viewListener);
        mResultReceiver = new AddressResultReceiver(new Handler(Looper.getMainLooper()));
    }

    @Override
    public boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission((Context) viewListener, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void getLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        //Log.i(TAG, (location == null) + "");
                        Log.i(TAG, "onSuccess: " + (location == null));
                        if (location != null) {
                            myLocation = location;
                        } else {
                            Location l = new Location("");
                            l.setLatitude(41.920201);
                            l.setLongitude(-88.265293);
                            myLocation = l;
                        }
                        mRequestingLocationUpdates = true;
                        startIntentService();
                    }
                });
    }

    @Override
    public void loadData() {
        Call<Response> call = retrofitApi.getData();
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    list = response.body().getVenues();
                    for (VenuesItem item : list) {
                        if (!item.isVerified()) {
                            list.remove(item);
                        }
                    }
                    myAdapter = new MyAdapter((Context)viewListener, list, myLocation);
                    viewListener.onRecyclerViewListener(myAdapter);
                    sortByDistance(list, myLocation);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i(TAG, "retrofit onFailure: web request");
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates: starts");
        if (mRequestingLocationUpdates) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(3000);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    int n = 0;
                    for (Location location : locationResult.getLocations()) {
                        Log.i(TAG, n++ + " times " + location.toString());
                    }
                }
            };
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
        }
    }

    @Override
    public void stopLocationUpdates() {
        if (mRequestingLocationUpdates) {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void startIntentService() {
        Log.i(TAG, "startIntentService: starts");
        Intent intent = new Intent((MainActivity)viewListener, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, myLocation);
        ((Context)viewListener).startService(intent);
    }

    @Override
    public void init() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient((Context) viewListener);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(((MainActivity) viewListener), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.i(TAG, "onSuccess setting in task");
            }
        });

        task.addOnFailureListener((MainActivity) viewListener, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    Log.i(TAG, "onFailure: exception on ResolvableApiException");
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult((MainActivity) viewListener, 11);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.i(TAG, "onReceiveResult: starts");
            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            Log.i(TAG, "onReceiveResult: " + mAddressOutput);
            displayAddressOutput(mAddressOutput);

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                //Toast.makeText((MainActivity)viewListener, (R.string.address_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayAddressOutput(String output) {
        Toast.makeText((MainActivity)viewListener, output, Toast.LENGTH_SHORT).show();
    }

    private void sortByDistance(List<VenuesItem> list, final Location location) {
        if (location != null) {
            Collections.sort(list, new Comparator<VenuesItem>() {
                @Override
                public int compare(VenuesItem o1, VenuesItem o2) {
                    Location l1 = new Location("1");
                    l1.setLatitude(o1.getLocation().getLatitude());
                    l1.setLongitude(o1.getLocation().getLongitude());
                    Location l2 = new Location("2");
                    l2.setLatitude(o2.getLocation().getLatitude());
                    l2.setLongitude(o2.getLocation().getLongitude());
                    return ((int) (location.distanceTo(l1) - location.distanceTo(l2)));
                }
            });
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sortDefault() {
        sortByDistance(list, myLocation);
        Toast.makeText((Context)viewListener, "sort by distance", Toast.LENGTH_SHORT).show();
    }
}
