package com.pranay.familyexpensetracker

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranay.familyexpensetracker.data.ExpenseStore
import java.text.DateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity:ComponentActivity(){
 private val sms=registerForActivityResult(ActivityResultContracts.RequestPermission()){}
 private val notifications=registerForActivityResult(ActivityResultContracts.RequestPermission()){}
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState); setContent {
  var rows by remember { mutableStateOf(ExpenseStore.all(this)) }
  MaterialTheme { Scaffold(topBar={TopAppBar(title={Text("Family Expense Tracker")})}) { p -> Column(Modifier.padding(p).padding(16.dp)) {
   Text("SMS-assisted expense capture",style=MaterialTheme.typography.titleMedium); Text("Messages are parsed on-device. Nothing is saved until you tap Add.")
   Spacer(Modifier.height(12.dp)); Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){ Button(onClick={sms.launch(Manifest.permission.RECEIVE_SMS)}){Text("Allow SMS")}; Button(onClick={if(android.os.Build.VERSION.SDK_INT>=33)notifications.launch(Manifest.permission.POST_NOTIFICATIONS)}){Text("Notifications")}}
   Spacer(Modifier.height(20.dp)); Row { Text("Confirmed expenses",style=MaterialTheme.typography.titleLarge); Spacer(Modifier.weight(1f)); TextButton(onClick={rows=ExpenseStore.all(this@MainActivity)}){Text("Refresh")} }
   if(rows.isEmpty()) Text("No confirmed expenses yet.")
   LazyColumn(verticalArrangement=Arrangement.spacedBy(8.dp)){items(rows,key={it.id}){e -> Card(Modifier.fillMaxWidth()){Column(Modifier.padding(12.dp)){Text("₹${e.amount} · ${e.merchant}"); Text("${e.sender} · ${DateFormat.getDateTimeInstance().format(Date(e.messageDate))}",style=MaterialTheme.typography.bodySmall)}}}}
  } } }
 } }
}
