package com.skd.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sunny on 2017/4/14.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mBtTeacher = null;
    private Button mBtClass = null;
    private Button mBtCourse = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToobars();
        initBase();
        initEvents();
    }

    private void initToobars() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_title);
        setSupportActionBar(toolbar);
    }

    private void initEvents() {
        mBtClass.setOnClickListener(this);
        mBtTeacher.setOnClickListener(this);
        mBtCourse.setOnClickListener(this);
    }

    private void initBase() {
        mBtClass = (Button) findViewById(R.id.bt_classroom);
        mBtCourse = (Button) findViewById(R.id.bt_course);
        mBtTeacher = (Button) findViewById(R.id.bt_teacher);
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.bt_classroom:
                 intent = new Intent(MainActivity.this, ClassActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_course:
                intent = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_teacher:
                 intent = new Intent(MainActivity.this, TeacherActivity.class);
                startActivity(intent);
                break;
        }
    }
}

