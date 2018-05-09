package com.example.yash.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yash on 23/2/18.
 */

public class CustomAdapterForMain extends ArrayAdapter<String> {

    List<String> route_nos = new ArrayList<String>();
    List<String> last_stops = new ArrayList<String>();
    List<String> next_stops = new ArrayList<String>();

    private Activity context;
    public CustomAdapterForMain(Activity context, List<String> route_nos, List<String> last_stops, List<String> next_stops) {
        super(context, R.layout.list_item_user_main, route_nos);

        this.context = context;
        this.route_nos = route_nos;
        this.last_stops = last_stops;
        this.next_stops = next_stops;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_user_main, null, true);
        TextView route = (TextView) listViewItem.findViewById(R.id.RouteNos);
        TextView next = (TextView) listViewItem.findViewById(R.id.NextStops);
        TextView last = (TextView) listViewItem.findViewById(R.id.LastStops);

        route.setText(route_nos.get(position));
        next.setText(next_stops.get(position));
        last.setText(last_stops.get(position));

        return  listViewItem;
    }
}
