package com.tony.brown.ui.camera;

import android.net.Uri;

import com.tony.brown.delegates.PermissionCheckerDelegate;
import com.tony.brown.util.file.FileUtil;

/**
 * Created by Tony on 2018/1/17.
 * 相机调用类
 */

public class BrownCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
