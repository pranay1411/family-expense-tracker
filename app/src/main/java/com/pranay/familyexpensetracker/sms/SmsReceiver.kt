package com.pranay.familyexpensetracker.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.pranay.familyexpensetracker.notifications.ExpenseNotification

class SmsReceiver:BroadcastReceiver(){ override fun onReceive(context:Context,intent:Intent){ if(intent.action!=Telephony.Sms.Intents.SMS_RECEIVED_ACTION)return; Telephony.Sms.Intents.getMessagesFromIntent(intent).forEach { sms -> val tx=TransactionParser.parse(sms.originatingAddress.orEmpty(),sms.messageBody.orEmpty(),sms.timestampMillis) ?: return@forEach; ExpenseNotification.show(context,tx) } } }
