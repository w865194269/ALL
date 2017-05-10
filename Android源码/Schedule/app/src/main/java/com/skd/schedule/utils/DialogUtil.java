package com.skd.schedule.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.skd.schedule.R;

/**
 * Created by 43827_000 on 2017/4/25.
 */

public  class DialogUtil {
    private AlertDialog dialog;
    private View view;
    public DialogUtil(Context context)
    {
        dialog = new AlertDialog.Builder(context).create();
        view = View.inflate(context, R.layout.layout_mydialog, null);
    }
    public void show()
    {
        dialog.setView(view);
        dialog.show();
    }
    public View getView()
    {
        return view;
    }
    public  void destory()
    {
        dialog.dismiss();
    }
}
