package com.example.ontime;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * This is a class that implements DestroyActivityUtil object
 */
public class DestroyActivityUtil {
    public static List<Activity> activityList = new LinkedList();       //将要销毁的Activity事先存在这个集合中
    public void exit()              //  启动退出程序的按钮时，调用该方法，遍历一遍集合，销毁所有的Activity
    {
        for(Activity act:activityList)
        {
            Log.d("TAGS",act.toString());
            act.finish();
        }
        System.exit(0);
    }

}
