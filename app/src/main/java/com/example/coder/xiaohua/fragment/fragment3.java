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
import com.example.coder.xiaohua.adapter.NewsAdapter;
import com.example.coder.xiaohua.entity.Collect;
import com.example.coder.xiaohua.entity.News;
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

public class fragment3 extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView lv3;
    private List<News> mList = new ArrayList<>();

    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment3, container, false);
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
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });
        lv3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                        String title =  mListTitle.get(position);
                                        Collect collect = new Collect();
                                        collect.setUrl(url);
                                        collect.setTitle(title);
                                        collect.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e==null){
                                                    show("收藏成功！");
                                                }else{
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
    }

    public void show (String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void findView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout3);
        lv3 = view.findViewById(R.id.list3);

        parsing();
    }

    private void parsing() {
        String url = "http://api.avatardata.cn/TouTiao/Query?key=4eebc1c0e8b24e2b85defe98b8112ff4&type=top";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingjson(t);
            }
        });
    }

    private void parsingjson(String t) {
        mListTitle.clear();
        mListUrl.clear();
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                News data = new News();
                String title = json.getString("title");
                String url = json.getString("url");
                data.setTitle(json.getString("title"));
                data.setDate(json.getString("date"));
                data.setAuthor_name(json.getString("author_name"));
                data.setThumbnail_pic_s(json.getString("thumbnail_pic_s"));
                data.setThumbnail_pic_s02(json.getString("thumbnail_pic_s02"));
                data.setThumbnail_pic_s03(json.getString("thumbnail_pic_s03"));
                data.setUrl(json.getString("url"));
                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);
            }
            NewsAdapter adapter = new NewsAdapter(getActivity(), mList);
            lv3.setAdapter(adapter);
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
