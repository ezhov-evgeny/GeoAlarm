package ru.ezhoff.geolocation.geoalarm;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import org.xml.sax.SAXException;
import ru.ezhoff.geolocation.Logger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainActivity extends Activity implements LocationListener {

    private static final Logger LOGGER = new Logger(MainActivity.class.getName());
    private TextView locationLbl;
    private LocationManager locationManager;
    private String provider;

    private static final String PROVIDER_MESSAGE = "Current provider - %s.";
    private static final String PROVIDER_ENABLED_MESSAGE = "The provider - %s is enabled.";
    private static final String PROVIDER_DISABLED_MESSAGE = "The provider - %s is disabled.";
    private static final String POSITION_MESSAGE = "Current position: Latitude - %s, Longitude - %s.";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locationLbl = (TextView) findViewById(R.id.Location);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        LOGGER.debug(String.format(PROVIDER_MESSAGE, provider));
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            LOGGER.debug(String.format(POSITION_MESSAGE, location.getLatitude(), location.getLongitude()));
            locationLbl.setText(String.format(POSITION_MESSAGE, location.getLatitude(), location.getLongitude()));
        }

        RoutesParser parser = new RoutesParser();
        try {
            parser.parse("res/xml-ru/metro_stations.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LOGGER.debug(String.format(POSITION_MESSAGE, location.getLatitude(), location.getLongitude()));
        locationLbl.setText(
                String.format(POSITION_MESSAGE, location.getLatitude(), location.getLongitude()) + "\n" +
                        String.format(PROVIDER_MESSAGE, provider)
        );
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        provider = s;
        LOGGER.debug(String.format(PROVIDER_ENABLED_MESSAGE, s));
    }

    @Override
    public void onProviderDisabled(String s) {
        LOGGER.debug(String.format(PROVIDER_DISABLED_MESSAGE, s));
    }
}
