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
import android.widget.Toast;

import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Activity_chat_room extends AppCompatActivity {

    private String from_server_temp = null;
    private Handler mHandler;

    private BufferedReader inFromServer;
    private BufferedWriter outToServer;
    private Socket clientSocket;

    User me;
    User you;

    @BindView(R.id.chat_view) ChatView mChatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ButterKnife.bind(this);
        init();

        Intent intent = getIntent();
        final String name = intent.getExtras().getString("name");



        //전송 버튼 눌렀을때
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                mChatView.send(message);

                try {
                    PrintWriter out = new PrintWriter(outToServer, true);
                    out.println(mChatView.getInputText());
                } catch (Exception e) {

                }


                Toast.makeText(getApplication(), "\"보낸이학번\",\"" + name + "\",\"" + mChatView.getInputText() + "\"", Toast.LENGTH_SHORT).show();
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
                Message receivedMessage = new Message.Builder()
                        .setUser(you)
                        .setRightMessage(false)
                        .setMessageText(from_server_temp)
                        .build();

                mChatView.receive(receivedMessage);
                from_server_temp = null;
            }
        }
    };

    void init(){
        try{
            //나중에 켜야됨
            //clientSocket = new Socket("219.248.3.152", 6788);

            outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
    }
}
