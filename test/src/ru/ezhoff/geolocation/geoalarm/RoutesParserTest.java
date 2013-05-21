package ru.ezhoff.geolocation.geoalarm;

import org.junit.Before;
import org.junit.Test;
import ru.ezhoff.geolocation.model.Route;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Evgeny Ezhov evgeny.ezhov@gmail.com
 * @version 1.0 19.05.13
 */
public class RoutesParserTest {

    private final static String STATIONS_XML_FILE = "test/resources/metro_stations.xml";

    private List<Route> routes;

    @Before
    public void setUp() throws Exception {
        //RoutesParser parser = new RoutesParser();
        //routes = parser.parse(STATIONS_XML_FILE);
        RoutesParserV2 parser = new RoutesParserV2();
        routes = parser.parse(new FileInputStream(new File(STATIONS_XML_FILE)));
    }

    @Test
    public void testParseRoutes() throws Exception {
        assertEquals(
                "Checks routes number",
                2,
                routes.size()
        );
    }

    @Test
    public void testParseStation() throws Exception {
        assertEquals(
                "Checks station name by id",
                "Отрадное",
                routes.get(0).getStations().get("gr03").getName()
        );
    }

    @Test
    public void testParseCoordinates() throws Exception {
        assertNotNull(
                "Checks location object exists",
                routes.get(0).getStations().get("gr03").getLocation()
        );
    }
}
