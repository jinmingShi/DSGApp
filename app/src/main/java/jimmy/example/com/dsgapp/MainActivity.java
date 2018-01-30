package jimmy.example.com.dsgapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import jimmy.example.com.dsgapp.model.Constants;
import jimmy.example.com.dsgapp.presenter.MyAdapter;
import jimmy.example.com.dsgapp.presenter.RecyclerViewPresenter;
import jimmy.example.com.dsgapp.presenter.RecyclerViewPresenterImpl;
import jimmy.example.com.dsgapp.view.RecyclerViewListener;

public class MainActivity extends AppCompatActivity implements RecyclerViewListener {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private RecyclerViewPresenter recyclerViewPresenter;
    private static final int REQUEST_FINE_LOCATION = 1001;
    private static final String TAG = "MainActivity";
    //private static final String REQUESTING_LOCATION_UPDATES_KEY = "updates";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerViewPresenter = new RecyclerViewPresenterImpl(this);
        recyclerViewPresenter.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewPresenter.startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerViewPresenter.stopLocationUpdates();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        recyclerViewPresenter.sortDefault();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!recyclerViewPresenter.checkPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        } else {
            recyclerViewPresenter.init();
            recyclerViewPresenter.getLocation();
            recyclerViewPresenter.startLocationUpdates();
        }
    }

    @Override
    public void onRecyclerViewListener(MyAdapter myAdapter) {
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults.length <= 0) {
                Log.d(TAG, "onRequestPermissionsResult: cancelled");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recyclerViewPresenter.getLocation();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: denied");
            }
        }
    }
}
