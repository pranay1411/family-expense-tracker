package com.pranay.familyexpensetracker.notifications

import android.app.*
import android.content.*
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pranay.familyexpensetracker.data.ExpenseStore
import com.pranay.familyexpensetracker.model.ExpenseTransaction
import java.util.Locale

object ExpenseNotification {
 private const val CHANNEL="expense_review"
 fun show(c:Context,e:ExpenseTransaction){
  if(Build.VERSION.SDK_INT>=26){ val m=c.getSystemService(NotificationManager::class.java); m.createNotificationChannel(NotificationChannel(CHANNEL,"Expense review",NotificationManager.IMPORTANCE_DEFAULT)) }
  fun pending(action:String,request:Int)=PendingIntent.getBroadcast(c,request,Intent(c,ExpenseActionReceiver::class.java).apply { this.action=action; putExtra("id",e.id); putExtra("amount",e.amount); putExtra("merchant",e.merchant); putExtra("sender",e.sender); putExtra("date",e.messageDate); putExtra("raw",e.rawMessage) },PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
  val n=NotificationCompat.Builder(c,CHANNEL).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Possible expense detected").setContentText("₹${String.format(Locale.getDefault(),"%.2f",e.amount)} at ${e.merchant}").addAction(0,"Add",pending("ADD",e.id.hashCode())).addAction(0,"Ignore",pending("IGNORE",e.id.hashCode()+1)).setAutoCancel(true).build()
  runCatching { NotificationManagerCompat.from(c).notify(e.id.hashCode(),n) }
 }
}
class ExpenseActionReceiver:BroadcastReceiver(){ override fun onReceive(c:Context,i:Intent){ if(i.action!="ADD")return; val e=ExpenseTransaction(i.getStringExtra("id")?:return,i.getDoubleExtra("amount",0.0),i.getStringExtra("merchant")?:"Unknown",i.getStringExtra("sender")?:"",i.getLongExtra("date",System.currentTimeMillis()),i.getStringExtra("raw")?:""); ExpenseStore.add(c,e) } }
