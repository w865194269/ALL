package com.skd.schedule.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.skd.schedule.beans.ListBean;

import java.util.List;

/**
 * Created by Wuchuang on 2017/4/17.
 */

public class SpinnerListAdapter implements SpinnerAdapter {

    private List<ListBean> lists;
    private Context context;

    public SpinnerListAdapter(Context context, List<ListBean> datas) {
        this.context = context;
        this.lists = datas;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return this.lists != null ? this.lists.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.lists == null ? null : lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(lists.get(position).getName());
        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView =  new TextView(context);
        textView.setText(lists.get(position).getName());
        return textView;
    }
}
