package kr.newface.new_face.new_face;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이 코드가 없으면 인터넷 통신이 안됨
        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //액션바 제거
        getSupportActionBar().hide();
        getSupportActionBar().setElevation(0);

        //페이지 넘기는 화면 설정
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);//페이지 2개

        //페이지 탭 설정
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);//페이지 넘기는 화면에 적용
        setupTabIcons();

        //화면 넘겼을때 (탭 클릭 했을 때)
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                //여기다가 위치 0일 때, 1일때 해야할 코드
                if(position == 0){
                }
                if(position == 1){

                }
            }
        });


    }

    private void setupTabIcons() {
        //탭 아이콘에 들어갈 이미지 설정
        int[] tabIcons = {
                R.drawable.friend,
                R.drawable.chat,
        };
        //이미지 적용
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getFragmentManager());
        //탭 0번째에는 Friend_List() 파일을 읽는다
        adapter.addFrag(new Friend_List(), "friend_list");
        //탭 1번째에는 Chat_List() 파일을 읽는다
        adapter.addFrag(new Chat_List(), "chat_list");
        viewPager.setAdapter(adapter);
    }

    //탭 관련인데 잘몰라여,,,
    class ViewPagerAdapter extends android.support.v13.app.FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            // return null to display only the icon
            return null;
        }
    }

    //앱 꺼졌을때
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
