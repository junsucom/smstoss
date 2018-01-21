package com.junsu.smstoss.util

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.junsu.smstoss.ui.list.ItemsActivity
import java.util.ArrayList

/**
 * Created by junsu on 2018-01-21.
 */
class PermissionUtil {
    companion object {
        fun requestPermition(activity: Activity, permissions: Array<String>, requestCode: Int) {
            val missingPermissions = ArrayList<String>()
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.add(permission)
                }
            }

            if (missingPermissions.size > 0) {
                val permissions = arrayOfNulls<String>(missingPermissions.size)
                ActivityCompat.requestPermissions(
                        activity,
                        missingPermissions.toTypedArray(),
                        ItemsActivity.PERMISSIONS_REQUEST)
            }
        }
    }
}