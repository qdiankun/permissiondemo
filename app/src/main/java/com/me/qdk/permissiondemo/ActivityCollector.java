package com.me.qdk.permissiondemo;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class ActivityCollector {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static Activity getTopActivity() {
        if (!activityList.isEmpty()) {
            activityList.get(activityList.size() - 1);
        }
    }
}
