package com.ibm.security.a2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.ibm.security.adaptivesdk.AllowAssessmentResult
import com.ibm.security.adaptivesdk.DenyAssessmentResult

object HelperConstants {
    const val TOKEN_KEY = "token"
    const val RESULT_STATUS = "result_status"
}

fun presentErrorDialog(context: Context, title: String, error: Throwable) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(error.localizedMessage)
        .setNegativeButton(android.R.string.ok, null)
        .show()
}

fun handleAllowResponse(context: Context, result: AllowAssessmentResult) {
    saveToken(context, result.token)

    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.putExtra(HelperConstants.RESULT_STATUS, "Allowed!")
    context.startActivity(intent)
}

fun handleDenyResponse(context: Context, result: DenyAssessmentResult) {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.putExtra(HelperConstants.RESULT_STATUS, "Denied!")
    context.startActivity(intent)
}

fun handleLogout(context: Context) {
    removeToken(context)

    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.startActivity(intent)
}

fun saveToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    with(sharedPreferences.edit()) {
        putString(HelperConstants.TOKEN_KEY, token)
        apply()
    }
}

fun getToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    return sharedPreferences.getString(HelperConstants.TOKEN_KEY, null)
}

fun removeToken(context: Context) {
    val sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    with(sharedPreferences.edit()) {
        remove(HelperConstants.TOKEN_KEY)
        apply()
    }
}
