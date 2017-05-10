package com.skd.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skd.schedule.adapter.SpinnerListAdapter;
import com.skd.schedule.adapter.TeacherScheduleAdapter;
import com.skd.schedule.beans.Course;
import com.skd.schedule.beans.ListBean;
import com.skd.schedule.beans.Teacher;
import com.skd.schedule.beans.json.JsonList;
import com.skd.schedule.db.DBManager;
import com.skd.schedule.utils.DialogUtil;
import com.skd.schedule.utils.ScheduleApplication;
import com.skd.schedule.utils.ServiceManger;
import com.skd.schedule.utils.TeacherParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Wuchuang on 2017/4/14.
 */

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int CODE_TRACHER_VER = 100;//获取验证码
    private static final int CODE_TEACHER_LIST = 200;//教师list
    private static final int CODE_SHCEDULE_INFO = 300;//教师课表信息
    private static final int CODE_TEACHER_VER_ERROR = -100;//验证码错误
    private static final  int MESSAGE_NOT_GET=500;

    private static final String REFERER = "http://xg.zdsoft.com.cn/ZNPK/TeacherKBFB.aspx";

    private Button mBtSearch,mDiaDestory,mDiaSure;
    private ImageView mIvVer;
    private EditText mEtVer;
    private Spinner mSpTeaList;
    private SpinnerListAdapter mAdapter = null;
    private List<ListBean> teacherLists = null;
    private ListBean teacher;

    private ListView mLvTSchedule;
    private TeacherScheduleAdapter mTAdapter = null;
    private List<Teacher> listScheudle = null;

    private DialogUtil dialogUtil;
    private Gson gson;
    private View view;
    private static final String TeacherListpath = "http://118.89.106.93:8080/ScheduleServer/teacher/list";
    private static final String CodePath = "http://118.89.106.93:8080/ScheduleServer/verification";
    private static final String SchedulePath = "http://118.89.106.93:8080/ScheduleServer/teacher/schedule";

    //private static final String SchedulePath = "http://118.89.106.93:8080/ScheduleServer/course/schedule";
    //private static final String CourseListPath = "http://118.89.106.93:8080/ScheduleServer/course/list";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_TEACHER_VER_ERROR:
                    //验证码出错
                    Toast.makeText(TeacherActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    String path = "http://xg.zdsoft.com.cn/sys/ValidateCode.aspx";
                    mEtVer.setText("");
                    dialogUtil.show();
                    getVerificationCode(CodePath);
                    listScheudle.clear();
                    mTAdapter.notifyDataSetChanged();
                    break;
                case CODE_TRACHER_VER:
                    Bitmap bm = (Bitmap) msg.obj;
                    if (bm != null) {
                        mIvVer.setImageBitmap(bm);
                    }
                    break;
                case CODE_TEACHER_LIST:
                    List<ListBean> lists = (List<ListBean>) msg.obj;
                    teacherLists.clear();
                    teacherLists.add(new ListBean("00000", "请选择---"));
                    teacherLists.addAll(lists);
                    break;
                case CODE_SHCEDULE_INFO:
                    List<Teacher> teachers = (List<Teacher>) msg.obj;
                    if (teachers==null||teachers.size()==0){
                        Toast.makeText(TeacherActivity.this,"该教师没课程",Toast.LENGTH_SHORT).show();
                    }
                    listScheudle.clear();
                    listScheudle.addAll(teachers);
                    mTAdapter.notifyDataSetChanged();
                    break;
                case MESSAGE_NOT_GET:
                    Toast.makeText(TeacherActivity.this, "课程为空", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        initToobars();
        initBase();
        initEvents();
    }

    private void initToobars() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("教师课表查询");
        toolbar.setNavigationIcon(R.mipmap.common_tital_bar_back_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initEvents() {
        mIvVer.setOnClickListener(this);
        mBtSearch.setOnClickListener(this);
        mDiaDestory.setOnClickListener(this);
        mDiaSure.setOnClickListener(this);
    }

    private void initBase() {
        mBtSearch = (Button) findViewById(R.id.bt_search);
        mEtVer = (EditText) findViewById(R.id.et_ver_code);
        mIvVer = (ImageView) findViewById(R.id.iv_ver_code);
        mSpTeaList = (Spinner) findViewById(R.id.sp_list);
        gson = new Gson();
        dialogUtil = new DialogUtil(this);
        view = dialogUtil.getView();
        mDiaDestory = (Button) view.findViewById(R.id.bt_dialog_destory);
        mDiaSure = (Button) view.findViewById(R.id.bt_dialog_sure);
        mEtVer= (EditText) view.findViewById(R.id.et_dialog_code);
        mIvVer= (ImageView) view.findViewById(R.id.iv_dialog_code);
        teacherLists = new ArrayList<ListBean>();
        teacherLists.add(new ListBean("00000", "请选择---"));
//        teacherLists.add(new TeacherList("as1d","234"));
        mAdapter = new SpinnerListAdapter(this, teacherLists);
        mSpTeaList.setAdapter(mAdapter);
        mSpTeaList.setOnItemSelectedListener(this);

        listScheudle = new ArrayList<Teacher>();
        mLvTSchedule = (ListView) findViewById(R.id.lv_teacher_schdule);
        mTAdapter = new TeacherScheduleAdapter(this, R.layout.layout_teacher_schedule, listScheudle);
        mLvTSchedule.setAdapter(mTAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int t = new Random().nextInt(400) + 100;
        String path = "http://xg.zdsoft.com.cn/ZNPK/Private/List_JS.aspx";
        path += "?xnxq=20160&t=" + t;
        getTeacherList(TeacherListpath);

        path = "http://xg.zdsoft.com.cn/sys/ValidateCode.aspx";
        //getVerificationCode(CodePath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dialog_code:
                int t = new Random().nextInt(200) + 100;
                String path = "http://xg.zdsoft.com.cn/sys/ValidateCode.aspx";
                getVerificationCode(CodePath);
                break;
            case R.id.bt_search:
                getTeacherSchedule(SchedulePath);
                break;
            case R.id.bt_dialog_destory:
                dialogUtil.destory();
                break;
            case R.id.bt_dialog_sure:
                getTeacherSchedule(SchedulePath);
                break;
        }
    }

    /**
     * 获取教师的列表
     *
     * @param path
     */
    public void getTeacherList(final String path) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                DBManager dbManager = new DBManager(TeacherActivity.this);
                List<ListBean> lists = dbManager.queryTeacherList();
                if (lists == null || lists.size() == 0) {
//                    Log.e("Log", "NET");
                    String html = ServiceManger.getInfoList(path);
                    JsonList<ListBean> jsonList = gson.fromJson(html, new TypeToken<JsonList<ListBean>>()  {}.getType());
                    lists= (ArrayList<ListBean>)jsonList.getResult();
                    //lists = TeacherParse.TeacherInfo(html);
                    dbManager.addTeacherList(lists);
                }
                msg.what = CODE_TEACHER_LIST;
                msg.obj = lists;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void getVerificationCode(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = CODE_TRACHER_VER;
                msg.obj = ServiceManger.getVerificationCode(path);
                handler.sendMessage(msg);
            }
        }).start();
    }
    public void hideKeyWord(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 获取教师的课表信息
     */
    public void getTeacherSchedule(final String path) {
        if (teacher==null){
            Toast.makeText(this,"请选择",Toast.LENGTH_SHORT).show();
            mSpTeaList.performClick();
            return;
        }
        final Map<String, String> params = new HashMap<String, String>();
        final Map<String, String> headers = new HashMap<String, String>();
        params.put("Sel_XNXQ", "20160");
        params.put("txt_yzm", "wwww");
        if(params.get("txt_yzm").equals("wwww"))
        {
            params.put("txt_yzm", mEtVer.getText().toString().trim());
        }
        params.put("Sel_JS", teacher.getId());


        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
        headers.put("Cookie", ScheduleApplication.getCookie() + ";");
        headers.put("Referer", REFERER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = new DBManager(TeacherActivity.this);
                List<Teacher> datas = dbManager.queryTeacher(teacher.getId());
                Message msg = handler.obtainMessage();
                if (datas == null || datas.size() == 0) {//数据库没有数据
                    String html = ServiceManger.getScheduleInfo(path, headers, params);
                    JsonList<Teacher> jsonCourse = gson.fromJson(html,new TypeToken<JsonList<Teacher>>() {}.getType());
                    if (jsonCourse.getCode() == -1) {
//                        Log.e("Log", "验证码出错");/
                        msg.what = CODE_TEACHER_VER_ERROR;
                    } else {//网络解析
                        if(jsonCourse.getResult()==null)
                        {
                            msg.what = MESSAGE_NOT_GET;
                        }
//                        datas = TeacherParse.getData(html, teacher.getId());
//                        Log.e("Log", "网络请求");
                        datas = (ArrayList<Teacher>)jsonCourse.getResult();
                        dialogUtil.destory();
                        dbManager.addTeacher(datas);
                        msg.what = CODE_SHCEDULE_INFO;
                        msg.obj = datas;
                    }
                } else {//数据库数据存在
                    msg.what = CODE_SHCEDULE_INFO;
                    msg.obj = datas;
//                    Log.e("Log", "数据库存在");
                }
                handler.sendMessage(msg);
            }
        }).start();
        hideKeyWord();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
//            Toast.makeText(this,"请选择",Toast.LENGTH_SHORT).show();
            teacher=null;
//            mSpTeaList.performClick();
        } else {
            teacher = teacherLists.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        Toast.makeText(this,"请选择",Toast.LENGTH_SHORT).show();
//        teacher=null;
//        mSpTeaList.performClick();
    }
}
