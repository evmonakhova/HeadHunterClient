package ru.hh.headhunterclient.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import ru.hh.headhunterclient.R;

/**
 * Created by alena on 09.05.2017.
 */

public class PermissionsHelper {

    public static final int REQUEST_PERMISSIONS_EXTERNAL_STORAGE = 0;

    private static final String REQUESTED_PERMISSION = "requested_permission";

    public interface IRequestPermissionListener {

        void result(boolean granted);

    }

    private static IRequestPermissionListener mExternalStorageListener;

    public static void onRequestPermissionsResult(AppCompatActivity activity, int requestCode,
                                                  String permission, int grantResults) {
        boolean granted = true;
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            granted = false;
        }
        if (mExternalStorageListener != null) {
            mExternalStorageListener.result(granted);
        }
    }

    public static boolean hasRequestedPermission(Context ctx) {
        SharedPreferences pref;
        pref = ctx.getSharedPreferences(REQUESTED_PERMISSION, Context.MODE_PRIVATE);
        return pref.contains(REQUESTED_PERMISSION);
    }

    private static void setFlagRequestedPermission(Context ctx) {
        SharedPreferences pref;
        pref = ctx.getSharedPreferences(REQUESTED_PERMISSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(REQUESTED_PERMISSION, true);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkExternalStoragePermission(IRequestPermissionListener listener,
                                                         final Activity activity) {
        mExternalStorageListener = listener;
        final List<String> permissionsList = new LinkedList<>();
        List<String> permissionsNeeded = new LinkedList<>();

        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = activity.getString(R.string.need_external_storage_permission);
                        showDialog(message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, activity);
                    }
                });
                return false;
            }
            activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_PERMISSIONS_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static boolean addPermission(List<String> permissionsList, String permission, Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!activity.shouldShowRequestPermissionRationale(permission)) {
                return false;
            }
        }
        return true;
    }

    private static void showDialog(String message, DialogInterface.OnClickListener positiveListener,
                                   Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton(R.string.ok, positiveListener);
        builder.setMessage(message);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
