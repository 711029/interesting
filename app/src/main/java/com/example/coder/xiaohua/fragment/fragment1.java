package com.example.coder.xiaohua.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.adapter.JokeAdapter;
import com.example.coder.xiaohua.entity.Collect;
import com.example.coder.xiaohua.entity.Joke;
import com.example.coder.xiaohua.ui.JokeDataActivity;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class fragment1 extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListView lv;
    private List<Joke> mList = new ArrayList<>();

    //更新时间
    private List<String> mListUpdatetime = new ArrayList<>();
    //内容
    private List<String> mListcontent = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment1, container, false);
        findView(view);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        return view;

    }

    private void findView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            mList.clear();
                            parsing();
                            mSwipeRefreshLayout.setRefreshing(false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        lv = view.findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), JokeDataActivity.class);
                intent.putExtra("content", mListcontent.get(position));
                intent.putExtra("updatetime", mListUpdatetime.get(position));
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String title = "你要收藏嘛?";
                String[] items = new String[]{"点击收藏"};
                new AlertDialog.Builder(getActivity())
                        .setTitle(title)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (which) {
                                    case 0:
                                        String content = mListcontent.get(position);
                                        String update = mListUpdatetime.get(position);
                                        Collect collect = new Collect();
                                        collect.setContent(content);
                                        collect.setUpdata(update);
                                        collect.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    show("收藏成功！");
                                                } else {
                                                    show("收藏失败！");
                                                }
                                            }
                                        });
                                        break;
                                }
                            }
                        }).show();
                return true;
            }
        });
        parsing();
    }

    public void show(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void parsing() {
        String url = "http://api.avatardata.cn/Joke/NewstJoke?key=51b1a11fea6047ca9b5ba0160a877fe1&page=1&rows=20";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonResult = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonResult.length(); i++) {
                JSONObject json = (JSONObject) jsonResult.get(i);
                Joke data = new Joke();
                String content = json.getString("content");
                String updata = json.getString("updatetime");
                data.setContent(json.getString("content"));
                data.setUpdatetime(json.getString("updatetime"));
                mList.add(data);
                mListUpdatetime.add(updata);
                mListcontent.add(content);
            }
            JokeAdapter adapter = new JokeAdapter(getActivity(), mList);
            lv.setAdapter(adapter);
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
