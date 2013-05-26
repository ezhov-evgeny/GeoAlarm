package ru.ezhoff.geolocation.geoalarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ru.ezhoff.geolocation.model.Station;

import java.util.List;

/**
 * @author Evgeny Ezhov evgeny.ezhov@gmail.com
 * @version 1.0 26.05.13
 */
public class StationArrayAdapter extends ArrayAdapter<Station> {

    List<Station> stations;

    public StationArrayAdapter(Context context, int textViewResourceId, List<Station> objects) {
        super(context, textViewResourceId, objects);
        stations = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(getContext());
        textView.setText(stations.get(position).getName());
        return textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(getContext());
        textView.setText(stations.get(position).getName());
        return textView;
    }
}
