package com.example.yash.myapplication;

//Activity of conductor login screen

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConductorLogin extends AppCompatActivity {

    Button button;
    EditText user,pass;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_login);
        button = (Button) findViewById(R.id.Login);
        user = (EditText) findViewById(R.id.user_name);
        pass = (EditText) findViewById(R.id.password);
        status = (TextView) findViewById(R.id.loginstatus);

    }

    public void onLogin(View view) {
        String username = user.getText().toString();
        String password = pass.getText().toString();
        String type = "login";
        status.setTextColor(Color.BLACK);
        status.setText("Please Wait");

        BackgroundTasks backgroundTask = new BackgroundTasks(this);
        backgroundTask.execute(type, username, password);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status.setText("");
    }
}
