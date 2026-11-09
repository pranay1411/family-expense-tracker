package com.pranay.familyexpensetracker.data

import android.content.Context
import com.pranay.familyexpensetracker.model.ExpenseTransaction
import org.json.JSONArray
import org.json.JSONObject

object ExpenseStore {
 private const val PREFS="family_expenses"; private const val KEY="confirmed"
 fun all(context:Context):List<ExpenseTransaction> {
  val a=JSONArray(context.getSharedPreferences(PREFS,0).getString(KEY,"[]") ?: "[]")
  return (0 until a.length()).mapNotNull { i -> runCatching { val o=a.getJSONObject(i); ExpenseTransaction(o.getString("id"),o.getDouble("amount"),o.getString("merchant"),o.getString("sender"),o.getLong("messageDate"),o.optString("rawMessage")) }.getOrNull() }.sortedByDescending{it.messageDate}
 }
 fun add(context:Context,e:ExpenseTransaction) {
  val current=all(context); if(current.any{it.id==e.id}) return
  val a=JSONArray(); (listOf(e)+current).forEach { x -> a.put(JSONObject().put("id",x.id).put("amount",x.amount).put("merchant",x.merchant).put("sender",x.sender).put("messageDate",x.messageDate).put("rawMessage",x.rawMessage)) }
  context.getSharedPreferences(PREFS,0).edit().putString(KEY,a.toString()).apply()
 }
}
