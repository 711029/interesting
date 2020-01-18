package com.example.coder.xiaohua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.Joke;


import java.util.List;

public class JokeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Joke> mList;
    private LayoutInflater inflater;
    private Joke data;

    public JokeAdapter(Context mContext, List<Joke> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //第一次加载
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_joke_item,null);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        data = mList.get(position);
        viewHolder.tv_content.setText(data.getContent());
        return convertView;
    }
    class ViewHolder{
        private TextView tv_content;

    }
}
