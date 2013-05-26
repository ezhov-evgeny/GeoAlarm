package ru.ezhoff.geolocation.geoalarm;

import android.graphics.drawable.Drawable;
import android.location.Location;
import ru.ezhoff.geolocation.model.Route;
import ru.ezhoff.geolocation.model.Station;

import java.util.LinkedList;
import java.util.List;

/**
 * @author e.ezhov
 * @version 1.0 15.05.13
 */
public class StationMap {

    private static StationMap instance;

    private List<Route> routes;

    private StationMap() {
    }

    public static StationMap getInstance() {
        if (instance == null) {
            instance = new StationMap();
        }
        return instance;
    }

    public Drawable loadAndGetMap() {
        return null;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Station getNearestStation(Location location) {
        double nearestDistance = 9999999;
        Station nearestStation = null;
        for (Route route: routes) {
            for (Station station: route.getStations().values()) {
                double distance = station.getLocation().distanceTo(location);
                if (distance < nearestDistance) {
                    nearestStation = station;
                    nearestDistance = distance;
                }
            }
        }
        return nearestStation;
    }

    public List<Station> getStationList() {
        List<Station> stations = new LinkedList<>();
        for (Route route: routes) {
            stations.addAll(route.getStations().values());
        }
        return stations;
    }

}
