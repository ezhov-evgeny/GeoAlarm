package ru.ezhoff.geolocation.model;

import android.location.Location;

/**
 * @author e.ezhov
 * @version 1.0 17.05.13
 */
public class Station {
    private String id;
    private String name;
    private Location location;
    private String[] joins;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String[] getJoins() {
        return joins;
    }

    public void setJoins(String[] joins) {
        this.joins = joins;
    }
}
