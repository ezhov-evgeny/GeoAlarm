package ru.ezhoff.geolocation.geoalarm;

import android.location.Location;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.ezhoff.geolocation.model.Route;
import ru.ezhoff.geolocation.model.Station;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author e.ezhov
 * @version 1.0 17.05.13
 */
public class RoutesParser extends DefaultHandler {

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


    private List<Route> routes = new LinkedList<>();
    private Route route;
    private Station station;
    private Location location;
    private List<String> joins;
    private String currentTag;

    public List<Route> parse(String pathToXml) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File(pathToXml), this);
        return routes;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (TAG_STATION_NAME.equals(currentTag)) {
            station.setName(new String(ch, start, length));
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName) {
            case TAG_ROUTE_ELEMENT      : routes.add(route); break;
            case TAG_STATION_ELEMENT    : route.addStation(station.getId(), station); break;
            case TAG_STATION_NAME       : currentTag = null;
            case TAG_STATION_COORDINATES: station.setLocation(location); break;
            case TAG_JOIN_ELEMENT       : station.setJoins(joins.toArray(new String[joins.size()])); break;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case TAG_ROUTE_ELEMENT      : route = new Route();
                                          setRouteAttributes(attributes);
                                          joins = new LinkedList<>();
                                          break;
            case TAG_STATION_ELEMENT    : station = new Station();
                                          station.setId(attributes.getValue(ATTR_STATION_ID));
                                          break;
            case TAG_STATION_NAME       : currentTag = TAG_STATION_NAME; break;
            case TAG_STATION_COORDINATES: createLocation(attributes); break;
            case TAG_JOIN_ELEMENT       : joins.add(attributes.getValue(ATTR_JOIN_REFERENCE)); break;
        }
    }

    private void setRouteAttributes(Attributes attributes) {
        route.setName(attributes.getValue(ATTR_ROUTE_OFFICIAL_NAME));
        route.setColorName(attributes.getValue(ATTR_ROUTE_UNOFFICIAL_NAME));
        // @TODO set color here
    }

    private void createLocation(Attributes attributes) {
        location = new Location("network");
        location.setLatitude(extractDouble(attributes, ATTR_COORDINATES_LATITUDE));
        location.setLongitude(extractDouble(attributes, ATTR_COORDINATES_LONGITUDE));
        location.setAltitude(extractDouble(attributes, ATTR_COORDINATES_ALTITUDE));
    }

    private double extractDouble(Attributes attributes, String name) {
        return Double.parseDouble(attributes.getValue(name));
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
