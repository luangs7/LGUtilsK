package br.com.luan2.lgutilsk.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.AppOpsManagerCompat
import android.support.v4.content.ContextCompat

/**
 * Created by luan silva on 01/06/18.
 */

/**
Dangerous permissions and permission groups.
Permission Group                            Permissions
CALENDAR                                    READ_CALENDAR
WRITE_CALENDAR
CAMERA                                      CAMERA
CONTACTS                                    READ_CONTACTS
WRITE_CONTACTS
GET_ACCOUNTS
LOCATION                                    ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
MICROPHONE                                  RECORD_AUDIO
PHONE                                       READ_PHONE_STATE
CALL_PHONE
READ_CALL_LOG
WRITE_CALL_LOG
ADD_VOICEMAIL
USE_SIP
PROCESS_OUTGOING_CALLS
SENSORS                                     BODY_SENSORS
SMS                                         SEND_SMS
RECEIVE_SMS
READ_SMS
RECEIVE_WAP_PUSH
RECEIVE_MMS
STORAGE                                     READ_EXTERNAL_STORAGE
WRITE_EXTERNAL_STORAGE
*/

/**
 * [permission]
 *
 * @param permission
 */
fun Context.isPermissionGranted(permission: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
    val opPermission: String? = AppOpsManagerCompat.permissionToOp(permission)
    if (!opPermission.isNullOrEmpty()) {
        val result = AppOpsManagerCompat.noteProxyOp(this, opPermission!!, packageName)
        if (result == AppOpsManagerCompat.MODE_IGNORED) return false
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) return false
    }

    return true
}

/**
 * [permissions]所有权限是否已经授权
 *
 * @param permissions
 */
fun Context.arePermissionGranted(vararg permissions: String): Boolean =
        permissions.all { isPermissionGranted(it) }

/**
 * [permission]权限是否被拒绝或不再提示
 *
 * @param permission
 */
fun Activity.isPermissionAlwaysDenied(permission: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false
    return !shouldShowRequestPermissionRationale(permission)
}

/**
 * [permissions]所有权限是否被拒绝或不再提示
 *
 * @param permissions
 */
fun Activity.arePermissionAlwaysDenied(vararg permissions: String): Boolean =
        permissions.all { isPermissionAlwaysDenied(it) }

/**
 * 请求[permissions]授权
 *
 * @param permissions
 * @param requestCode
 */
fun Activity.requestPermission(permissions: Array<out String>, requestCode: Int) =
        ActivityCompat.requestPermissions(this, permissions, requestCode)

/**
 * 请求[permissions]授权
 *
 * @param permissions
 * @param requestCode
 * @param rationale
 */
inline fun Activity.requestPermissionWithRationale(permissions: Array<out String>, requestCode: Int, rationale: ((permissions: Array<out String>) -> Unit)) {
    permissions.forEach {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, it)) {
            rationale(permissions)
            return
        }
    }
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

/**
 * 处理请求权限结果
 *
 * @param permissions
 * @param grantResults
 * @param success
 * @param fail
 */
fun handlePermissionResult(permissions: Array<out String>, grantResults: IntArray,
                           success: ((permissions: List<String>) -> Unit)={},
                           fail: ((permissions: List<String>) -> Unit)={}) {
    val deniedList = arrayListOf<String>()
    permissions.indices
            .filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }
            .forEach { deniedList += permissions[it] }
    if (deniedList.isNotEmpty()) {
        fail(deniedList)
    } else {
        success(arrayListOf(*permissions))
    }
}

