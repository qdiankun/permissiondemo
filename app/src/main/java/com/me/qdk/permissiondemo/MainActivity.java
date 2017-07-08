package com.me.qdk.permissiondemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        makeCall();
    }

    public void buttonClick2(View view) {

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CALL_PHONE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            doSomething();
        }

        //未授权,请求权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            //参数3，请求码，标识谁去请求的
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
//
//        } else {
//            makeCall();
//        }
    }

    public void buttonClick3(View view) {
        requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "所有权限都同意了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(MainActivity.this, "某个权限被拒绝了" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doSomething() {
        Toast.makeText(MainActivity.this, "所有权限都同意了", Toast.LENGTH_SHORT).show();
    }

    private void makeCall() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel://123456789"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this, "某个权限被拒绝了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    doSomething();
                }


//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //同意权限申请
//                    makeCall();
//                } else {
//                    //拒绝权限申请
//                    Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_SHORT).show();
//                }
                break;
            default:
                break;
        }
    }
}
