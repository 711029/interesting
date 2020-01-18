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
import com.example.coder.xiaohua.adapter.JokeImgAdapter;
import com.example.coder.xiaohua.entity.Collect;
import com.example.coder.xiaohua.entity.JokeImg;
import com.example.coder.xiaohua.ui.WebViewActivity;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class fragment2 extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView lv2;
    private List<JokeImg> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment2, container, false);
        findView(view);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        setlisteners();
        return view;
    }

    private void setlisteners() {
        lv2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                        String url = String.valueOf(mListUrl.get(position));
                                        String title = mListTitle.get(position);
                                        Collect collect = new Collect();
                                        collect.setUrl(url);
                                        collect.setTitle(title);
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
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", mListUrl.get(position));
                intent.putExtra("title", mListTitle.get(position));
                startActivity(intent);
            }
        });
    }

    public void parsing() {
        String url = "http://api.avatardata.cn/Joke/NewstImg?key=51b1a11fea6047ca9b5ba0160a877fe1&page=1&rows=30";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingjson(t);
            }

            private void parsingjson(String t) {
                mListTitle.clear();
                mListUrl.clear();
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    JSONArray jsonResult = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonResult.length(); i++) {
                        JSONObject json = (JSONObject) jsonResult.get(i);
                        JokeImg data = new JokeImg();
                        String url = json.getString("url");
                        String content = json.getString("content");
                        data.setContent(json.getString("content"));
                        data.setUpdatetime(json.getString("updatetime"));
                        data.setUrl(json.getString("url"));
                        mList.add(data);
                        mListUrl.add(url);
                        mListTitle.add(content);
                    }
                    JokeImgAdapter adapter = new JokeImgAdapter(getActivity(), mList);
                    lv2.setAdapter(adapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void show(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void findView(View view) {
        lv2 = view.findViewById(R.id.lv2);
        mSwipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout2);
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
        parsing();
    }
}
