package com.example.yash.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yash on 16/2/18.
 */

public class BgTask_StartStopAct extends AsyncTask<String,Void,String> {


    public Activity activity;
    public TextView welcome,message;
    public String type;
    ArrayList<HashMap<String, String>> contactList;
    private ListView lv;
    public String json_string = "";

    public BgTask_StartStopAct(StartStopAct activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        contactList = new ArrayList<>();
        lv = (ListView) activity.findViewById(R.id.bus_number_options);
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];

        if(type.equals("getname")) {
            try {
                String urls = MainActivity.main_domain + "getConductorName.php";
                String id = params[1];

                URL url = new URL(urls);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");
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
        }else if(type.equals("getJSON")){
            try {
                String login_url = MainActivity.main_domain + "getBusNumbersJSON.php";
                String json = "";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                while ((json = bufferedReader.readLine()) != null){
                    sb.append(json + '\n');
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        if(type.equals("getname")) {
            welcome = (TextView) activity.findViewById(R.id.messageid);
            message = (TextView) activity.findViewById(R.id.message);
            welcome.setText("Welcome, " + s + " Master"  );
            message.setText("Select a route number to start");

        }else if(type.equals("getJSON")) {
            json_string = s;
            String num ="";
            try {
                JSONObject jsonResponse = new JSONObject(s);
                JSONArray cast = jsonResponse.getJSONArray("bus_numbers");

                for (int i = 0; i < cast.length(); i++) {
                    JSONObject c = cast.getJSONObject(i);
                    num = c.getString("bus_number");

                    HashMap<String, String> contact = new HashMap<>();

                    contact.put("num", num);

                    // adding contact to contact list
                    contactList.add(contact);
                }


            }catch(Exception e){}


            ListAdapter adapter = new SimpleAdapter(activity, contactList, R.layout.list_item, new String[]{"num"}, new int[]{R.id.num});
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String temp = "";

                    try {
                        JSONObject jsonResponse = new JSONObject(json_string);
                        JSONArray cast = jsonResponse.getJSONArray("bus_numbers");


                        JSONObject c = cast.getJSONObject(i);
                        temp = c.getString("bus_number");

                    }catch(Exception e){}

                    Intent in = new Intent(activity,StartPointAct.class);
                    in.putExtra("route_number", temp);
                    activity.startActivity(in);

                    //Toast.makeText(activity.getApplicationContext(), temp, Toast.LENGTH_LONG).show();
                }
            });

        }

    }
}