package com.example.coder.xiaohua.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.adapter.FragmentAdapter;
import com.example.coder.xiaohua.entity.MyUser;
import com.example.coder.xiaohua.fragment.fragment1;
import com.example.coder.xiaohua.fragment.fragment2;
import com.example.coder.xiaohua.fragment.fragment3;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;


//主界面
public class UiActivity extends AppCompatActivity {
    private List<Fragment> mFragments;
    private ViewPager mvp;
    private TabLayout tablayout;
    private long firstTime;
    private NavigationView nav_view;
    private TextView tv_user;
    private TextView tv_desc;
    private Toolbar tb_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        initviews();
        setlisteners();
    }

    private void setlisteners() {
        //将fragment放入到viewpager
        mFragments = new ArrayList<>();
        mFragments.add(new fragment1());
        mFragments.add(new fragment2());
        mFragments.add(new fragment3());
        FragmentAdapter adpter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mvp.setAdapter(adpter);
        tablayout.setupWithViewPager(mvp);
        tb_main.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer_open = (DrawerLayout) findViewById(R.id.activity_na);
                if (!drawer_open.isDrawerOpen(GravityCompat.START)) {
                    drawer_open.openDrawer(GravityCompat.START);
                }
            }
        });
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.my_zl) {
                    Intent i = new Intent(UiActivity.this, DataActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_gallery) {
                    Intent i = new Intent(UiActivity.this, CollectActivity.class);
                    startActivity(i);
                } else if (id == R.id.updatapwd) {
                    Intent i = new Intent(UiActivity.this, UpdataPwdActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_quit) {
                    Intent intent = new Intent(UiActivity.this, RegisterActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    BmobUser.logOut();
                    show("已成功退出登录！");
                } else if (id == R.id.nav_share) {
                    Intent i = new Intent(UiActivity.this, FeedBackActivity.class);
                    startActivity(i);
                }
                DrawerLayout drawer = findViewById(R.id.activity_na);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void initviews() {
        mvp = findViewById(R.id.vp);
        tablayout = findViewById(R.id.tab_layout);
        nav_view = findViewById(R.id.nav_view);
        tb_main = findViewById(R.id.tb_main);

        tv_user = nav_view.getHeaderView(0).findViewById(R.id.tv_user);
        tv_desc = nav_view.getHeaderView(0).findViewById(R.id.tv_desc);

        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        tv_user.setText(userInfo.getUsername());
        tv_desc.setText(userInfo.getDesc());
    }

    public void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出程序！", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
