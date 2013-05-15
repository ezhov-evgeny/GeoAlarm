package ru.ezhoff.geolocation.geoalarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author e.ezhov
 * @version 1.0 15.05.13
 */
public class GeoAlarmService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
