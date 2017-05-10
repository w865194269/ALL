package com.skd.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skd.schedule.adapter.CourseScheduleAdapter;
import com.skd.schedule.adapter.SpinnerListAdapter;
import com.skd.schedule.adapter.SpinnertremAdapter;
import com.skd.schedule.beans.Course;
import com.skd.schedule.beans.ListBean;
import com.skd.schedule.beans.json.JsonList;
import com.skd.schedule.db.DBManager;
import com.skd.schedule.utils.CourseParse;
import com.skd.schedule.utils.DialogUtil;
import com.skd.schedule.utils.ScheduleApplication;
import com.skd.schedule.utils.ServiceManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sunny on 2017/4/14.
 */

public class CourseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private static final String CodePath = "http://118.89.106.93:8080/ScheduleServer/verification";
    private static final String SchedulePath = "http://118.89.106.93:8080/ScheduleServer/course/schedule";
    private static final String CourseListPath = "http://118.89.106.93:8080/ScheduleServer/course/list";

    private static final  int CODE_COURSE_VER=100;
    private static final int CODE_COURSE_lIST=200;
    private static final int CODE_SHCEDULE_INFO=300;
    private static final int CODE_TEACHER_VER_ERROR=400;
    private static final  int MESSAGE_NOT_GET=500;
    private static final String REFERER="http://xg.zdsoft.com.cn/ZNPK/KBFB_LessonSel.aspx";

    private Button mBtSearch,mDiaDestory,mDiaSure;
    private ImageView mIvVer;
    private EditText mEtVer;
    private Spinner mSpCourList,mSpTermList;
    private SpinnerListAdapter mAdapter=null;
    private SpinnertremAdapter mTadapter=null;
    private List<ListBean> courseLists=null;
    private List<String> termLists = null;
    private ListBean course;
    private LinearLayout linearLayout;
    private ListView mLvCSchedule;
    private CourseScheduleAdapter mTAdapter = null;
    private List<Course> listScheudle = null;
    private Gson gson;
    private DialogUtil dialogUtil;
    private View view;

    Map<String, String> params ;
    private DBManager courseManager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CODE_TEACHER_VER_ERROR:
                    Toast.makeText(CourseActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    mEtVer.setText("");
                    //linearLayout.setVisibility(View.VISIBLE);
                    dialogUtil.show();
                    getVerificationCode(CodePath);
                    listScheudle.clear();
                    mTAdapter.notifyDataSetChanged();
                    break;
                case CODE_COURSE_VER:
                    Bitmap bm = (Bitmap) msg.obj;
                    if (bm != null) {
                        mIvVer.setImageBitmap(bm);
                    }
                    break;
                case CODE_COURSE_lIST:
                    List<ListBean> lists= (List<ListBean>) msg.obj;
                    courseLists.clear();
                    courseLists.add(new ListBean("00000","请选择---"));
                    courseLists.addAll(lists);
                    break;
                case CODE_SHCEDULE_INFO:
                    List<Course> courses = (ArrayList<Course>)msg.obj;
                    if (courses==null||courses.size()==0){
                        Toast.makeText(CourseActivity.this,"该课程没有安排",Toast.LENGTH_SHORT).show();
                    }
                    listScheudle.clear();
                    listScheudle.addAll(courses);
                    mTAdapter.notifyDataSetChanged();
                    break;
                case MESSAGE_NOT_GET:
                    Toast.makeText(CourseActivity.this, "课程为空", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initEvents() {
        mIvVer.setOnClickListener(this);
        mBtSearch.setOnClickListener(this);
        mDiaDestory.setOnClickListener(this);
        mDiaSure.setOnClickListener(this);
    }
    private void initBase() {
        dialogUtil = new DialogUtil(this);
        view = dialogUtil.getView();
        mDiaDestory = (Button) view.findViewById(R.id.bt_dialog_destory);
        mDiaSure = (Button) view.findViewById(R.id.bt_dialog_sure);
        mBtSearch= (Button) findViewById(R.id.bt_search);
        mEtVer= (EditText) view.findViewById(R.id.et_dialog_code);
        mIvVer= (ImageView) view.findViewById(R.id.iv_dialog_code);
        mSpCourList=(Spinner)findViewById(R.id.sp_list);
        mSpTermList=(Spinner)findViewById(R.id.sp_term_list);
        linearLayout = (LinearLayout) findViewById(R.id.ll_layout_top);
        courseManager = new DBManager(this);
        courseLists=new ArrayList<ListBean>();
        courseLists.add(new ListBean("0000","请选择---"));
        termLists = new ArrayList<String>();
        params = new HashMap<String,String>();
        termLists.add("学期--");
        termLists.add("2017023");
        mAdapter = new SpinnerListAdapter(this,courseLists);
        gson = new Gson();
        mSpCourList.setAdapter(mAdapter);
        mSpCourList.setOnItemSelectedListener(this);

        mTadapter = new SpinnertremAdapter(this,termLists);
        mSpTermList.setAdapter(mTadapter);
        mSpTermList.setOnItemSelectedListener(this);

        listScheudle = new ArrayList<Course>();
        mLvCSchedule = (ListView)findViewById(R.id.lv_course_schdule);
        mTAdapter = new CourseScheduleAdapter(this,R.layout.layout_course_schedule,listScheudle);
        mLvCSchedule.setAdapter(mTAdapter);
    }

    private void initToobars() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("课程课表");
        toolbar.setNavigationIcon(R.mipmap.common_tital_bar_back_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        initToobars();
        initBase();
        initEvents();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_dialog_code:
                //验证码改地址就好
                getVerificationCode(CodePath);
                break;
            case R.id.bt_search:
                getCourseSchedule(SchedulePath);
                break;
            case R.id.bt_dialog_destory:
                dialogUtil.destory();
                break;
            case R.id.bt_dialog_sure:
                getCourseSchedule(SchedulePath);
                break;

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        getCourseList(CourseListPath);
        //修改地址
        getVerificationCode(CodePath);
    }



    private void getVerificationCode(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = CODE_COURSE_VER;
                msg.obj = ServiceManger.getVerificationCode(path);
                handler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 获取课程的列表
     * @param path
     */
    public void getCourseList(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                List<ListBean> lists = courseManager.queryCourseList();
                //先查询数据库，没有的话进行网络请求
                if (lists==null||lists.size()==0){
                    String courseHtml = ServiceManger.getInfoList(path);
                   // Log.i("log",courseHtml);
                    //gson解析
                    JsonList<ListBean> jsonList = gson.fromJson(courseHtml, new TypeToken<JsonList<ListBean>>()  {}.getType());
                    lists= (ArrayList<ListBean>)jsonList.getResult();
                    courseManager.addCourseList(lists);
                }
                    msg.what = CODE_COURSE_lIST;
                    msg.obj =   lists;
                    handler.sendMessage(msg);
            }
        }).start();
    }

    public void hideKeyWord(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取课程的课表信息
     */
    public void getCourseSchedule(final String path){
        if (course==null){
            Toast.makeText(this,"请选择",Toast.LENGTH_SHORT).show();
            mSpCourList.performClick();
            return;
        }
        //final Map<String, String> params = new HashMap<String,String>();
        final Map<String, String> headers = new HashMap<String, String>();//请求头不需要
        params.put("Sel_XNXQ", "20160");
        params.put("txt_yzm", "wwww");
        if(params.get("txt_yzm").equals("wwww"))
        {
            params.put("txt_yzm", mEtVer.getText().toString().trim());
        }
        params.put("Sel_KC", course.getId());

        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
        headers.put("Cookie", ScheduleApplication.getCookie()+";");
        headers.put("Referer",REFERER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Course> courses = courseManager.queryCourse(course.getId());//查询数据库
                Message msg = handler.obtainMessage();
                if(courses==null||courses.size()==0)//本地没有
                {
                    String html = ServiceManger.getScheduleInfo(path,headers,params);
                    Log.i("log",html+"html");
                    //gson解析
                    JsonList<Course> jsonCourse = gson.fromJson(html,new TypeToken<JsonList<Course>>() {}.getType());
                    if (jsonCourse.getCode() == -1){
                        msg.what=CODE_TEACHER_VER_ERROR;
                    }else{//网络
                        try {
                            if(jsonCourse.getResult()==null)
                            {
                                msg.what = MESSAGE_NOT_GET;
                            }
                            courses = (ArrayList<Course>)jsonCourse.getResult();
                            dialogUtil.destory();
                            //courses=CourseParse.getData(html,course.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        courseManager.addCourse(courses);
                        msg.what = CODE_SHCEDULE_INFO;
                        msg.obj = courses;
                    }
                }else {//数据库数据存在
                    msg.what = CODE_SHCEDULE_INFO;
                    msg.obj = courses;
                }
                handler.sendMessage(msg);
            }
        }).start();
        hideKeyWord();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
         course=null;
        }else {
            course = courseLists.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
