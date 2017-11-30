package kr.newface.new_face.new_face;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import static kr.newface.new_face.new_face.MainActivity.my_id;

public class searchActivity extends AppCompatActivity {
    int[] search ;
    String get_str;
    private BufferedWriter outToServer;
    private Socket clientSocket;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //init();
        final int[] q1 = new int[4];
        final int[] q2 = new int[6];
        final int[] q3 = new int[3];
        final int[] q4 = new int[6];
        final int[] q5 = new int[4];
        final int[] q6 = new int[4];
        final int[] q7 = new int[2];
        final int[] q8 = new int[2];
        final int[] q9 = new int[8];
        final int[] q10 = new int[4];

        get_str = Activity_signup.send_str;

        final CheckBox q1_1 = (CheckBox)findViewById(R.id.q1_1);
        final CheckBox q1_2 = (CheckBox)findViewById(R.id.q1_2);
        final CheckBox q1_3 = (CheckBox)findViewById(R.id.q1_3);
        final CheckBox q1_4 = (CheckBox)findViewById(R.id.q1_4);
        final CheckBox q1_no = (CheckBox)findViewById(R.id.q1_no);
        final CheckBox q1_all = (CheckBox)findViewById(R.id.q1_all);
        final CheckBox q2_1 = (CheckBox)findViewById(R.id.q2_1);
        final CheckBox q2_2 = (CheckBox)findViewById(R.id.q2_2);
        final CheckBox q2_3 = (CheckBox)findViewById(R.id.q2_3);
        final CheckBox q2_4 = (CheckBox)findViewById(R.id.q2_4);
        final CheckBox q2_5 = (CheckBox)findViewById(R.id.q2_5);
        final CheckBox q2_6 = (CheckBox)findViewById(R.id.q2_6);
        final CheckBox q2_no = (CheckBox)findViewById(R.id.q2_no);
        final CheckBox q2_all = (CheckBox)findViewById(R.id.q2_all);
        final CheckBox q3_1 = (CheckBox)findViewById(R.id.q3_1);
        final CheckBox q3_2 = (CheckBox)findViewById(R.id.q3_2);
        final CheckBox q3_3 = (CheckBox)findViewById(R.id.q3_3);
        final CheckBox q3_all = (CheckBox)findViewById(R.id.q3_all);
        final CheckBox q4_1 = (CheckBox)findViewById(R.id.q4_1);
        final CheckBox q4_2 = (CheckBox)findViewById(R.id.q4_2);
        final CheckBox q4_3 = (CheckBox)findViewById(R.id.q4_3);
        final CheckBox q4_4 = (CheckBox)findViewById(R.id.q4_4);
        final CheckBox q4_5 = (CheckBox)findViewById(R.id.q4_5);
        final CheckBox q4_6 = (CheckBox)findViewById(R.id.q4_6);
        final CheckBox q4_no = (CheckBox)findViewById(R.id.q4_no);
        final CheckBox q4_all = (CheckBox)findViewById(R.id.q4_all);
        final CheckBox q5_1 = (CheckBox)findViewById(R.id.q5_1);
        final CheckBox q5_2 = (CheckBox)findViewById(R.id.q5_2);
        final CheckBox q5_3 = (CheckBox)findViewById(R.id.q5_3);
        final CheckBox q5_4 = (CheckBox)findViewById(R.id.q5_4);
        final CheckBox q6_1 = (CheckBox)findViewById(R.id.q6_1);
        final CheckBox q6_2 = (CheckBox)findViewById(R.id.q6_2);
        final CheckBox q6_3 = (CheckBox)findViewById(R.id.q6_3);
        final CheckBox q6_4 = (CheckBox)findViewById(R.id.q6_4);
        final CheckBox q6_no = (CheckBox)findViewById(R.id.q6_no);
        final CheckBox q6_all = (CheckBox)findViewById(R.id.q6_all);
        final CheckBox q7_1 = (CheckBox)findViewById(R.id.q7_1);
        final CheckBox q7_2 = (CheckBox)findViewById(R.id.q7_2);
        final CheckBox q8_1 = (CheckBox)findViewById(R.id.q8_1);
        final CheckBox q8_2= (CheckBox)findViewById(R.id.q8_2);
        final CheckBox q8_no = (CheckBox)findViewById(R.id.q8_no);
        final CheckBox q9_1 = (CheckBox)findViewById(R.id.q9_1);
        final CheckBox q9_2 = (CheckBox)findViewById(R.id.q9_2);
        final CheckBox q9_3 = (CheckBox)findViewById(R.id.q9_3);
        final CheckBox q9_4 = (CheckBox)findViewById(R.id.q9_4);
        final CheckBox q9_5 = (CheckBox)findViewById(R.id.q9_5);
        final CheckBox q9_6 = (CheckBox)findViewById(R.id.q9_6);
        final CheckBox q9_7 = (CheckBox)findViewById(R.id.q9_7);
        final CheckBox q9_8 = (CheckBox)findViewById(R.id.q9_8);
        final CheckBox q9_no = (CheckBox)findViewById(R.id.q9_no);
        final CheckBox q9_all = (CheckBox)findViewById(R.id.q9_all);
        final CheckBox q10_1 = (CheckBox)findViewById(R.id.q10_1);
        final CheckBox q10_2 = (CheckBox)findViewById(R.id.q10_2);
        final CheckBox q10_3 = (CheckBox)findViewById(R.id.q10_3);
        final CheckBox q10_4= (CheckBox)findViewById(R.id.q10_4);
        final CheckBox q10_no = (CheckBox)findViewById(R.id.q10_no);
        final CheckBox q10_all = (CheckBox)findViewById(R.id.q10_all);

        q1_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q1_1){
                    q1_no.setChecked(false);
                    q1_all.setChecked(false);
                }
            }
        });
        q1_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q1_2){
                    q1_no.setChecked(false);
                    q1_all.setChecked(false);
                }
            }
        });
        q1_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q1_3){
                    q1_no.setChecked(false);
                    q1_all.setChecked(false);
                }
            }
        });
        q1_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q1_4){
                    q1_no.setChecked(false);
                    q1_all.setChecked(false);
                }
            }
        });
        q1_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q1_no){
                    if(q1_no.isChecked()){
                        q1_1.setChecked(false);
                        q1_2.setChecked(false);
                        q1_3.setChecked(false);
                        q1_4.setChecked(false);
                        q1_all.setChecked(false);
                        q1_no.setChecked(true);
                    }
                }
            }
        });
        q1_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q1_all){
                    if(q1_all.isChecked()){
                        q1_1.setChecked(true);
                        q1_2.setChecked(true);
                        q1_3.setChecked(true);
                        q1_4.setChecked(true);
                        q1_no.setChecked(false);
                        q1_all.setChecked(true);
                    }
                }
            }
        });
        q2_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q2_no){
                    if(q2_no.isChecked()){
                        q2_1.setChecked(false);
                        q2_2.setChecked(false);
                        q2_3.setChecked(false);
                        q2_4.setChecked(false);
                        q2_5.setChecked(false);
                        q2_6.setChecked(false);
                        q2_no.setChecked(true);
                        q2_all.setChecked(false);
                    }
                }
            }
        });
        q2_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q2_all){
                    if(q2_all.isChecked()){
                        q2_1.setChecked(true);
                        q2_2.setChecked(true);
                        q2_3.setChecked(true);
                        q2_4.setChecked(true);
                        q2_5.setChecked(true);
                        q2_6.setChecked(true);
                        q2_all.setChecked(true);
                        q2_no.setChecked(false);
                    }
                }
            }
        });
        q2_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q2_1){
                    q2_no.setChecked(false);
                    q2_all.setChecked(false);
                }
            }
        });
        q2_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q2_2){
                    q2_no.setChecked(false);
                    q2_all.setChecked(false);
                }
            }
        });
        q2_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q2_3){
                    q2_no.setChecked(false);
                    q2_all.setChecked(false);
                }
            }
        });
        q2_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q2_4){
                    q2_no.setChecked(false);
                    q2_all.setChecked(false);
                }
            }
        });
        q2_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q2_5){
                    q2_no.setChecked(false);
                    q2_all.setChecked(false);
                }
            }
        });
        q2_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q2_6){
                    q2_no.setChecked(false);
                    q2_all.setChecked(false);
                }
            }
        });

        q3_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q3_all){
                    if(q3_all.isChecked()){
                        q3_1.setChecked(true);
                        q3_2.setChecked(true);
                        q3_3.setChecked(true);
                        q3_all.setChecked(true);
                    }
                    else{
                        q3_1.setChecked(false);
                        q3_2.setChecked(false);
                        q3_3.setChecked(false);
                        q3_all.setChecked(false);
                    }
                }
            }
        });
        q3_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q3_1){
                    q3_all.setChecked(false);
                }
            }
        });
        q3_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q3_2){
                    q3_all.setChecked(false);
                }
            }
        });
        q3_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q3_3){
                    q3_all.setChecked(false);
                }
            }
        });
        q4_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q4_no){
                    if(q4_no.isChecked()){
                        q4_1.setChecked(false);
                        q4_2.setChecked(false);
                        q4_3.setChecked(false);
                        q4_4.setChecked(false);
                        q4_5.setChecked(false);
                        q4_6.setChecked(false);
                        q4_no.setChecked(true);
                        q4_all.setChecked(false);
                    }
                }
            }
        });
        q4_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q4_all){
                    if(q4_all.isChecked()){
                        q4_1.setChecked(true);
                        q4_2.setChecked(true);
                        q4_3.setChecked(true);
                        q4_4.setChecked(true);
                        q4_5.setChecked(true);
                        q4_6.setChecked(true);
                        q4_no.setChecked(false);
                        q4_all.setChecked(true);
                    }
                }
            }
        });
        q4_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q4_1){
                    q4_no.setChecked(false);
                    q4_all.setChecked(false);
                }
            }
        });
        q4_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q4_2){
                    q4_no.setChecked(false);
                    q4_all.setChecked(false);
                }
            }
        });
        q4_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q4_3){
                    q4_no.setChecked(false);
                    q4_all.setChecked(false);
                }
            }
        });
        q4_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q4_4){
                    q4_no.setChecked(false);
                    q4_all.setChecked(false);
                }
            }
        });
        q4_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q4_5){
                    q4_no.setChecked(false);
                    q4_all.setChecked(false);
                }
            }
        });
        q4_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q4_6){
                    q4_no.setChecked(false);
                    q4_all.setChecked(false);
                }
            }
        });


        q6_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q6_no){
                    if(q6_no.isChecked()){
                        q6_1.setChecked(false);
                        q6_2.setChecked(false);
                        q6_3.setChecked(false);
                        q6_4.setChecked(false);
                        q6_all.setChecked(false);
                        q6_no.setChecked(true);
                    }
                }
            }
        });
        q6_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q6_all){
                    if(q6_all.isChecked()){
                        q6_1.setChecked(true);
                        q6_2.setChecked(true);
                        q6_3.setChecked(true);
                        q6_4.setChecked(true);
                        q6_all.setChecked(true);
                        q6_no.setChecked(false);
                    }
                }
            }
        });
        q6_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q6_1){
                    q6_no.setChecked(false);
                    q6_all.setChecked(false);
                }
            }
        });
        q6_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q6_2){
                    q6_no.setChecked(false);
                    q6_all.setChecked(false);
                }
            }
        });
        q6_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q6_3){
                    q6_no.setChecked(false);
                    q6_all.setChecked(false);
                }
            }
        });
        q6_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q6_4){
                    q6_no.setChecked(false);
                    q6_all.setChecked(false);
                }
            }
        });

        q8_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q8_no){
                    if(q8_no.isChecked()){
                        q8_1.setChecked(false);
                        q8_2.setChecked(false);
                        q8_no.setChecked(true);
                    }
                }
            }
        });
        q8_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q8_1){
                    q8_no.setChecked(false);
                }
            }
        });
        q8_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q8_2){
                    q8_no.setChecked(false);
                }
            }
        });
        q9_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q9_no){
                    if(q9_no.isChecked()){
                        q9_1.setChecked(false);
                        q9_2.setChecked(false);
                        q9_3.setChecked(false);
                        q9_4.setChecked(false);
                        q9_5.setChecked(false);
                        q9_6.setChecked(false);
                        q9_7.setChecked(false);
                        q9_8.setChecked(false);
                        q9_all.setChecked(false);
                        q9_no.setChecked(true);
                    }
                }
            }
        });
        q9_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q9_all){
                    if(q9_all.isChecked()){
                        q9_1.setChecked(true);
                        q9_2.setChecked(true);
                        q9_3.setChecked(true);
                        q9_4.setChecked(true);
                        q9_5.setChecked(true);
                        q9_6.setChecked(true);
                        q9_7.setChecked(true);
                        q9_8.setChecked(true);
                        q9_all.setChecked(true);
                        q9_no.setChecked(false);
                    }
                }
            }
        });
        q9_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_1){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_2){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_3){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_4){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_5){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_6){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_7){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q9_8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q9_8){
                    q9_no.setChecked(false);
                    q9_all.setChecked(false);
                }
            }
        });
        q10_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q10_no){
                    if(q10_no.isChecked()){
                        q10_1.setChecked(false);
                        q10_2.setChecked(false);
                        q10_3.setChecked(false);
                        q10_4.setChecked(false);
                        q10_all.setChecked(false);
                        q10_no.setChecked(true);
                    }
                }
            }
        });
        q10_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.q10_all){
                    if(q10_all.isChecked()){
                        q10_1.setChecked(true);
                        q10_2.setChecked(true);
                        q10_3.setChecked(true);
                        q10_4.setChecked(true);
                        q10_no.setChecked(false);
                        q10_all.setChecked(true);
                    }
                }
            }
        });
        q10_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q10_1){
                    q10_no.setChecked(false);
                    q10_all.setChecked(false);
                }
            }
        });
        q10_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q10_2){
                    q10_no.setChecked(false);
                    q10_all.setChecked(false);
                }
            }
        });
        q10_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q10_3){
                    q10_no.setChecked(false);
                    q10_all.setChecked(false);
                }
            }
        });
        q10_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {@Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.q10_4){
                    q10_no.setChecked(false);
                    q10_all.setChecked(false);}}});
        Button btn = (Button)findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(q1_1.isChecked()){q1[0]=1;}else{q1[0]=0;}
                if(q1_2.isChecked()){q1[1]=1;}else{q1[1]=0;}
                if(q1_3.isChecked()){q1[2]=1;}else{q1[2]=0;}
                if(q1_4.isChecked()){q1[3]=1;}else{q1[3]=0;}
                if(q2_1.isChecked()){q2[0]=1;}else{q2[0]=0;}
                if(q2_2.isChecked()){q2[1]=1;}else{q2[1]=0;}
                if(q2_3.isChecked()){q2[2]=1;}else{q2[2]=0;}
                if(q2_4.isChecked()){q2[3]=1;}else{q2[3]=0;}
                if(q2_4.isChecked()){q2[4]=1;}else{q2[4]=0;}
                if(q2_5.isChecked()){q2[5]=1;}else{q2[5]=0;}
                if(q3_1.isChecked()){q3[0]=1;}else{q3[0]=0;}
                if(q3_2.isChecked()){q3[1]=1;}else{q3[1]=0;}
                if(q3_3.isChecked()){q3[2]=1;}else{q3[2]=0;}
                if(q4_1.isChecked()){q4[0]=1;}else{q4[0]=0;}
                if(q4_2.isChecked()){q4[1]=1;}else{q4[1]=0;}
                if(q4_3.isChecked()){q4[2]=1;}else{q4[2]=0;}
                if(q4_4.isChecked()){q4[3]=1;}else{q4[3]=0;}
                if(q4_5.isChecked()){q4[4]=1;}else{q4[4]=0;}
                if(q4_6.isChecked()){q4[5]=1;}else{q4[5]=0;}
                if(q5_1.isChecked()){q5[0]=1;}else{q5[0]=0;}
                if(q5_2.isChecked()){q5[1]=1;}else{q5[1]=0;}
                if(q5_3.isChecked()){q5[2]=1;}else{q5[2]=0;}
                if(q5_4.isChecked()){q5[3]=1;}else{q5[3]=0;}
                if(q6_1.isChecked()){q6[0]=1;}else{q6[0]=0;}
                if(q6_2.isChecked()){q6[1]=1;}else{q6[1]=0;}
                if(q6_3.isChecked()){q6[2]=1;}else{q6[2]=0;}
                if(q6_4.isChecked()){q6[3]=1;}else{q6[3]=0;}
                if(q7_1.isChecked()){q7[0]=1;}else{q7[0]=0;}
                if(q7_2.isChecked()){q7[1]=1;}else{q7[1]=0;}
                if(q8_1.isChecked()){q8[0]=1;}else{q8[0]=0;}
                if(q8_2.isChecked()){q8[1]=1;}else{q8[1]=0;}
                if(q9_1.isChecked()){q9[0]=1;}else{q9[0]=0;}
                if(q9_2.isChecked()){q9[1]=1;}else{q9[1]=0;}
                if(q9_3.isChecked()){q9[2]=1;}else{q9[2]=0;}
                if(q9_4.isChecked()){q9[3]=1;}else{q9[3]=0;}
                if(q9_5.isChecked()){q9[4]=1;}else{q9[4]=0;}
                if(q9_6.isChecked()){q9[5]=1;}else{q9[5]=0;}
                if(q9_7.isChecked()){q9[4]=1;}else{q9[6]=0;}
                if(q9_8.isChecked()){q9[5]=1;}else{q9[7]=0;}
                if(q10_1.isChecked()){q10[0]=1;}else{q10[0]=0;}
                if(q10_2.isChecked()){q10[1]=1;}else{q10[1]=0;}
                if(q10_3.isChecked()){q10[2]=1;}else{q10[2]=0;}
                if(q10_4.isChecked()){q10[3]=1;}else{q10[3]=0;}
                String arr = transString(q1)+ transString(q2)+ transString(q3)+transString(q4)+transString(q5)+transString(q6)+transString(q7)+transString(q8)+transString(q9)+transString(q10);
                get_str = get_str+" "+arr;
                Toast.makeText(getApplicationContext(),get_str,Toast.LENGTH_LONG).show();
                /*try {
                    PrintWriter out = new PrintWriter(outToServer, true);
                    out.println(get_str);
                    //out.println("\"" + my_id + "\"");
                } catch (Exception e) {}
                Intent it = new Intent(searchActivity.this, Activity_login.class);
                startActivity(it);*/
            }
        });

    }
    void init(){
        getSupportActionBar().hide();
        getSupportActionBar().setElevation(0);
        try{
            //나중에 켜야됨
            clientSocket = new Socket("192.9.13.79", 9002);
            outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            mHandler = new Handler();
            //clientSocket.close();
        }catch (Exception e){
            Toast.makeText(getApplication(), "서버 오류", Toast.LENGTH_SHORT).show();
        }
    }
    public String transString(int[] arr){
        String arrString = "";
        for(int i=0;i<arr.length;i++){
            arrString+=arr[i];
        }
        return arrString;
    }
}