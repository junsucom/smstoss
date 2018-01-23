package com.junsu.smstoss.util

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import java.util.*

/**
 * Created by junsu on 2018-01-21.
 */
class PermissionUtil {
    companion object {
        fun requestPermission(activity: Activity, permissions: Array<String>, requestCode: Int) {
            val missingPermissions = ArrayList<String>()
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.add(permission)
                }
            }

            if (missingPermissions.size > 0) {
                ActivityCompat.requestPermissions(
                        activity,
                        missingPermissions.toTypedArray(),
                        requestCode)
            }
        }
    }
}