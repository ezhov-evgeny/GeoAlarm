package ru.ezhoff.geolocation.geoalarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import ru.ezhoff.geolocation.Logger;
import ru.ezhoff.geolocation.model.Station;

public class MainActivity extends Activity implements LocationListener {

    private static final Logger LOGGER = new Logger(MainActivity.class.getName());
    private TextView locationLbl;
    private Spinner fromSpinner, toSpiner;
    private LocationManager locationManager;
    private String provider;

    private static final String PROVIDER_MESSAGE = "Current provider - %s.";
    private static final String PROVIDER_ENABLED_MESSAGE = "The provider - %s is enabled.";
    private static final String PROVIDER_DISABLED_MESSAGE = "The provider - %s is disabled.";
    private static final String POSITION_MESSAGE = "Current position: Latitude - %s, Longitude - %s.";
    private static final String STATION_MESSAGE = "The nearest station - %s: Latitude - %s, Longitude - %s.";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locationLbl = (TextView) findViewById(R.id.locationLbl);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        toSpiner    = (Spinner) findViewById(R.id.toSpinner);

        RoutesParser parser = new RoutesParser();
        XmlPullParser xml = getResources().getXml(R.xml.metro_stations);
        try {
            StationMap.getInstance().setRoutes(parser.parse(xml));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            new AlertDialog.Builder(getApplicationContext()).setMessage(e.getMessage()).show();
            return;
        }

        StationArrayAdapter stationAdapter = new StationArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line,
                StationMap.getInstance().getStationList()
        );

        fromSpinner.setAdapter(stationAdapter);
        toSpiner.setAdapter(stationAdapter);
        toSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Station station = (Station) adapterView.getItemAtPosition(i);
                ((TextView) findViewById(R.id.textView)).setText("Selected: " + station.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        LOGGER.debug(String.format(PROVIDER_MESSAGE, provider));
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            processLocation(location);
        }
        locationLbl.setText("Loaded");
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
        processLocation(location);
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

    private void processLocation(Location location) {
        LOGGER.debug(String.format(POSITION_MESSAGE, location.getLatitude(), location.getLongitude()));
        Station station = StationMap.getInstance().getNearestStation(location);
        locationLbl.setText(
                String.format(POSITION_MESSAGE, location.getLatitude(), location.getLongitude()) + "\n" +
                        String.format(PROVIDER_MESSAGE, provider) + "\n" +
                        String.format(STATION_MESSAGE, station.getName(), station.getLocation().getLatitude(),
                                station.getLocation().getLongitude())
        );
    }
}
