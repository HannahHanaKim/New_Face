package kr.newface.new_face.new_face;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //액션바 감추기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //2초 후 인트로 액티비티 제거
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(IntroActivity.this, Activity_login.class);
                startActivity(it);
                finish();
            }
        },500);
    }
}
