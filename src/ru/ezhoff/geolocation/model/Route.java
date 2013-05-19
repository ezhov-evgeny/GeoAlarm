package ru.ezhoff.geolocation.model;

import android.graphics.Color;

import java.util.HashMap;
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
    private Map<String, Station> stations = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Map<String, Station> getStations() {
        return stations;
    }

    public void setStations(Map<String, Station> stations) {
        this.stations = stations;
    }

    public void addStation(String id, Station station) {
        this.stations.put(id, station);
    }
}
