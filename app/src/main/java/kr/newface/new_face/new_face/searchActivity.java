package kr.newface.new_face.new_face;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        search = new int[28];
        for(int i=0;i<search.length;i++){
            search[i]=0;
        }
        get_str = Activity_signup.send_str;

        final CheckBox s1 = (CheckBox)findViewById(R.id.s1);
        final CheckBox s2 = (CheckBox)findViewById(R.id.s2);
        final CheckBox s3 = (CheckBox)findViewById(R.id.s3);
        final CheckBox s4 = (CheckBox)findViewById(R.id.s4);
        final CheckBox s5 = (CheckBox)findViewById(R.id.s5);
        final CheckBox s6 = (CheckBox)findViewById(R.id.s6);
        final CheckBox s7 = (CheckBox)findViewById(R.id.s7);
        final CheckBox s8 = (CheckBox)findViewById(R.id.s8);
        final CheckBox s9 = (CheckBox)findViewById(R.id.s9);
        final CheckBox s10 = (CheckBox)findViewById(R.id.s10);
        final CheckBox s11 = (CheckBox)findViewById(R.id.s11);
        final CheckBox s12 = (CheckBox)findViewById(R.id.s12);
        final CheckBox s13 = (CheckBox)findViewById(R.id.s13);
        final CheckBox s14 = (CheckBox)findViewById(R.id.s14);
        final CheckBox s15 = (CheckBox)findViewById(R.id.s15);
        final CheckBox s16 = (CheckBox)findViewById(R.id.s16);
        final CheckBox s17 = (CheckBox)findViewById(R.id.s17);
        final CheckBox s18 = (CheckBox)findViewById(R.id.s18);
        final CheckBox s19 = (CheckBox)findViewById(R.id.s19);
        final CheckBox s20 = (CheckBox)findViewById(R.id.s20);
        final CheckBox s21 = (CheckBox)findViewById(R.id.s21);
        final CheckBox s22 = (CheckBox)findViewById(R.id.s22);
        final CheckBox s23 = (CheckBox)findViewById(R.id.s23);
        final CheckBox s24 = (CheckBox)findViewById(R.id.s24);
        final CheckBox s25 = (CheckBox)findViewById(R.id.s25);
        final CheckBox s26 = (CheckBox)findViewById(R.id.s26);
        final CheckBox s27 = (CheckBox)findViewById(R.id.s27);
        final CheckBox s28 = (CheckBox)findViewById(R.id.s28);
        Button btn = (Button)findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s1.isChecked()){search[0]=1;} else{search[0]=0;}
                if(s2.isChecked()){search[1]=1;} else{search[1]=0;}
                if(s3.isChecked()){search[2]=1;} else{search[2]=0;}
                if(s4.isChecked()){search[3]=1;} else{search[3]=0;}
                if(s5.isChecked()){search[4]=1;} else{search[4]=0;}
                if(s6.isChecked()){search[5]=1;} else{search[5]=0;}
                if(s7.isChecked()){search[6]=1;} else{search[6]=0;}
                if(s8.isChecked()){search[7]=1;} else{search[7]=0;}
                if(s9.isChecked()){search[8]=1;} else{search[8]=0;}
                if(s10.isChecked()){search[9]=1;} else{search[9]=0;}
                if(s11.isChecked()){search[10]=1;} else{search[10]=0;}
                if(s12.isChecked()){search[11]=1;} else{search[11]=0;}
                if(s13.isChecked()){search[12]=1;} else{search[12]=0;}
                if(s14.isChecked()){search[13]=1;} else{search[13]=0;}
                if(s15.isChecked()){search[14]=1;} else{search[14]=0;}
                if(s16.isChecked()){search[15]=1;} else{search[15]=0;}
                if(s17.isChecked()){search[16]=1;} else{search[16]=0;}
                if(s18.isChecked()){search[17]=1;} else{search[17]=0;}
                if(s19.isChecked()){search[18]=1;} else{search[18]=0;}
                if(s20.isChecked()){search[19]=1;} else{search[19]=0;}
                if(s21.isChecked()){search[20]=1;} else{search[20]=0;}
                if(s22.isChecked()){search[21]=1;} else{search[21]=0;}
                if(s23.isChecked()){search[22]=1;} else{search[22]=0;}
                if(s24.isChecked()){search[23]=1;} else{search[23]=0;}
                if(s25.isChecked()){search[24]=1;} else{search[24]=0;}
                if(s26.isChecked()){search[25]=1;} else{search[25]=0;}
                if(s27.isChecked()){search[26]=1;} else{search[26]=0;}
                if(s28.isChecked()){search[27]=1;} else{search[27]=0;}

                String arr ="";
                for(int i=0;i<search.length;i++){
                    arr = arr+String.valueOf(search[i]);
                }
                get_str = get_str + " " + arr;
                try {


                    PrintWriter out = new PrintWriter(outToServer, true);
                    out.println(get_str);

                    //out.println("\"" + my_id + "\"");
                } catch (Exception e) {

                }

                Intent it = new Intent(searchActivity.this, Activity_login.class);
                startActivity(it);
            }
        });


    }
    void init(){
        getSupportActionBar().hide();
        getSupportActionBar().setElevation(0);
        try{
            //나중에 켜야됨
            clientSocket = new Socket("192.9.128.170", 9001);

            outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            mHandler = new Handler();


            //clientSocket.close();
        }catch (Exception e){
            Toast.makeText(getApplication(), "서버 오류", Toast.LENGTH_SHORT).show();
        }

    }

}