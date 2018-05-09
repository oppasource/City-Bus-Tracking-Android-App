package com.example.yash.myapplication;

//contains code to signin the conductor for type=login (runs in background, called from StartStopaAct)

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yash on 14/2/18.
 */

public class BackgroundTasks extends AsyncTask<String,Void,String> {
    public Activity activity;
    public TextView text;

    public BackgroundTasks(ConductorLogin activity) {

        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if(type.equals("login")) {
            try {
                String login_url = MainActivity.main_domain + "/conductorlogin.php";
                String user_name = params[1];
                String password = params[2];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+ URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {

        if(s.equals("login not success")) {
            text = (TextView) activity.findViewById(R.id.loginstatus);
            text.setTextColor(Color.RED);
            text.setText("Please try again");
        }else{
            Intent i = new Intent(activity,StartStopAct.class);
            i.putExtra("message", s);
            activity.startActivity(i);
        }




    }
}
