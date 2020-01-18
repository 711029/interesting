package com.example.coder.xiaohua.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.coder.xiaohua.R;

//笑话详情页面

public class JokeDataActivity extends AppCompatActivity {
    private Toolbar tb_joke;
    private TextView tv_content;
    private TextView tv_updata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_data);
        initviews();
    }
    private void initviews() {
        tb_joke = findViewById(R.id.tb_joke);
        tv_content = findViewById(R.id.tv_content);
        tv_updata = findViewById(R.id.tv_updata);

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        String updata = intent.getStringExtra("updatetime");
        tv_content.setText(content);
        tv_updata.setText(updata);
        tb_joke.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
