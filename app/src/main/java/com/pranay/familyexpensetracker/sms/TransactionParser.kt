package com.pranay.familyexpensetracker.sms

import com.pranay.familyexpensetracker.model.ExpenseTransaction
import java.util.UUID

object TransactionParser {
 private val amountPattern = Regex("(?:INR|Rs\\.?|₹)\\s*([0-9,]+(?:\\.[0-9]{1,2})?)|(?:debited|spent|paid|purchase)\\D{0,25}([0-9,]+(?:\\.[0-9]{1,2})?)", RegexOption.IGNORE_CASE)
 private val merchantPattern = Regex("(?:at|to|towards|@)\\s+([A-Za-z0-9&*._ -]{2,35}?)(?:\\s+(?:on|using|via|from)|[.,;\\n]|$)", RegexOption.IGNORE_CASE)
 fun parse(sender:String, body:String, timestamp:Long):ExpenseTransaction? {
  val text=body.trim(); val lower=text.lowercase()
  if(text.isBlank() || listOf("otp","one time password","offer","won","unsubscribe").any(lower::contains)) return null
  if(listOf("credited","received","refund","deposited").any(lower::contains)) return null
  if(listOf("debited","spent","purchase","paid","payment","withdrawn").none(lower::contains)) return null
  val match=amountPattern.find(text) ?: return null
  val raw=match.groupValues.drop(1).firstOrNull{it.isNotBlank()} ?: return null
  val amount=raw.replace(",","").toDoubleOrNull() ?: return null
  if(amount<=0) return null
  val merchant=merchantPattern.find(text)?.groupValues?.getOrNull(1)?.trim()?.takeIf{it.isNotBlank()} ?: "Unknown merchant"
  return ExpenseTransaction(UUID.randomUUID().toString(),amount,merchant,sender,timestamp,text)
 }
}
