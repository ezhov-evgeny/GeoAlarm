package ru.ezhoff.geolocation.geoalarm;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * @author Evgeny Ezhov evgeny.ezhov@gmail.com
 * @version 1.0 20.04.13
 */
public class TrackerService extends Service {

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onProviderEnabled(String s) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onProviderDisabled(String s) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        // @TODO Read about Geocoder

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
