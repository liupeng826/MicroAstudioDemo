package com.microastudio.demo;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author peng
 * @date 2019/3/16
 */
public class MyAdapter extends TagAdapter<MyAdapter.ItemViewHolder> {
    private Context myContext;
    private ArrayList<SearchHistoryBean> listitemList;

    public MyAdapter(Context context, ArrayList<SearchHistoryBean> itemlist) {
        myContext = context;
        listitemList = itemlist;
    }

    @Override
    public int getItemCount() {
        return listitemList.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.tv, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void whenBindViewHolder(ItemViewHolder holder, int position) {
        if (holder != null) {
            holder.mTitle.setTag(position);
            holder.mTitle.setText(listitemList.get(position).getSearchTitle());
        }
    }

    @Override
    public void afterBindViewHolder(ItemViewHolder holder, int position) {
        if (holder.mTitle.isSelected()) {
            holder.mTitle.setBackgroundResource(R.drawable.checked_bg);
            holder.mTitle.setTextColor(ContextCompat.getColor(myContext, R.color.colorAccent));
        } else {
            holder.mTitle.setBackgroundResource(R.drawable.normal_bg);
            holder.mTitle.setTextColor(ContextCompat.getColor(myContext, R.color.colorPrimaryDark));
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(View view) {
            super(view);

            mTitle = (TextView) view.findViewById(R.id.tv);
        }

        TextView mTitle;
    }
}