package ru.ezhoff.geolocation.geoalarm;

import android.location.Location;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import ru.ezhoff.geolocation.model.Route;
import ru.ezhoff.geolocation.model.Station;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author e.ezhov
 * @version 1.0 21.05.13
 */
public class RoutesParserV2 {

    /**
     * Tags of xml
     */
    private final static String TAG_ROUTES_LIST             = "routes";
    private final static String TAG_ROUTE_ELEMENT           = "route";
    private final static String ATTR_ROUTE_OFFICIAL_NAME    = "officialNme";
    private final static String ATTR_ROUTE_UNOFFICIAL_NAME  = "unofficialName";
    private final static String ATTR_ROUTE_COLOR            = "color";
    private final static String TAG_STATION_ELEMENT         = "station";
    private final static String ATTR_STATION_ID             = "id";
    private final static String TAG_STATION_NAME            = "name";
    private final static String TAG_STATION_COORDINATES     = "coordinates";
    private final static String ATTR_COORDINATES_LATITUDE   = "latitude";
    private final static String ATTR_COORDINATES_LONGITUDE  = "longitude";
    private final static String ATTR_COORDINATES_ALTITUDE   = "altitude";
    private final static String TAG_JOIN_ELEMENT            = "join";
    private final static String ATTR_JOIN_REFERENCE         = "ref";

    /**
     * Namespace value
     */
    private static final String ns = null;

    private List<Route> routes = new LinkedList<>();
    private Route route;
    private Station station;
    private Location location;
    private List<String> joins;
    private String currentTag;

    public List<Route> parse(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(is, null);
        parser.nextTag();
        readRoutes(parser);
        return routes;
    }

    private void readRoutes(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_ROUTES_LIST);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                switch (parser.getName()) {
                    case TAG_ROUTE_ELEMENT      : route = new Route();
                                                  setRouteAttributes(parser);
                                                  joins = new LinkedList<>();
                                                  break;
                    case TAG_STATION_ELEMENT    : station = new Station();
                                                  station.setId(parser.getAttributeValue(ns, ATTR_STATION_ID));
                                                  break;
                    case TAG_STATION_NAME       : currentTag = TAG_STATION_NAME; break;
                    case TAG_STATION_COORDINATES: createLocation(parser); break;
                    case TAG_JOIN_ELEMENT       : joins.add(parser.getAttributeValue(ns, ATTR_JOIN_REFERENCE)); break;
                }
            } else if (parser.getEventType() == XmlPullParser.END_TAG) {
                switch (parser.getName()) {
                    case TAG_ROUTE_ELEMENT      : routes.add(route); break;
                    case TAG_STATION_ELEMENT    : route.addStation(station.getId(), station); break;
                    case TAG_STATION_NAME       : currentTag = null;
                    case TAG_STATION_COORDINATES: station.setLocation(location); break;
                    case TAG_JOIN_ELEMENT       : station.setJoins(joins.toArray(new String[joins.size()])); break;
                }
            }
        }
    }

    private void setRouteAttributes(XmlPullParser parser) {
        route.setName(parser.getAttributeValue(ns, ATTR_ROUTE_OFFICIAL_NAME));
        route.setColorName(parser.getAttributeValue(ns, ATTR_ROUTE_UNOFFICIAL_NAME));
        // @TODO set color here
    }

    private void createLocation(XmlPullParser parser) {
        location = new Location("network");
        location.setLatitude(extractDouble(parser, ATTR_COORDINATES_LATITUDE));
        location.setLongitude(extractDouble(parser, ATTR_COORDINATES_LONGITUDE));
        location.setAltitude(extractDouble(parser, ATTR_COORDINATES_ALTITUDE));
    }

    private double extractDouble(XmlPullParser parser, String name) {
        return Double.parseDouble(parser.getAttributeValue(ns, name));
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
