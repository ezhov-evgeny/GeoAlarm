package ru.ezhoff.geolocation;

import android.location.Location;

/**
 * @author Evgeny Ezhov evgeny.ezhov@gmail.com
 * @version 1.0 03.05.13
 */
public class Station {

    private Location location;
    private String name;
    private String brunch;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrunch() {
        return brunch;
    }

    public void setBrunch(String brunch) {
        this.brunch = brunch;
    }
}
