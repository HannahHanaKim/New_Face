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
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static kr.newface.new_face.new_face.MainActivity.my_id;

public class Activity_login extends AppCompatActivity {
    public static Activity fa;
    public static String my_frineds = "";
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private BufferedReader inFromServer;
    private BufferedWriter outToServer;
    private Socket clientSocket;
    private Handler mHandler;
    private String from_server_temp = null;
    static String studentID;
    static String login_ip = "";
    static String chat_ip = "";

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

        login_ip = gethttp2("https://github.com/iveinvalue/New_Face/blob/master/login.txt").split(" <td id=\"LC1\" class=\"blob-code blob-code-inner js-file-line\">")[1].split("</td>")[0];
        chat_ip  = gethttp2("https://github.com/iveinvalue/New_Face/blob/master/chat.txt").split(" <td id=\"LC1\" class=\"blob-code blob-code-inner js-file-line\">")[1].split("</td>")[0];
        Toast.makeText(getApplicationContext(),login_ip, Toast.LENGTH_LONG).show();

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
                searchActivity.signup_button_check = 0;
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
            clientSocket = new Socket(login_ip, 9002);
            inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
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
                //Toast.makeText(getApplication(), from_server_temp, Toast.LENGTH_SHORT).show();
                if(from_server_temp.equalsIgnoreCase("error")){
                    Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_LONG).show();
                }
                else {
                    my_frineds = from_server_temp;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivityForResult(intent, REQUEST_SIGNUP);
                    finish();
                }
            }
        }
    };


    public String gethttp2(String sstr1) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet post = new HttpGet();
            post.setURI(new URI(sstr1));
            //post.setHeader("HOST","openapi.naver.com");
            //post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            //post.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            //post.setHeader("Accept-Encoding","gzip, deflate, br");
            //post.setHeader("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
            //post.setHeader("Upgrade-Insecure-Requests","1");
            //post.setHeader("Host","github.com");

            HttpResponse resp = client.execute(post);
            BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(),"UTF-8"));
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                sb.append(str).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }
}