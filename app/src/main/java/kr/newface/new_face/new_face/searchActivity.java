package kr.newface.new_face.new_face;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Arrays;

public class searchActivity extends AppCompatActivity {
    String data;
    String arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final int[] search = new int[28];
        Button btn = (Button)findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(searchActivity.this, Activity_login.class);
                startActivity(it);
            }
        });

        CheckBox s1 = (CheckBox)findViewById(R.id.s1);
        CheckBox s2 = (CheckBox)findViewById(R.id.s2);
        CheckBox s3 = (CheckBox)findViewById(R.id.s3);
        CheckBox s4 = (CheckBox)findViewById(R.id.s4);
        CheckBox s5 = (CheckBox)findViewById(R.id.s5);
        CheckBox s6 = (CheckBox)findViewById(R.id.s6);
        CheckBox s7 = (CheckBox)findViewById(R.id.s7);
        CheckBox s8 = (CheckBox)findViewById(R.id.s8);
        CheckBox s9 = (CheckBox)findViewById(R.id.s9);
        CheckBox s10 = (CheckBox)findViewById(R.id.s10);
        CheckBox s11 = (CheckBox)findViewById(R.id.s11);
        CheckBox s12 = (CheckBox)findViewById(R.id.s12);
        CheckBox s13 = (CheckBox)findViewById(R.id.s13);
        CheckBox s14 = (CheckBox)findViewById(R.id.s14);
        CheckBox s15 = (CheckBox)findViewById(R.id.s15);
        CheckBox s16 = (CheckBox)findViewById(R.id.s16);
        CheckBox s17 = (CheckBox)findViewById(R.id.s17);
        CheckBox s18 = (CheckBox)findViewById(R.id.s18);
        CheckBox s19 = (CheckBox)findViewById(R.id.s19);
        CheckBox s20 = (CheckBox)findViewById(R.id.s20);
        CheckBox s21 = (CheckBox)findViewById(R.id.s21);
        CheckBox s22 = (CheckBox)findViewById(R.id.s22);
        CheckBox s23 = (CheckBox)findViewById(R.id.s23);
        CheckBox s24 = (CheckBox)findViewById(R.id.s24);
        CheckBox s25 = (CheckBox)findViewById(R.id.s25);
        CheckBox s26 = (CheckBox)findViewById(R.id.s26);
        CheckBox s27 = (CheckBox)findViewById(R.id.s27);
        CheckBox s28 = (CheckBox)findViewById(R.id.s28);

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
    }
}
