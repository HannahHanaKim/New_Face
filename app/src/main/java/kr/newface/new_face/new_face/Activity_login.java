package kr.newface.new_face.new_face;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bassaer.chatmessageview.models.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

import static kr.newface.new_face.new_face.MainActivity.my_id;

public class Activity_login extends AppCompatActivity {
    public static Activity fa;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private BufferedReader inFromServer;
    private BufferedWriter outToServer;
    private Socket clientSocket;
    private Handler mHandler;
    private String from_server_temp = null;
    static String studentID;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//이 코드가 없으면 인터넷 통신이 안됨
        fa = this;
        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ButterKnife.bind(this);
        init();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!validate()) {
                    return;
                }
                else {
                    try {
                        PrintWriter out = new PrintWriter(outToServer, true);
                        out.println("LOGIN");
                        out.println(_emailText.getText().toString() + " " + _passwordText.getText().toString());
                        //out.println();
                    } catch (Exception e) {

                    }
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Activity_signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    public boolean validate() {
        boolean valid = true;

        String name = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.length()!=9||name.isEmpty()) {
            _emailText.setError("학번을 입력해주십시오");
            valid = false;
        } else {
            studentID = name;
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("비밀번호를 입력해주십시오");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
    void init(){
        //Toast.makeText(getApplication(), "여기까지옴", Toast.LENGTH_SHORT).show();
        //getSupportActionBar().hide();
        //getSupportActionBar().setElevation(0);
        try{
            //나중에 켜야됨
            clientSocket = new Socket("192.9.13.79", 9002);
            inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            checkUpdate.start();
            outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            mHandler = new Handler();
            //clientSocket.close();
        }catch (Exception e) {
            Toast.makeText(getApplication(), "서버 오류", Toast.LENGTH_SHORT).show();
        }
    }

    private Thread checkUpdate = new Thread() {
        public void run() {
            try {
                while (true) {
                    from_server_temp = inFromServer.readLine();
                    mHandler.post(showUpdate);
                }
            } catch (Exception e) {
                //Log.w("error", "error");
            }
        }
    };

    private Runnable showUpdate = new Runnable() {
        public void run() {
            if (from_server_temp != null){
                Toast.makeText(getApplication(), from_server_temp, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        }
    };
}
