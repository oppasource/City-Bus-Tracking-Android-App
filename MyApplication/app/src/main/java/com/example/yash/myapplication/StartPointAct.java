package com.example.yash.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StartPointAct extends AppCompatActivity {
    private RadioButton selected;
    private RadioGroup radiogroup;
    private EditText vehicle_num;
    RadioButton radioButton1;
    RadioButton radioButton2;
    Button start;
    String route_number, id, v_num;
    String stop1,stop2,end_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_point);

        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton1.setVisibility(View.INVISIBLE);
        radioButton2.setVisibility(View.INVISIBLE);
        vehicle_num = (EditText) findViewById(R.id.vehicle_num);
        start = (Button) findViewById(R.id.start);


        vehicle_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(((count == 2) && (before == 1))  || ((count == 5) && (before == 4))  ||  ((count == 8) && (before == 7)) ){
                    vehicle_num.append("-");
                }
                if( ((count == 2) && (before == 3))  || ((count == 5) && (before == 6))  ||  ((count == 8) && (before == 9)) ){
                    vehicle_num.setText(s.subSequence(0,s.length() -1));
                    vehicle_num.setSelection(vehicle_num.getText().length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });



        route_number = getIntent().getExtras().getString("route_number").toString();

        String type = "getorder";
        BgTask_StartPointAct bgtask = new BgTask_StartPointAct(this);
        bgtask.execute(type, route_number);


        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stop1 = (String) radioButton1.getText();
                stop2 = (String) radioButton2.getText();

                // get selected radio button from radioGroup
                int selectedId = radiogroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                selected = (RadioButton) findViewById(selectedId);
                String seletedstop = (String) selected.getText();

                if(stop1.equals(seletedstop)){
                    end_point = stop2;
                }else{
                    end_point = stop1;
                }

                StartStopAct getid= new StartStopAct();
                id = getid.id;

                v_num = vehicle_num.getText().toString();

                String type = "start";
                BgTask_StartPointAct starting = new BgTask_StartPointAct(StartPointAct.this);
                starting.execute(type, id, route_number, seletedstop,v_num, end_point);

            }

        });
    }
}
