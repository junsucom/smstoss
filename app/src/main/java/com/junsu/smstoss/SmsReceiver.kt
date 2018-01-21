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
                handleSmsSent(context, intent)
            }
        }
    }

    private fun handleSmsReceived(context: Context, intent: Intent) {
        val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        val sms = msgs[0]
        var receiveNumber = sms.getOriginatingAddress()
        var body = sms.getMessageBody()
        Log.v(TAG, "handleSmsReceived address: " + receiveNumber + " body: " + body)

        val items = ItemDatabase.get(context).itemDao().findNumber(receiveNumber)
        if(items.isNotEmpty()){
            for(item in items){
                Log.d(TAG, item.toString());
                if(item.enabled) {
                    SmsUtil.sendSMS(context, receiveNumber, item.sendNumber, body)
                }
            }
        }

//        var settings: MutableMap<String, ItemData> = ItemData.load(context)
//        Log.d("test", "settings.size:"+settings.size);
//        if (settings.containsKey(receiveNumber)) {
//
//            var data: ItemData = settings.get(receiveNumber)!!
//            Log.d("test", receiveNumber+" "+data.sendNumber);
//            SmsUtil.sendSMS(context, receiveNumber, data.sendNumber, body)
//        }
    }

    private fun handleSmsSent(context: Context, intent: Intent) {
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
