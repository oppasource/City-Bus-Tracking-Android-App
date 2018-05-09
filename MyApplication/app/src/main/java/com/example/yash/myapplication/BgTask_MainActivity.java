package com.example.yash.myapplication;

// Background tast file runs to get the nearest bus stop by sending latitude and logitude

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BgTask_MainActivity extends AsyncTask<String, Void, String> {
    private Activity activity;
    private TextView nearest;
    String stop_name = null;

    List<String> route_nos_up = new ArrayList<String>();
    List<String> last_stops_up = new ArrayList<String>();
    List<String> next_stops_up = new ArrayList<String>();

    List<String> route_nos_down = new ArrayList<String>();
    List<String> last_stops_down = new ArrayList<String>();
    List<String> next_stops_down = new ArrayList<String>();


    public BgTask_MainActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... arg) {
        String type = arg[0];

        if(type.equals("nearest")){
            try{
                double latitude =Double.parseDouble(arg[1]) ;
                double longitude = Double.parseDouble(arg[2]);

                String link= MainActivity.main_domain + "GetNearestBusStop.php";
                String data  = URLEncoder.encode("latitude", "UTF-8") + "=" +
                        URLEncoder.encode(String.valueOf(latitude), "UTF-8");
                data += "&" + URLEncoder.encode("longitude", "UTF-8") + "=" +
                        URLEncoder.encode(String.valueOf(longitude), "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                //setting the nearest stop name
                stop_name = sb.toString();

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }


        try{

            String link= MainActivity.main_domain + "getArrivingBuses.php";
            String data  = URLEncoder.encode("stop_name", "UTF-8") + "=" + URLEncoder.encode(stop_name, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    protected void onPostExecute(String result) {
        nearest = (TextView) activity.findViewById(R.id.BusStop);
        nearest.setText(stop_name);

        String r_num = "";
        String next = "";
        String last = "";
        String dir = "";

        try {
            JSONObject jsonResponse = new JSONObject(result);
            JSONArray cast = jsonResponse.getJSONArray("live_feed");

            for (int i = 0; i < cast.length(); i++) {
                JSONObject c = cast.getJSONObject(i);
                r_num = c.getString("route_number");
                next = c.getString("next_stop");
                last = c.getString("end_stop");
                dir = c.getString("direction");

                if(dir.equals("1")){
                    route_nos_up.add(r_num);
                    last_stops_up.add(last);
                    next_stops_up.add(next);
                }else{
                    route_nos_down.add(r_num);
                    last_stops_down.add(last);
                    next_stops_down.add(next);
                }
            }
        }catch(Exception e){}


        ListAdapter listAdapter = new CustomAdapterForMain(activity, route_nos_up, last_stops_up, next_stops_up);
        ListView listView1 = (ListView) activity.findViewById(R.id.routelist1);
        listView1.setAdapter(listAdapter);

        ListAdapter listAdapter2 = new CustomAdapterForMain(activity, route_nos_down, last_stops_down, next_stops_down);
        ListView listView2 = (ListView) activity.findViewById(R.id.routelist2);
        listView2.setAdapter(listAdapter2);
    }
}
