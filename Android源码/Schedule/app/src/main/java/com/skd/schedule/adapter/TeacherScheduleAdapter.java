package com.skd.schedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skd.schedule.R;
import com.skd.schedule.beans.Teacher;

import java.util.List;

/**
 * Created by Wuchuang on 2017/4/15.
 */

public class TeacherScheduleAdapter extends ArrayAdapter {

    private Context context;
    private List<Teacher> datas;
    private int resource;

    public TeacherScheduleAdapter(Context context, int resource, List<Teacher> objects) {
        super(context, resource, objects);
        this.context=context;
        this.datas=objects;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderTeacherSchedule holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(resource,parent,false);
            holder=new HolderTeacherSchedule();
            holder.viewLine=convertView.findViewById(R.id.v_line);
//            holder.textView= (TextView) convertView.findViewById(R.id.iv_teacher_tmp);
            holder.tvName= (TextView) convertView.findViewById(R.id.tv_t_class_name);
            holder.tvClass= (TextView) convertView.findViewById(R.id.tv_t_classes);
            holder.tvTime= (TextView) convertView.findViewById(R.id.tv_t_class_time);
            holder.tvPlace= (TextView) convertView.findViewById(R.id.tv_t_class_place);
//            holder.viewInBottomLine=convertView.findViewById(R.id.v_inner_bottom_line);
//            holder.viewBottomLine=convertView.findViewById(R.id.v_bottom_line);
            convertView.setTag(holder);
        }else{
            holder= (HolderTeacherSchedule) convertView.getTag();
        }
        if (datas.get(position).isTrue()) {
            holder.viewLine.setVisibility(View.VISIBLE);
        }else{
            holder.viewLine.setVisibility(View.INVISIBLE);
        }
//        if (position==datas.size()-1){
//            holder.viewInBottomLine.setVisibility(View.INVISIBLE);
//            holder.viewBottomLine.setVisibility(View.VISIBLE);
//        }
        holder.tvName.setText(datas.get(position).getCourse());
        holder.tvClass.setText(datas.get(position).getClassroom());
        holder.tvTime.setText(datas.get(position).getTime());
        holder.tvPlace.setText(datas.get(position).getAddress());
//        holder.textView.setText(datas.get(position).toString());
        return convertView;
    }
    class HolderTeacherSchedule{
//        TextView textView;
        TextView tvName;//课程名
        TextView tvClass;//上课班级
        TextView tvTime;//时间
        TextView tvPlace;//地点
        View viewLine;
//        View viewInBottomLine;
//        View viewBottomLine;
    }
}

