package com.example.coder.xiaohua.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coder.xiaohua.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
//修改密码界面
public class UpdataPwdActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_old;//旧密码
    private EditText ed_new;//新密码
    private EditText ed_new_password;
    private Button btn_update_password;
    private Toolbar tb_updata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_pwd);
        initviews();
        setlisteners();
    }

    private void setlisteners() {
        btn_update_password.setOnClickListener(this);
    }
    private void initviews() {
        ed_old = findViewById(R.id.ed_old);
        ed_new = findViewById(R.id.ed_new);
        ed_new_password = findViewById(R.id.ed_new_password);
        btn_update_password = findViewById(R.id.btn_update_password);
        tb_updata = findViewById(R.id.tb_updata);
        tb_updata.setNavigationOnClickListener(new View.OnClickListener() {
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
            case R.id.btn_update_password:
                String oldpwd = ed_old.getText().toString().trim();
                String newpwd = ed_new.getText().toString().trim();
                String uppwd = ed_new_password.getText().toString().trim();
                if (!oldpwd.equals("") & !newpwd.equals("") & !uppwd.equals("")) {
                    if (newpwd.equals(uppwd)) {
                        BmobUser.updateCurrentUserPassword(oldpwd, newpwd, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    show("密码修改成功，可以用新密码进行登录啦");
                                    BmobUser.logOut();
                                    finish();
                                    Intent intent = new Intent(UpdataPwdActivity.this, RegisterActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    show("修改失败:" + e.getMessage());
                                }
                            }
                        });
                    } else {
                        show("两次密码输入不一致！");
                    }
                } else {
                    show("输入框不能为空！");
                }
                break;
        }
    }
}
