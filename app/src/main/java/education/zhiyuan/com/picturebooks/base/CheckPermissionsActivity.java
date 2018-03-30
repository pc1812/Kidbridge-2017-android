package education.zhiyuan.com.picturebooks.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：Bro0cL
 * @date: 2017/8/31
 */

public class CheckPermissionsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 2018;
    private CheckPermissionsListener mListener;
    public static int flag = -1; //标记不同事件的请求

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void requestPermissionsC(Activity activity, String[] permissions, CheckPermissionsListener listener) {
        mListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity == null) {
                return;
            }
            List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
            if (!deniedPermissions.isEmpty()) {
                ActivityCompat.requestPermissions(this, deniedPermissions.toArray(new String[deniedPermissions.size()]), REQUEST_CODE);
                return;
            }
        }
        mListener.onGranted();
    }

    private List<String> findDeniedPermissions(Activity activity, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                List<String> deniedPermissions = new ArrayList<>();
                int length = grantResults.length;
                for (int i = 0; i < length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //该权限被拒绝了
                        deniedPermissions.add(permissions[i]);
                    }
                }
                if (deniedPermissions.size() > 0) {
                    mListener.onDenied(deniedPermissions);
                } else {
                    mListener.onGranted();
                }
                break;
            default:
                break;
        }
    }

    public String getPermissionName(String permisson) {
        String name = "";
        switch (permisson) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                name = "位置";
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                name = "读写";
                break;
            case Manifest.permission.RECORD_AUDIO:
                name = "录音";
                break;
            case Manifest.permission.CAMERA:
                name = "相机";
                break;
            case Manifest.permission.READ_PHONE_STATE:
                name = "电话";
                break;
        }
        return name;
    }
}
