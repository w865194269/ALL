package com.skd.schedule.utils;

import android.app.Application;

/**
 * Created by Wuchuang on 2017/4/14.
 */

public class ScheduleApplication extends Application {

    private static String cookie;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static void setCookies(String cookie){
        ScheduleApplication.cookie=cookie;
    }

    public static String getCookie(){
        return ScheduleApplication.cookie;
    }

}
