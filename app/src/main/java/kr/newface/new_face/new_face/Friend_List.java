package kr.newface.new_face.new_face;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by User on 2017-10-27.
 */

public class Friend_List extends Fragment {
    View view;
    public Friend_List() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friend_list, null);

        Button chat_room=(Button)view.findViewById(R.id.chat_room);
        chat_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),Activity_chat_room.class);
                startActivity(intent1);
            }

        });

        Button login=(Button)view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),Activity_login.class);
                startActivity(intent1);
            }

        });

        Button signup=(Button)view.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),Activity_signup.class);
                startActivity(intent1);
            }

        });

        return view;
    }


}
