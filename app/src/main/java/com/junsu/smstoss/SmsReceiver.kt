package com.junsu.smstoss

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.junsu.smstoss.persistence.ItemDatabase
import com.junsu.smstoss.util.SmsUtil
import io.reactivex.Flowable

class SmsReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "SmsReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, intent.action)
        when (intent.action) {
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                handleSmsReceived(context, intent)
            }
            SmsUtil.ACTION_SENT -> {
                handleSmsSent(context)
            }
        }
    }

    private fun handleSmsReceived(context: Context, intent: Intent) {
        val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        val sms = msgs[0]
        val receiveNumber = sms.getOriginatingAddress()
        val body = sms.getMessageBody()
        Log.v(TAG, "handleSmsReceived address: " + receiveNumber + " body: " + body)

        ItemDatabase.get(context).itemDao().findNumber(receiveNumber)
                .flatMap({ Flowable.fromIterable(it) })
                .filter({ it.enabled })
                .subscribe({
                    Log.d(TAG, it.toString());
                    SmsUtil.sendSMS(context, receiveNumber, it.sendNumber, body)
                })
    }

    private fun handleSmsSent(context: Context) {
        when (resultCode) {
            Activity.RESULT_OK ->
                // 전송 성공
                Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show()
            SmsManager.RESULT_ERROR_GENERIC_FAILURE ->
                // 전송 실패
                Toast.makeText(context, "전송 실패", Toast.LENGTH_SHORT).show()
            SmsManager.RESULT_ERROR_NO_SERVICE ->
                // 서비스 지역 아님
                Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show()
            SmsManager.RESULT_ERROR_RADIO_OFF ->
                // 무선 꺼짐
                Toast.makeText(context, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show()
            SmsManager.RESULT_ERROR_NULL_PDU ->
                // PDU 실패
                Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show()
        }
    }

}
