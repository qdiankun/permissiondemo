package com.me.qdk.permissiondemo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermission);

}
