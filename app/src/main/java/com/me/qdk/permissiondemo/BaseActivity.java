package com.me.qdk.permissiondemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */

public class BaseActivity extends AppCompatActivity {

    private static PermissionListener mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public static void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();

        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(ActivityCollector.getTopActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }
        }
        //有权限未得到授权
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(ActivityCollector.getTopActivity(), permissionList.toArray(new String[permissionList.size()]), 1);
        }else{
            mPermissionListener.onGranted();
        }
    }

//    public void requestRuntimePermission(String[] permissions,PermissionListener permissionListener) {
//        mPermissionListener = permissionListener;
//        List<String> permissionList = new ArrayList<>();
//
//        if (permissions != null && permissions.length > 0) {
//            for (String permission : permissions) {
//                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                    permissionList.add(permission);
//                }
//            }
//        }
//        //有权限未得到授权
//        if (!permissionList.isEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
//        }else{
//            mPermissionListener.onGranted();
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermission = new ArrayList<>();
                    for(int i=0;i<grantResults.length;i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermission.add(permission);
                        }
                    }
                    if (deniedPermission.isEmpty()) {
                        mPermissionListener.onGranted();
                    }else{
                        mPermissionListener.onDenied(deniedPermission);
                    }
                }
                break;
            default:
                break;
        }
    }
}
