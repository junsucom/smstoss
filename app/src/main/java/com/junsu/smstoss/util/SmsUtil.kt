package com.junsu.smstoss.util

import android.content.Context
import android.telephony.SmsManager


/**
 * Created by jslee on 2017-12-07.
 */
class SmsUtil {

    companion object {
        var ACTION_SENT: String = "com.junsu.smstoss.ACTION_SMS_SENT"
        var ACTION_DELIVERY: String = "com.junsu.smstoss.ACTION_SMS_DELIVERY"

        @JvmStatic
        fun sendSMS(context: Context, receiveNumber:String, smsNumber: String, smsText: String) {

//            val sentIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_SENT), 0)
//            val deliveredIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_DELIVERY), 0)

            val smsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage(smsNumber, receiveNumber, smsText, sentIntent, deliveredIntent)
            smsManager.sendTextMessage(smsNumber, receiveNumber, smsText, null, null)
        }
    }

}