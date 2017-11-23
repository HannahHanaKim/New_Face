package kr.newface.new_face.new_face;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

import static kr.newface.new_face.new_face.MainActivity.my_id;


public class Activity_chat_room extends AppCompatActivity {

    private String from_server_temp = null;
    private Handler mHandler;

    private BufferedReader inFromServer;
    private BufferedWriter outToServer;
    private Socket clientSocket;

    User me;
    User you;
    String name;

    @BindView(R.id.switch2) Switch switch2;
    @BindView(R.id.chat_view) ChatView mChatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ButterKnife.bind(this);


        Intent intent = getIntent();
        name = intent.getExtras().getString("name");

        init();

        //전송 버튼 눌렀을때
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_str = mChatView.getInputText();

                if (switch2.isChecked() == true){
                    temp_str = gethttp(temp_str).split("translatedText\":\"")[1].split("\"")[0];
                }

                //Toast.makeText(getApplication(), gethttp(""), Toast.LENGTH_SHORT).show();
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(temp_str)
                        .hideIcon(true)
                        .build();
                mChatView.send(message);

                try {
                    PrintWriter out = new PrintWriter(outToServer, true);
                    out.println(temp_str);
                } catch (Exception e) {

                }


                Toast.makeText(getApplication(), temp_str, Toast.LENGTH_SHORT).show();
                mChatView.setInputText("");
            }

        });
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
                if (!from_server_temp.split("/")[0].equals(my_id)){
                    Message receivedMessage = new Message.Builder()
                            .setUser(you)
                            .setRightMessage(false)
                            .setMessageText(from_server_temp.split("/")[2])
                            .build();

                    mChatView.receive(receivedMessage);
                    from_server_temp = null;
                }
            }
        }
    };

    void init(){
        getSupportActionBar().hide();
        getSupportActionBar().setElevation(0);
        try{
            //나중에 켜야됨
            clientSocket = new Socket("192.9.128.170", 9001);

            outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            checkUpdate.start();
            mHandler = new Handler();


            //clientSocket.close();
        }catch (Exception e){
            Toast.makeText(getApplication(), "서버 오류", Toast.LENGTH_SHORT).show();
        }


        try {


            PrintWriter out = new PrintWriter(outToServer, true);


            if (name.contains("채팅방")){
                Toast.makeText(getApplication(), my_id + "/" + name.split("채팅방")[1], Toast.LENGTH_SHORT).show();
                out.println(my_id + "/" + name.split("채팅방")[1]);
            }else{
                Toast.makeText(getApplication(), my_id + "/-1", Toast.LENGTH_SHORT).show();
                out.println(my_id + "/-1");
                Toast.makeText(getApplication(), name, Toast.LENGTH_SHORT).show();
                out.println(name);
            }

            //out.println("\"" + my_id + "\"");
        } catch (Exception e) {

        }



        //채팅방 디자인 설정
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("메세지를 입력해주세요");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //사용자 설정
        me = new User(0, "나", BitmapFactory.decodeResource(getResources(), R.drawable.profile));
        you = new User(1, "상대방", BitmapFactory.decodeResource(getResources(), R.drawable.profile));
    }

    public String gethttp(String sstr1) {
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("source", "en"));
            nameValuePairs.add(new BasicNameValuePair("target", "ko"));
            nameValuePairs.add(new BasicNameValuePair("text", sstr1));


            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost();
            post.setURI(new URI("https://openapi.naver.com/v1/language/translate"));
            //post.setHeader("HOST","openapi.naver.com");
            post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader("X-Naver-Client-Id","I7xF1aoEPAUZElXdHSH1");
            post.setHeader("X-Naver-Client-Secret","MhLdSaa8lj");
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse resp = client.execute(post);
            BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
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


    @Override
    protected void onDestroy() {
       super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }



}
