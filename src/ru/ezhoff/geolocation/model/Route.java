package ru.ezhoff.geolocation.model;

import android.graphics.Color;

import java.util.Map;

/**
 * @author e.ezhov
 * @version 1.0 17.05.13
 */
public class Route {

    private String name;
    private String colorName;
    // @TODO May be convert to int and create color constants
    private Color color;
    private Map<String, Station> stations;
}
