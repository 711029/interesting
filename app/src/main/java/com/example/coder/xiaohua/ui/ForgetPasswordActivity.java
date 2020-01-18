package com.example.coder.xiaohua.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

//忘记密码页面
public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_result;
    private EditText ed_email;
    private Button btn_forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initviews();
        setlisteners();
    }

    private void setlisteners() {
        btn_forget_password.setOnClickListener(this);
    }

    private void initviews() {
        ed_email = findViewById(R.id.ed_email);
        btn_forget_password = findViewById(R.id.btn_forget_password);
        tb_result = findViewById(R.id.tb_result);
        tb_result.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                final String email = ed_email.getText().toString().trim();
                if (!email.equals("")) {
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                show("邮箱已发送至:" + email);
                            } else {
                                show("邮箱发送失败！");
                            }
                        }
                    });
                } else {
                    show("输入框不能为空！");
                }
                break;
        }
    }
}
