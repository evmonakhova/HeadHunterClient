package ru.hh.headhunterclient.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import ru.hh.headhunterclient.R;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by alena on 09.05.2017.
 */

public class PermissionsHelper {

    public static final int REQUEST_PERMISSIONS_EXTERNAL_STORAGE = 0;

    public interface IRequestPermissionListener {

        void result(boolean granted);

    }

    private static IRequestPermissionListener mExternalStorageListener;

    public static void onRequestPermissionsResult(AppCompatActivity activity, int requestCode,
                                                  String[] permissions, int[] grantResults) {
        boolean granted = true;
        if (ContextCompat.checkSelfPermission(activity,
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            granted = false;
        }
        if (mExternalStorageListener != null) {
            mExternalStorageListener.result(granted);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkExternalStoragePermission(IRequestPermissionListener listener,
                                                         final Activity activity) {
        mExternalStorageListener = listener;

        if (hasStoragePermission(activity)) {
            return true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, WRITE_EXTERNAL_STORAGE)) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = activity.getString(R.string.need_external_storage_permission);
                        showDialog(message, activity);
                    }
                });
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS_EXTERNAL_STORAGE);
            }
            return false;
        }
    }

    public static boolean hasStoragePermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private static void showDialog(String message, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setMessage(message);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
