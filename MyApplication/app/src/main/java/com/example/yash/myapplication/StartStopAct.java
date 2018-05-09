package com.example.yash.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class StartStopAct extends AppCompatActivity {

    TextView message_view;
     public static String id; //this is conductor_id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stop);

        message_view = (TextView) findViewById(R.id.messageid);
        id = getIntent().getExtras().getString("message").toString();

        String type = "getname";
        BgTask_StartStopAct bgtask = new BgTask_StartStopAct(this);
        bgtask.execute(type, id);

        type = "getJSON";
        BgTask_StartStopAct getjson = new BgTask_StartStopAct(this);
        getjson.execute(type);
    }

}
