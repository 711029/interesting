package com.example.coder.xiaohua.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//注册界面
public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_name;
    private EditText ed_age;
    private EditText ed_desc;
    private RadioGroup Group;
    private EditText ed_pwd;
    private EditText ed_pwd2;
    private EditText ed_Email;
    private Button btn_login;
    private Toolbar tb_login;
    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Bmob.initialize(this, "2a2dcd89ca2b4127bf108c67edaa0358");
        initviews();
    }
    private void initviews() {
        ed_name = findViewById(R.id.ed_name);
        ed_age = findViewById(R.id.ed_age);
        ed_desc = findViewById(R.id.ed_desc);
        ed_pwd = findViewById(R.id.ed_pwd);
        ed_pwd2 = findViewById(R.id.ed_pwd2);
        ed_Email = findViewById(R.id.ed_Email);
        tb_login = findViewById(R.id.tb_login);
        Group = findViewById(R.id.Group);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        tb_login.setNavigationOnClickListener(new View.OnClickListener() {
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
            case R.id.btn_login:
                final String name = ed_name.getText().toString().trim();
                String age = ed_age.getText().toString().trim();
                String desc = ed_desc.getText().toString().trim();
                final String pwd = ed_pwd.getText().toString().trim();
                String pwd2 = ed_pwd2.getText().toString().trim();
                String Email = ed_Email.getText().toString().trim();
                if (!(name.equals("")) &
                        !(age.equals("")) &
                        !(pwd.equals("")) &
                        !(pwd2.equals("")) &
                        !(Email.equals(""))) {
                    if (pwd.equals(pwd2)) {
                        Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.rb_boy) {
                                    isGender = true;
                                } else if (checkedId == R.id.rb_girl) {
                                    isGender = false;
                                }
                            }
                        });
                        if (desc.equals("")) {
                            desc = "这个家伙很懒，什么都没有留下！";
                        }

                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(pwd);
                        user.setEmail(Email);
                        user.setDesc(desc);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    show("success");
                                    Intent intent = getIntent();
                                    intent.putExtra("NAME", name);
                                    intent.putExtra("PWD", pwd);
                                    //回传数据
                                    setResult(2, intent);
                                    finish();
                                } else {
                                    show("NO" + e.getMessage());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
