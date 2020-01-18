package com.example.coder.xiaohua.ui;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.MyUser;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

//我的资料界面
public class DataActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView icon;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private TextView tv_user;
    private Button btn_update_ok;
    private Toolbar tb_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initviews();
        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_age.setText(userInfo.getAge() + "");
        et_sex.setText(userInfo.isSex() ? "男" : "女");
        et_desc.setText(userInfo.getDesc());
        setlisteners();

    }

    private void setlisteners() {
        tv_user.setOnClickListener(this);
        btn_update_ok.setOnClickListener(this);
        icon.setOnClickListener(this);
        tb_data.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initviews() {
        et_username = findViewById(R.id.et_username);
        et_sex = findViewById(R.id.et_sex);
        et_age = findViewById(R.id.et_age);
        et_desc = findViewById(R.id.et_desc);
        tb_data = findViewById(R.id.tb_data);
        tv_user = findViewById(R.id.tv_user);
        btn_update_ok = findViewById(R.id.btn_update_ok);
        icon = findViewById(R.id.icon);

        et_username.setEnabled(false);
        setEnabled(false);
    }

    private void setEnabled(boolean is) {
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }

    public void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                tv_user.setVisibility(View.GONE);
                break;
            case R.id.btn_update_ok:
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                if (!username.equals("") &
                        !age.equals("") &
                        !sex.equals("")) {
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    if (!desc.equals("")) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没有留下！");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                show("资料修改成功！");
                                finish();
                                Intent intent = new Intent(DataActivity.this, UiActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                show("资料修改失败！" + e.getMessage());
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
