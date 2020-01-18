package com.example.coder.xiaohua.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.adapter.CollectAdapter;
import com.example.coder.xiaohua.entity.Collect;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//我的收藏界面
public class CollectActivity extends AppCompatActivity {
    private Toolbar tb_collect;
    private ListView lv_coollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initviews();
        setlisteners();

    }

    private void setlisteners() {
        tb_collect.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void show (String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void initviews() {
        tb_collect = findViewById(R.id.tb_collect);
        lv_coollect = findViewById(R.id.lv_coollect);

        final BmobQuery<Collect> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Collect>() {
            @Override
            public void done(List<Collect> list, BmobException e) {
                if (e == null) {
                    CollectAdapter adapter = new CollectAdapter(CollectActivity.this, list);
                    lv_coollect.setAdapter(adapter);
                }
            }
        });
    }
}
