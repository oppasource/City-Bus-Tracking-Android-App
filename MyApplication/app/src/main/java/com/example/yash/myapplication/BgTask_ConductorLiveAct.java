package com.example.yash.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yash on 19/2/18.
 */

public class BgTask_ConductorLiveAct extends AsyncTask<String,Void,String> {
    Activity activity;
    String type = "", route_number = "", v_num = "", next_stop_order = "";
    String conductor_id, conductor_name, first,last,next_stop_name,lat,longi;
    public TextView message, r_number,v_number,start_point, end_point, next_stop;

    public BgTask_ConductorLiveAct(ConductorLiveAct activity)  {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];

        if(type.equals("insertinfo")) {
            try {
                String urls = MainActivity.main_domain + "getLiveInfo.php";
                conductor_id = params[1];

                URL url = new URL(urls);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("conductor_id","UTF-8")+"="+ URLEncoder.encode(conductor_id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line = "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }else if(type.equals("end")) {
            try {
                String urls = MainActivity.main_domain + "deleteFromLive.php";
                conductor_id = params[1];

                URL url = new URL(urls);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("conductor_id","UTF-8")+"="+ URLEncoder.encode(conductor_id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line = "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }else if(type.equals("updateinfo")) {
            try {
                String urls = MainActivity.main_domain + "updateLive.php";
                lat = params[1];
                longi = params[2];
                conductor_id = params[3];

                URL url = new URL(urls);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("lat","UTF-8")+"="+ URLEncoder.encode(lat,"UTF-8")+"&"
                        +URLEncoder.encode("long","UTF-8")+"="+ URLEncoder.encode(longi,"UTF-8")+"&"
                        +URLEncoder.encode("conductor_id","UTF-8")+"="+ URLEncoder.encode(conductor_id ,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line = "";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(type.equals("insertinfo")) {

            message = (TextView) activity.findViewById(R.id.message);
            r_number = (TextView) activity.findViewById(R.id.route_num);
            v_number = (TextView) activity.findViewById(R.id.vehicle_num);
            start_point = (TextView) activity.findViewById(R.id.start_point);
            end_point = (TextView) activity.findViewById(R.id.end_point);
            next_stop = (TextView) activity.findViewById(R.id.next_stop);


            try{
                JSONObject jsonResponse = new JSONObject(s);
                JSONArray cast = jsonResponse.getJSONArray("live_info");
                JSONObject c = cast.getJSONObject(0);
                route_number = c.getString("route_number");
                v_num = c.getString("vehicle_number");
                next_stop_order = c.getString("next_stop_order");
                conductor_name = c.getString("conductor_name");
                first = c.getString("start_stop");
                last = c.getString("end_stop");
                next_stop_name = c.getString("next_stop");
            }catch(Exception e){}

            message.setText("Hello, " + conductor_name);
            r_number.append(route_number);
            v_number.append(v_num);
            next_stop.append(next_stop_name);
            start_point.append(first);
            end_point.append(last);

        }else if(type.equals("updateinfo")) {

            next_stop = (TextView) activity.findViewById(R.id.next_stop);
            next_stop.setText("Next Stop - " + s);

        }else if(type.equals("end")){
            activity.finish();

        }


    }
}
