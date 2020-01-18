package com.example.coder.xiaohua.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.FeedBack;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.kymjs.rxvolley.toolbox.RxVolleyContext.toast;

//反馈界面
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_feedback;
    private EditText ed_feedtext;
    private EditText ed_feednum;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initviews();
        setlisteners();
    }

    private void setlisteners() {
        btn_submit.setOnClickListener(this);
        tb_feedback.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initviews() {
        btn_submit = findViewById(R.id.btn_submit);
        tb_feedback = findViewById(R.id.tb_feedback);
        ed_feedtext = findViewById(R.id.ed_feedtext);
        ed_feednum = findViewById(R.id.ed_feednum);
    }
    public void show(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String text = ed_feedtext.getText().toString().trim();
                String  num = ed_feednum.getText().toString().trim();
                if (!text.equals("") & !num.equals("")) {
                    FeedBack fb = new FeedBack();
                    fb.setText(text);
                    fb.setNum(num);
                    fb.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId,BmobException e) {
                                        if(e==null){
                                            show("反馈成功！");
                                            finish();
                                            Log.i("objectid",objectId);
                                        }else{
                                            show("反馈失败！");
                                Log.i("objectid",e.getMessage());
                            }
                        }
                    });
                }else{
                    show("输入框不能为空!");
                }
                break;
        }
    }
}
