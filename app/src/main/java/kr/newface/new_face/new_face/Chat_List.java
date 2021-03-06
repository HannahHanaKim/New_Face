package kr.newface.new_face.new_face;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;

/**
 * Created by User on 2017-10-27.
 */

public class Chat_List extends Fragment {

    View view;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    Handler handler = new Handler();

    public Chat_List() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_list, null);

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

        //add list
        mAdapter.addItem(getResources().getDrawable(R.drawable.travel),"여행","","여행 추천지, 여행 후기");
        mAdapter.addItem(getResources().getDrawable(R.drawable.movie),"영화","","최신개봉작 소개, 영화추천");
        mAdapter.addItem(getResources().getDrawable(R.drawable.food),"푸드","","맛집 소개, 레시피 공개");
        mAdapter.addItem(getResources().getDrawable(R.drawable.sport),"스포츠","","경기후기, 번개");
        mAdapter.addItem(getResources().getDrawable(R.drawable.music),"음악","","게임소식, 게임리뷰");
        mAdapter.addItem(getResources().getDrawable(R.drawable.game),"게임","","신곡소개, 음악추천");


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
                convertView = inflater.inflate(R.layout.chat_cell, null);

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
                    Intent intent1 = new Intent(getActivity(),Activity_chat_room.class);
                    intent1.putExtra("name", mData.name);
                    startActivity(intent1);
                }

            });

            return convertView;
        }

        //add list item
        public void addItem(Drawable image, String name, String num , String text){
            Table_Cell addInfo = null;
            addInfo = new Table_Cell();
            addInfo.image = image;
            addInfo.name = name;
            addInfo.num = num;
            addInfo.text = text;

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
    }

}
