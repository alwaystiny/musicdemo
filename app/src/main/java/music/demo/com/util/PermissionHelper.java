package music.demo.com.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * 权限请求助手
 * Created by liunian on 2018/1/10.
 */

public class PermissionHelper {

    private static final String TAG = "PermissionHelper";

    private final static int READ_PHONE_STATE_CODE = 101;

    private final static int WRITE_EXTERNAL_STORAGE_CODE = 102;

    private final static int REQUEST_OPEN_APPLICATION_SETTINGS_CODE = 12345;
    private PermissionModel[] mPermissionModels = new PermissionModel[] {
            new PermissionModel("电话", Manifest.permission.READ_PHONE_STATE, "我们需要读取手机信息的权限来标识您的身份", READ_PHONE_STATE_CODE),
            new PermissionModel("存储空间", Manifest.permission.WRITE_EXTERNAL_STORAGE, "我们需要您允许我们读写你的存储卡，以方便我们临时保存一些数据",
                    WRITE_EXTERNAL_STORAGE_CODE)
    };

    private Activity mActivity;

    private OnApplyPermissionListener onApplyPermissionListener;

    public PermissionHelper(Activity activity){
        this.mActivity = activity;
    }

    public void setOnApplyPermissionListener(OnApplyPermissionListener onApplyPermissionListener){
        this.onApplyPermissionListener = onApplyPermissionListener;
    }

    public void applyPermissions(){
        for(PermissionModel permissionModel:mPermissionModels){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity,permissionModel.permission)){
                ActivityCompat.requestPermissions(mActivity,new String[]{permissionModel.permission},permissionModel.requestCode);
                return;
            }
        }
        if(onApplyPermissionListener != null){
            onApplyPermissionListener.onAfterApplyAllPermission();
        }
    }

    public boolean isAllRequestedPermissionGranted() {
            for(PermissionModel model:mPermissionModels){
                if (PackageManager.PERMISSION_GRANTED!=ContextCompat.checkSelfPermission(mActivity,model.permission)){
                    return  false;
                }
            }
            return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case READ_PHONE_STATE_CODE:
            case WRITE_EXTERNAL_STORAGE_CODE:
                if(PackageManager.PERMISSION_GRANTED!=grantResults[0]){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,permissions[0])){

                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                                .setTitle("权限申请")
                                .setMessage(findPermissionExplain(permissions[0]))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        applyPermissions();
                                    }
                                });
                        builder.setCancelable(false);
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                                .setTitle("权限申请")
                                .setMessage("请在打开的窗口的权限中开启" + findPermissionName(permissions[0]) + "权限，以正常使用本应用")
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openApplicationSettings(REQUEST_OPEN_APPLICATION_SETTINGS_CODE);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mActivity.finish();
                                    }
                                });
                        builder.setCancelable(false);
                        builder.show();
                    }
                    return;
                }
                // 到这里就表示用户允许了本次请求，我们继续检查是否还有待申请的权限没有申请
                if (isAllRequestedPermissionGranted()) {
                    if (onApplyPermissionListener != null) {
                        onApplyPermissionListener.onAfterApplyAllPermission();
                    }
                } else {
                    applyPermissions();
                }
                break;
        }
    }

    private boolean openApplicationSettings(int requestCode) {
        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            // Android L 之后Activity的启动模式发生了一些变化
            // 如果用了下面的 Intent.FLAG_ACTIVITY_NEW_TASK ，并且是 startActivityForResult
            // 那么会在打开新的activity的时候就会立即回调 onActivityResult
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Throwable e) {
            Log.e(TAG, "", e);
        }
        return false;
    }

    private String findPermissionName(String permission) {
        if (mPermissionModels!=null){
            for (PermissionModel model:mPermissionModels){
                if (model!=null && model.permission!=null && model.permission.equals(permission)){
                    return  model.name;
                }
            }
        }
        return null;
    }

    private String findPermissionExplain(String permission) {
        if (mPermissionModels!=null){
            for (PermissionModel model:mPermissionModels){
                if (model!=null && model.permission!=null && model.permission.equals(permission)){
                    return  model.explain;
                }
            }
        }
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_OPEN_APPLICATION_SETTINGS_CODE:
                if (isAllRequestedPermissionGranted()) {
                    if (onApplyPermissionListener != null) {
                        onApplyPermissionListener.onAfterApplyAllPermission();
                    }
                } else {
                    mActivity.finish();
                }
                break;
        }
    }



    public static class PermissionModel{
        /**
         * 权限名称
         */
        public String name;

        /**
         * 请求的权限
         */
        public String permission;

        /**
         * 解析为什么请求这个权限
         */
        public String explain;

        /**
         * 请求代码
         */
        public int requestCode;

        public PermissionModel(String name, String permission, String explain, int requestCode) {
            this.name = name;
            this.permission = permission;
            this.explain = explain;
            this.requestCode = requestCode;
        }
    }

    /**
     * 权限申请事件监听
     */
    public interface OnApplyPermissionListener {

        /**
         * 申请所有权限之后的逻辑
         */
        void onAfterApplyAllPermission();
    }
}
