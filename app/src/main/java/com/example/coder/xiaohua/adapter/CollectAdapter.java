package com.example.coder.xiaohua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.coder.xiaohua.R;
import com.example.coder.xiaohua.entity.Collect;
import java.util.List;

public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<Collect> mList;
    private LayoutInflater inflater;
    private Collect data;

    public CollectAdapter(Context context, List<Collect> list) {
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
            convertView = inflater.inflate(R.layout.layout_collect_item, null);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_content = convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        if (data.getTitle() == null) {
            viewHolder.tv_title.setVisibility(View.GONE);
        } else {
            viewHolder.tv_title.setText(data.getTitle());
        }
        if (data.getContent() == null) {
            viewHolder.tv_content.setVisibility(View.GONE);
        } else {
            viewHolder.tv_content.setText(data.getContent());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
        TextView tv_title;
    }
}
