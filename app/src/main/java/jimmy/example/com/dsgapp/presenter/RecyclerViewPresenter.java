package jimmy.example.com.dsgapp.presenter;

import android.view.View;

/**
 * Created by Jinming on 1/26/18.
 */

public interface RecyclerViewPresenter {
    boolean checkPermission();
    void getLocation();
    void loadData();
    void sortDefault();
    void init();
    void startLocationUpdates();
    void stopLocationUpdates();
    void startIntentService();
}
