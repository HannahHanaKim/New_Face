package kr.newface.new_face.new_face;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.views.ChatView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static kr.newface.new_face.new_face.Activity_login.chat_ip;
import static kr.newface.new_face.new_face.Activity_login.my_frineds;
import static kr.newface.new_face.new_face.MainActivity.my_id;

public class Activity_chat_room extends AppCompatActivity {
    String url = "";
    String naver = "";
    private String from_server_temp = null;
    private Handler mHandler;

    private BufferedReader inFromServer;
    private BufferedWriter outToServer;
    private Socket clientSocket;

    User me;

    User you;
    String name;



    String photo = "";
    String code;

    @BindView(R.id.switch2) Switch switch2;
    @BindView(R.id.chat_view) ChatView mChatView;
    @BindView(R.id.title2) TextView recommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ButterKnife.bind(this);

        //get id from before intent
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");



        //init socket
        init();

        //recommand word button listener
        Button button1 = (Button) findViewById(R.id.button3) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //trun url to browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplication(), "오류", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //전송 버튼 눌렀을때
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_str = mChatView.getInputText();

                //translate check
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
                    //send to server
                    PrintWriter out = new PrintWriter(outToServer, true);
                    out.println(temp_str);
                } catch (Exception e) {

                }


                //Toast.makeText(getApplication(), temp_str, Toast.LENGTH_SHORT).show();
                mChatView.setInputText("");
            }

        });
    }

    //서버로 부터 문자열을 받기위한 쓰레드 무한 반복
    private Thread checkUpdate = new Thread() {
        public void run() {
            try {
                while (true) {
                    //get from server
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
                //if server's string is not null
                if (!from_server_temp.split("/")[0].equals(my_id)){

                    //set photo init 2
                    photo = "2";
                    //get sender id
                    String get_code = from_server_temp.split("/")[0];
                    code = get_code;
                    int id = Integer.parseInt(get_code);
                    //find sender name from id
                    String[] f_splt = my_frineds.split("/");
                    for (int i = 1 ;i< f_splt.length;i++){
                        String[] f_splt2 = f_splt[i].split(" ");
                        if (get_code.equals(f_splt2[2])){
                            photo = f_splt2[3];
                            code = f_splt2[1];
                            break;
                        }


                    }

                    //image init
                    Drawable[] image_p = {getResources().getDrawable(R.drawable.asian_man),getResources().getDrawable(R.drawable.asian_woman),getResources().getDrawable(R.drawable.black_man),
                            getResources().getDrawable(R.drawable.black_woman),getResources().getDrawable(R.drawable.white_man),getResources().getDrawable(R.drawable.white_woman)};

                    //Bitmap icon = BitmapFactory.decodeResource(getResources(),image_p[Integer.valueOf(photo)-1]);
                    Bitmap bitmap = ((BitmapDrawable)image_p[Integer.valueOf(photo)-1]).getBitmap();

                    //set sender profile
                    you = new User(id, code, bitmap);

                    //Toast.makeText(getApplication(), from_server_temp, Toast.LENGTH_SHORT).show();

                    //show message to interface
                    Message receivedMessage = new Message.Builder()
                            .setUser(you)
                            .setRightMessage(false)
                            .setMessageText(from_server_temp.split("/")[2])
                            .build();

                    //set message
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
            clientSocket = new Socket(chat_ip, 9001);

            outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"));
            inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));

            checkUpdate.start();
            mHandler = new Handler();


            //clientSocket.close();
        }catch (Exception e){
            Toast.makeText(getApplication(), "서버 오류", Toast.LENGTH_SHORT).show();
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

        try {

            PrintWriter out = new PrintWriter(outToServer, true);

            //이 톡방이 개인톡방인지 단체 톡방인지 구분
            if (name.contains("여행")){
                //추천 단어 가져오기
                naver = gethttp2("https://m.naver.com/include/grid/panel_TRAVEL.shtml");
                //Toast.makeText(getApplication(), naver, Toast.LENGTH_SHORT).show();
                String tmp = naver.split("<span class=\"ct_ts\">")[1].split("</span>")[0];
                url = naver.split("<a href=\"")[1].split("\"")[0];
                recommand.setText(tmp);
                out.println(my_id + "/" + "1");
            }
            else if (name.contains("영화")){
                //추천 단어 가져오기 네이버 메인에서 자료 파싱
                naver = gethttp2("https://m.naver.com/include/grid/panel_MOVIE.shtml");
                String tmp = naver.split("<span class=\"ct_ts\">")[1].split("</span>")[0];
                url = naver.split("<a href=\"")[1].split("\"")[0];
                recommand.setText(tmp);
                out.println(my_id + "/" + "2");
            }
            else if (name.contains("푸드")){
                //추천 단어 가져오기 네이버 메인에서 자료 파싱
                naver = gethttp2("https://m.naver.com/include/grid/panel_LIVING.shtml");
                String tmp = naver.split("<span class=\"ct_ts\">")[1].split("</span>")[0];
                url = naver.split("<a href=\"")[1].split("\"")[0];
                recommand.setText(tmp);
                out.println(my_id + "/" + "3");
            }
            else if (name.contains("스포츠")){
                //추천 단어 가져오기 네이버 메인에서 자료 파싱
                naver = gethttp2("https://m.naver.com/include/grid/panel_SPORTS.shtml");
                String tmp = naver.split("<strong class=\"ut_t\">")[1].split("<")[0];
                url = naver.replace("\t","").replace("\n","").split("<li class=\"ut_item\"><a href=\"")[1].split("</span>")[0];
                recommand.setText(tmp);
                out.println(my_id + "/" + "4");
            }
            else if (name.contains("게임")){
                //추천 단어 가져오기 네이버 메인에서 자료 파싱
                naver = gethttp2("https://m.naver.com/include/grid/panel_GAMEAPP.shtml");
                String tmp = naver.split("<span class=\"ct_ts\">")[1].split("</span>")[0];
                url = naver.split("<a href=\"")[1].split("\"")[0];
                recommand.setText(tmp);
                out.println(my_id + "/" + "5");
            }
            else if (name.contains("음악")){
                //추천 단어 가져오기네이버 메인에서 자료 파싱
                naver = gethttp2("https://m.naver.com/include/grid/panel_MUSIC.shtml");
                String tmp = naver.split("<span class=\"ct_ts\">")[1].split("</span>")[0];
                url = naver.split("<a href=\"")[1].split("\"")[0];
                recommand.setText(tmp);
                out.println(my_id + "/" + "6");
            }else{
                //개인 톡방 일 경우
                findViewById(R.id.appBarLayout2).setVisibility(View.GONE);
                //Toast.makeText(getApplication(), my_id + "/-1", Toast.LENGTH_SHORT).show();
                out.println(my_id + "/-1");
                //Toast.makeText(getApplication(), name, Toast.LENGTH_SHORT).show();
                out.println(name);
            }

            //out.println("\"" + my_id + "\"");
        } catch (Exception e) {

        }
    }

    //파파고 api http요청
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

    //http 데이터 가져오기
    public String gethttp2(String sstr1) {
        try {



            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost();
            post.setURI(new URI(sstr1));
            //post.setHeader("HOST","openapi.naver.com");
            //post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            //post.setHeader("X-Naver-Client-Id","I7xF1aoEPAUZElXdHSH1");
            //post.setHeader("X-Naver-Client-Secret","MhLdSaa8lj");
            //post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
