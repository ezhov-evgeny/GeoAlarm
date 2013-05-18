package ru.ezhoff.geolocation;

import android.location.Location;

/**
 * @author Evgeny Ezhov evgeny.ezhov@gmail.com
 * @version 1.0 03.05.13
 */
public class MetroMap {

    private static final Logger LOGGER = new Logger(MetroMap.class.getName());

    public static MetroMap createMetroMap(String provider) {
        MetroMap newMetroMap = new MetroMap();
        newMetroMap.stations = new Station[4];
        newMetroMap.stations[0] = new Station();
        newMetroMap.stations[0].setName("Бибирево");
        Location newLocation = new Location(provider);
        newLocation.setLatitude(37.60523);
        newLocation.setLongitude(55.88294);
        newMetroMap.stations[0].setLocation(newLocation);
        newMetroMap.stations[1] = new Station();
        newMetroMap.stations[1].setName("Отрадное");
        newLocation = new Location(provider);
        newLocation.setLatitude(37.60488);
        newLocation.setLongitude(55.86417);
        newMetroMap.stations[1].setLocation(newLocation);
        newMetroMap.stations[2] = new Station();
        newMetroMap.stations[2].setName("Бабушкинская");
        newLocation = new Location(provider);
        newLocation.setLatitude(37.66292);
        newLocation.setLongitude(55.86814);
        newMetroMap.stations[2].setLocation(newLocation);
        newMetroMap.stations[3] = new Station();
        newMetroMap.stations[3].setName("Cвиблово");
        newLocation = new Location(provider);
        newLocation.setLatitude(37.65419);
        newLocation.setLongitude(55.85543);
        newMetroMap.stations[3].setLocation(newLocation);
        return newMetroMap;
    }

    private Station[] stations;

    public Station getNearestStation(Location l) {
        int nearest = 0;
        double minDistanse = 100000000;
        LOGGER.debug("Search nearest station");
        for (int i = 0; i < stations.length; i++) {
            double current = stations[i].getLocation().distanceTo(l);
            LOGGER.debug(String.format(
                    "Now checks station #%i %s and distance is %d",
                    new Object[] {i, stations[i].getName(), current})
            );
            if (current < minDistanse) {
                nearest = i;
                minDistanse = current;
            }
        }
        return stations[nearest];
    }


}
