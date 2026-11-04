package ro.pub.cs.systems.eim.practicaltest01

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class PracticalTest01MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticalTest01App()
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PracticalTest01Service::class.java))
        super.onDestroy()
    }
}

@Composable
fun PracticalTest01App() {
    val context = LocalContext.current

    var leftCount by rememberSaveable { mutableIntStateOf(0) }
    var rightCount by rememberSaveable { mutableIntStateOf(0) }
    var serviceStarted by rememberSaveable { mutableStateOf(false) }

    val serviceThreshold = 5

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "Result from secondary activity: OK", Toast.LENGTH_LONG).show()
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(context, "Result from secondary activity: CANCELED", Toast.LENGTH_LONG).show()
        }
    }

    fun checkAndStartService() {
        if (leftCount + rightCount > serviceThreshold && !serviceStarted) {
            val intent = Intent(context, PracticalTest01Service::class.java).apply {
                putExtra("firstNumber", leftCount)
                putExtra("secondNumber", rightCount)
            }
            context.startService(intent)
            serviceStarted = true
        }
    }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context?, intent: Intent?) {
                Log.d("PracticalTest01Broadcast", intent?.getStringExtra("message") ?: "Empty")
            }
        }
        val filter = IntentFilter().apply {
            addAction("ro.pub.cs.systems.eim.action1")
            addAction("ro.pub.cs.systems.eim.action2")
            addAction("ro.pub.cs.systems.eim.action3")
        }

        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )


    {
        Button(
            onClick = {
                val intent = Intent(context, PracticalTest01SecondaryActivity::class.java).apply {
                    putExtra("totalClicks", leftCount + rightCount)
                }
                launcher.launch(intent)
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0E0E0),
                contentColor = Color.Black
            ),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("NAVIGATE TO SECONDARY ACTIVITY")
        }




        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = leftCount.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.weight(1f),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
            TextField(
                value = rightCount.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.weight(1f),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    leftCount++
                    checkAndStartService()
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0),
                    contentColor = Color.Black
                )
            ) {
                Text("PRESS ME!")
            }
            Button(
                onClick = {
                    rightCount++
                    checkAndStartService()
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0),
                    contentColor = Color.Black
                )
            ) {
                Text("PRESS ME, TOO!")
            }
        }
    }
}