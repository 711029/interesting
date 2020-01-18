package com.example.coder.xiaohua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<News> mList;
    private LayoutInflater inflater;
    private News data;

    public NewsAdapter(Context context, List<News> list) {
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
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_news_item, null);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_author = convertView.findViewById(R.id.tv_author);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.tv_pic01 = convertView.findViewById(R.id.tv_pic01);
            viewHolder.tv_pic02 = convertView.findViewById(R.id.tv_pic02);
            viewHolder.tv_pic03 = convertView.findViewById(R.id.tv_pic03);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_author.setText(data.getAuthor_name());
        viewHolder.tv_date.setText(data.getDate());
        Picasso.get().load(data.getThumbnail_pic_s()).into(viewHolder.tv_pic01);
        Picasso.get().load(data.getThumbnail_pic_s02()).into(viewHolder.tv_pic02);
        Picasso.get().load(data.getThumbnail_pic_s03()).into(viewHolder.tv_pic03);
        return convertView;
    }
    class ViewHolder {
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_date;
        private ImageView tv_pic01;
        private ImageView tv_pic02;
        private ImageView tv_pic03;
    }
}
