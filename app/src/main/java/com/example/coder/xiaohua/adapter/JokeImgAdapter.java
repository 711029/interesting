package com.example.coder.xiaohua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.JokeImg;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JokeImgAdapter extends BaseAdapter {
    private Context mContext;
    private List<JokeImg> mList;
    private LayoutInflater inflater;
    private JokeImg data;

    public JokeImgAdapter(Context context, List<JokeImg> list) {
        mContext = context;
        mList = list;
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_jokeimg_item, null);
            viewHolder.tv_content = convertView.findViewById(R.id.tv_content);
            viewHolder.tv_updatatime = convertView.findViewById(R.id.tv_updatatime);
            viewHolder.img_url = convertView.findViewById(R.id.img_url);
            //设置缓存
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        viewHolder.tv_content.setText(data.getContent());
        viewHolder.tv_updatatime.setText(data.getUpdatetime());
        Picasso.get().load(data.getUrl()).into(viewHolder.img_url);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_content;
        private TextView tv_updatatime;
        private ImageView img_url;
    }
}