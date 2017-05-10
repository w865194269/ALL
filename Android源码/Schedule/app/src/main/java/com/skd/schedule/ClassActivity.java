package com.skd.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skd.schedule.adapter.SpinnerListAdapter;
import com.skd.schedule.beans.ClassSchedule;
import com.skd.schedule.beans.ListBean;
import com.skd.schedule.beans.json.JsonList;
import com.skd.schedule.beans.json.JsonObject;
import com.skd.schedule.db.DBManager;
import com.skd.schedule.utils.ClassParse;
import com.skd.schedule.utils.DialogUtil;
import com.skd.schedule.utils.ScheduleApplication;
import com.skd.schedule.utils.ServiceManger;

import org.jsoup.select.Evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wuchuang on 2017/4/15.
 */

public class ClassActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final  int CODE_CLASS_VER=100;
    private static final int CODE_CLASS_LIST=200;
    private static final int CODE_CLASS_IMG_PATH=300;
    private static final int CODE_CLASS_IMG=400;
    private static final int CODE_CLASS_VER_ERROR=-100;


    private static  final String CLASSLISTPATH = "http://118.89.106.93:8080/ScheduleServer/clazz/list";
    private static final String CLASSSCHEDULE = "http://118.89.106.93:8080/ScheduleServer/clazz/local/path";
    private static final String CLASSNET = "http://118.89.106.93:8080/ScheduleServer/clazz/net/path";
    private static final String CodePath = "http://118.89.106.93:8080/ScheduleServer/verification";
    private static final String CLASSS = "http://118.89.106.93:8080/ScheduleServer/clazz/schedule";

    private static final String REFERER="http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel.aspx";

    private Gson gson;
    private DialogUtil dialogUtil;
    private View view;

    private Button mBtSearch,mDiaDestory,mDiaSure;
    private ImageView mIvVer;
    private EditText mEtVer;
    private ImageView mIvClass;

    private Spinner mSpTeaList;
    private List<ListBean> classes;
    private SpinnerListAdapter spinnerAdapter;

    private ListBean classList=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CODE_CLASS_VER_ERROR:
                    dialogUtil.show();
                    String path="http://xg.zdsoft.com.cn/sys/ValidateCode.aspx";
                    getVerificationCode(CodePath);
                    mEtVer.setText("");
                    mIvVer.setImageBitmap(null);
                    break;
                case CODE_CLASS_IMG_PATH://获取图片地址
                    String paths= (String) msg.obj;
                   String url=CLASSS + "/" + paths;
//                    Log.e("Log",url);
                    getClassSchedule(url);
                    break;
                case CODE_CLASS_LIST://课程列表
                    List<ListBean> lists= (List<ListBean>) msg.obj;
                    classes.clear();
                    classes.add(new ListBean("00000","请选择---"));
                    classes.addAll(lists);
                    break;
                case CODE_CLASS_VER://验证码
                    Bitmap bm = (Bitmap) msg.obj;
                    if (bm != null) {
                        mIvVer.setImageBitmap(bm);
                     //   getClassImgPath(CLASSNET);
                    }
                    break;
                case CODE_CLASS_IMG://课程图片
                    Bitmap bm2 = (Bitmap) msg.obj;
                    if (bm2 != null) {
                        mIvClass.setImageBitmap(bm2);
                        dialogUtil.destory();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_class);
        initToobars();
        initBase();
        initEvents();
    }

    private void initToobars() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("班级课表");
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
        mBtSearch.setOnClickListener(this);
        mIvVer.setOnClickListener(this);
        mDiaDestory.setOnClickListener(this);
        mDiaSure.setOnClickListener(this);
    }

    private void initBase() {
        gson = new Gson();
        dialogUtil = new DialogUtil(this);
        view = dialogUtil.getView();
        mBtSearch= (Button) findViewById(R.id.bt_search);
        mEtVer= (EditText) findViewById(R.id.et_ver_code);
        mIvVer= (ImageView) findViewById(R.id.iv_ver_code);
        mIvClass= (ImageView) findViewById(R.id.iv_img_class);
        mDiaDestory = (Button) view.findViewById(R.id.bt_dialog_destory);
        mDiaSure = (Button) view.findViewById(R.id.bt_dialog_sure);
        mEtVer= (EditText) view.findViewById(R.id.et_dialog_code);
        mIvVer= (ImageView) view.findViewById(R.id.iv_dialog_code);
        mSpTeaList= (Spinner) findViewById(R.id.sp_list);
        classes=new ArrayList<ListBean>();
        classes.add(new ListBean("000000","请选择---"));
        spinnerAdapter=new SpinnerListAdapter(this,classes);
        mSpTeaList.setAdapter(spinnerAdapter);
        mSpTeaList.setOnItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //String path="http://xg.zdsoft.com.cn/sys/ValidateCode.aspx";
        //getVerificationCode(CodePath);

       // path="http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel.aspx";
        getClassList(CLASSLISTPATH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                getClassImgPath(CLASSSCHEDULE);
                break;
            case R.id.iv_ver_code:
                String path="http://xg.zdsoft.com.cn/sys/ValidateCode.aspx";
                getVerificationCode(CodePath);
                break;
            case R.id.bt_dialog_destory:
                dialogUtil.destory();
                break;
            case R.id.bt_dialog_sure:
                getClassImgPath(CLASSNET);
                break;
        }
    }
    public void hideKeyWord(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 获取图片的地址
     * @param path
     */
    public void getClassImgPath(final String path){
        if (classList==null){
            Toast.makeText(this,"请选择",Toast.LENGTH_SHORT).show();
            mSpTeaList.performClick();
            return;
        }
        //http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel_rpt.aspx
        final Map<String, String> params = new HashMap<String,String>();
        final Map<String, String> headers = new HashMap<String, String>();

        params.put("Sel_XZBJ", classList.getId());
        params.put("txt_yzm", "wwww");
        if(params.get("txt_yzm").equals("wwww"))
        {
            params.put("txt_yzm", mEtVer.getText().toString().trim());
        }
        params.put("Sel_XNXQ","20160");

        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
        headers.put("Cookie", ScheduleApplication.getCookie()+";");
        headers.put("Referer",REFERER);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Message msg = handler.obtainMessage();
                msg.what = CODE_CLASS_IMG_PATH;
                String html = ServiceManger.getScheduleInfo(path,headers,params);
                Log.i("log",html+"html");
                if(html != null)
                {
                    JsonObject<ClassSchedule> jsonCourse = gson.fromJson(html,new TypeToken<JsonObject<ClassSchedule>>() {}.getType());
                    if(jsonCourse.getCode() == -1)
                    {
                        msg.what = CODE_CLASS_VER_ERROR;
//                    Toast.makeText(ClassActivity.this,jsonCourse.getMsg(),Toast.LENGTH_SHORT).show();
                    }else {
                        msg.obj = jsonCourse.getResult().getPath();
                    }
                }
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void getClassList(final String path){
        new Thread(new Runnable(){
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                DBManager dbManager=new DBManager(ClassActivity.this);
                List<ListBean> lists=dbManager.queryAllClassList();
                if (lists==null||lists.size()==0){//数据库中数据没有
                    String html=ServiceManger.getInfoList(path);
                    JsonList<ListBean> jsonList = gson.fromJson(html, new TypeToken<JsonList<ListBean>>()  {}.getType());
                    lists= (ArrayList<ListBean>)jsonList.getResult();
                    //lists=ClassParse.ClassInfo(html);
                    dbManager.addClassList(lists);
                }
                msg.what = CODE_CLASS_LIST;
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
                msg.what = CODE_CLASS_VER;
                msg.obj = ServiceManger.getVerificationCode(path);
                handler.sendMessage(msg);
            }
        }).start();

    }

    public void getClassSchedule(final String path){
        final Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
        headers.put("Cookie", ScheduleApplication.getCookie()+";");
        headers.put("Referer",REFERER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
               Bitmap bt= ServiceManger.getVerificationCode(path);
                if (bt==null){
                    msg.what = CODE_CLASS_VER_ERROR;
                }else{
                    msg.what = CODE_CLASS_IMG;
                    msg.obj = bt;
                }
                handler.sendMessage(msg);
            }
        }).start();
        hideKeyWord();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this,classes.get(position).getName(),Toast.LENGTH_SHORT).show();
        if (position==0){
            classList=null;
        } else {
            classList = classes.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
