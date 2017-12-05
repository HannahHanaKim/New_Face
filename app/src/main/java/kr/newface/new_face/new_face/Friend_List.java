package kr.newface.new_face.new_face;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static kr.newface.new_face.new_face.Activity_login.my_frineds;

/**
 * Created by User on 2017-10-27.
 */

public class Friend_List extends Fragment {
    View view;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    Handler handler = new Handler();

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

            //listview refresh listener
            final PullRefreshLayout layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mListView.setEnabled(false);
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            layout.setRefreshing(false);
                                            mListView.setEnabled(true);
                                        }
                                    }, 0);
                                }
                            }, 500);

                        }
                    };
                    thread.start();
                }
            });
            layout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);

            mListView = (ListView) view.findViewById(R.id.listView);
            mAdapter = new ListViewAdapter(getActivity());
            mListView.setAdapter(mAdapter);

            //init image
            Drawable[] image_p = {getResources().getDrawable(R.drawable.asian_man),getResources().getDrawable(R.drawable.asian_woman),getResources().getDrawable(R.drawable.black_man),
                    getResources().getDrawable(R.drawable.black_woman),getResources().getDrawable(R.drawable.white_man),getResources().getDrawable(R.drawable.white_woman)};

            //get from server
            String[] f_splt = my_frineds.split("/");


            //add list
            for (int i = 1 ;i< f_splt.length;i++){
                String[] f_splt2 = f_splt[i].split(" ");
                String photo = f_splt2[3];
                String name = f_splt2[1];
                String code = f_splt2[2];
                String str = f_splt2[4].replace("_"," ");
                String point = f_splt2[5].replace(".","_");
                if (point.contains("_")){
                    point = point.split("_")[0];
                }
                mAdapter.addItem(image_p[Integer.valueOf(photo)-1],name ,"매칭값:" + point + "%",str,code);
            }


            return view;
        }

    //make list adapter
        private class ListViewAdapter extends BaseAdapter {
            private Context mContext = null;
            private ArrayList<Table_Cell> mListData = new ArrayList<Table_Cell>();

            public ListViewAdapter(Context mContext) {
                super();
                this.mContext = mContext;
            }
            //set list size
            @Override
            public int getCount() {
                return mListData.size();
            }

            //set list position
        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

            //set list id position
        @Override
        public long getItemId(int position) {
            return position;
        }

            //set layout code for listview
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.friend_cell, null);

                //set layout to view
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.num = (TextView) convertView.findViewById(R.id.num);
                holder.text = (TextView) convertView.findViewById(R.id.text);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            final Table_Cell mData = mListData.get(position);

            //set text
            holder.image.setImageDrawable(mData.image);
            holder.name.setText(mData.name);
            holder.num.setText(mData.num);
            holder.text.setText(mData.text);

            //button listener
            Button Button1= (Button)  convertView  .findViewById(R.id.button);
            Button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //friend = mData.name;
                    Intent intent1 = new Intent(getActivity(),Activity_chat_room.class);
                    intent1.putExtra("name", mData.id);
                    startActivity(intent1);
                }

            });

            return convertView;
        }

            //add list item
        public void addItem(Drawable image, String name, String num , String text,String id){
            Table_Cell addInfo = null;
            addInfo = new Table_Cell();
            addInfo.image = image;
            addInfo.name = name;
            addInfo.num = num;
            addInfo.text = text;
            addInfo.id = id;
            mListData.add(addInfo);
        }
        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }
        public void removeall(){
            mListData.clear();
            dataChange();
        }
        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }

    //set list hold
    private class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView num;
        public TextView text;
        public TextView id;
    }




}
