package com.example.coder.xiaohua.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//登录界面
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_name;
    private EditText ed_pwd;
    private Button btn_login;
    private Button btn_register;
    private CheckBox keep_password;
    private TextView tv_forget;

    private long firstTime;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "2a2dcd89ca2b4127bf108c67edaa0358");
        initview();
        setlisteners();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemeber = sp.getBoolean("keep_password", false);
        if (isRemeber) {
            String account = sp.getString("username", "");
            String password = sp.getString("password", "");
            ed_name.setText(account);
            ed_pwd.setText(password);
            keep_password.setChecked(true);
        }
    }

    private void setlisteners() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        keep_password.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
    }

    private void initview() {
        ed_name = findViewById(R.id.ed_name);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        ed_pwd = findViewById(R.id.ed_pwd);
        keep_password = findViewById(R.id.keep_password);
        tv_forget = findViewById(R.id.tv_forget);
    }

    public void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String name = data.getStringExtra("NAME");//通过key得到值
            String pwd = data.getStringExtra("PWD");
            ed_name.setText(name);
            ed_pwd.setText(pwd);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(this, LogInActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_register:
                final String name = ed_name.getText().toString().trim();
                final String password = ed_pwd.getText().toString().trim();
                if (!name.equals("") && !password.equals("")) {
                    final BmobUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
//                                if (user.getEmailVerified()) {
                                Intent i = new Intent(RegisterActivity.this, UiActivity.class);
                                startActivity(i);
                                show("登录成功！");
                                editor = sp.edit();
                                if (keep_password.isChecked()) {
                                    editor.putBoolean("keep_password", true);
                                    editor.putString("username", name);
                                    editor.putString("password", password);
                                } else {
                                    editor.clear();
                                }
                                editor.apply();
                                finish();
//                                } else {
//                                    show("登录失败，请前往邮箱验证！");
//                                }
                            } else {
                                show("登录失败" + e.getMessage());
                            }
                        }
                    });
                }
                break;
            case R.id.tv_forget:
                Intent forgetpassword = new Intent(this, ForgetPasswordActivity.class);
                startActivity(forgetpassword);
                break;
        }
    }
}
