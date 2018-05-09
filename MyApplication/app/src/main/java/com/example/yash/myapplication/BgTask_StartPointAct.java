package com.example.yash.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RadioButton;
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
import java.util.HashMap;

/**
 * Created by yash on 17/2/18.
 */

public class BgTask_StartPointAct extends AsyncTask<String,Void,String> {
    Activity activity;
    String type;
    RadioButton radiobutton1;
    RadioButton radiobutton2;

    public BgTask_StartPointAct(StartPointAct activity)  {
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... params) {
        type = params[0];

        if(type.equals("getorder")) {
            try {
                String urls = MainActivity.main_domain + "getRouteOrder.php";
                String route = params[1];

                URL url = new URL(urls);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("route_number","UTF-8")+"="+ URLEncoder.encode(route,"UTF-8");
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
        }else if(type.equals("start")) {
            try {

                String urls = MainActivity.main_domain + "setlive.php";
                String id = params[1];
                String route_number = params[2];
                String seletedstop = params[3];
                String v_num = params[4];
                String end_point = params[5];


                URL url = new URL(urls);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //copy the POST variable names from here
                String post_data = URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("route_number","UTF-8")+"="+ URLEncoder.encode(route_number,"UTF-8")+"&"
                        +URLEncoder.encode("seletedstop","UTF-8")+"="+ URLEncoder.encode(seletedstop,"UTF-8")+"&"
                        +URLEncoder.encode("v_num","UTF-8")+"="+ URLEncoder.encode(v_num,"UTF-8")+"&"
                        +URLEncoder.encode("end_point","UTF-8")+"="+ URLEncoder.encode(end_point,"UTF-8");

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
                return id;

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String stop_name;
        String first;
        String last;
        Integer order;
        HashMap< Integer, String> name_order = new HashMap<>();

        if(type.equals("getorder")) {
            try {
                JSONObject jsonResponse = new JSONObject(s);
                JSONArray cast = jsonResponse.getJSONArray("response");

                for (int i = 0; i < cast.length(); i++) {
                    JSONObject c = cast.getJSONObject(i);
                    stop_name = c.getString("bus_stop_name");
                    order = Integer.parseInt(c.getString("stop_order"));

                    name_order.put(order,stop_name);

                }
            }catch (Exception e){}

            radiobutton1 = (RadioButton) activity.findViewById(R.id.radioButton1);
            radiobutton2 = (RadioButton) activity.findViewById(R.id.radioButton2);
            radiobutton1.setText(name_order.get(1));
            radiobutton2.setText(name_order.get(name_order.size()));
            radiobutton1.setVisibility(View.VISIBLE);
            radiobutton2.setVisibility(View.VISIBLE);

        }else if(type.equals("start")) {
            //code to start a new activity (ConductorLiveAct)
            Intent in = new Intent(activity,ConductorLiveAct.class);
            in.putExtra("conductor_id", s);
            activity.startActivity(in);
            activity.finish();

        }

    }
}
