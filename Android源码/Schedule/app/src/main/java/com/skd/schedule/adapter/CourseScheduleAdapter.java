package com.skd.schedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.skd.schedule.R;
import com.skd.schedule.beans.Course;

import java.util.List;

/**
 * Created by Sunny on 2017/4/16.
 */

public class CourseScheduleAdapter extends ArrayAdapter{

    private Context context;
    private List<Course> datas;
    private int resource;

    public CourseScheduleAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);
        this.context = context;
        this.datas = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderCourseSchedule holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            holder=new HolderCourseSchedule();
            holder.viewLine=convertView.findViewById(R.id.v_line);
            holder.tvName=(TextView)convertView.findViewById(R.id.tv_t_class_name);
            holder.tvPreName = (TextView) convertView.findViewById(R.id.tv_t_class_prename);

            holder.tvClassNum= (TextView) convertView.findViewById(R.id.tv_t_class_classnum);
            holder.tvPreClassNum = (TextView) convertView.findViewById(R.id.tv_t_class_preclassnum);

            holder.tvNumber= (TextView) convertView.findViewById(R.id.tv_t_class_number);
            holder.tvPreNumber = (TextView) convertView.findViewById(R.id.tv_t_class_prenumber);

            holder.tvCourseType= (TextView) convertView.findViewById(R.id.tv_t_class_classtype);
            holder.tvClassRoom= (TextView) convertView.findViewById(R.id.tv_t_class_classroom);
            holder.tvWeeks= (TextView) convertView.findViewById(R.id.tv_t_class_weeks);
            holder.tvSection= (TextView) convertView.findViewById(R.id.tv_t_class_section);
            holder.tvPlace= (TextView) convertView.findViewById(R.id.tv_t_class_place);
            convertView.setTag(holder);
        }else {
            holder = (HolderCourseSchedule) convertView.getTag();
        }
        if (datas.get(position).getClassNum()==null||datas.get(position).getClassNum().length()==0) {
//            Log.i("vivi","position="+position + "result="+datas.get(position).getClassNum());
            holder.viewLine.setVisibility(View.INVISIBLE);
            holder.tvPreName.setText("");
            holder.tvPreClassNum.setText("");
            holder.tvPreNumber.setText("");
        }else{
            holder.viewLine.setVisibility(View.VISIBLE);
            holder.tvPreName.setText("任课教师:");
            holder.tvPreClassNum.setText("上课班号:");
            holder.tvPreNumber.setText("上课人数:");
        }
        holder.tvName.setText(datas.get(position).getName());
        holder.tvClassNum.setText(datas.get(position).getClassNum());
        holder.tvNumber.setText(datas.get(position).getNumber());
        holder.tvCourseType.setText(datas.get(position).getCourseType());
        holder.tvClassRoom.setText(datas.get(position).getClassRoom());
        holder.tvWeeks.setText(datas.get(position).getWeeks());
        holder.tvSection.setText(datas.get(position).getSection());
        holder.tvPlace.setText(datas.get(position).getAddress());
        return convertView;
    }

    class HolderCourseSchedule{
        TextView tvName;//任课教师
        TextView tvPreName;

        TextView tvClassNum;//上课班号
        TextView tvPreClassNum;

        TextView tvNumber;//上课人数
        TextView tvPreNumber;

        TextView tvCourseType;//课程类别
        TextView tvCredit;//考核方式
        TextView tvClassRoom;//上课班级
        TextView tvWeeks;//周次
        TextView tvSection;//节次
        TextView tvPlace;//地点
        View viewLine;
    }
}
