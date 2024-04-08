package ro.pub.cs.systems.eim.colocviujetpack

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ro.pub.cs.systems.eim.colocviujetpack.Constants.INPUT1
import ro.pub.cs.systems.eim.colocviujetpack.Constants.INPUT2
import ro.pub.cs.systems.eim.colocviujetpack.ui.theme.ColocviuJetpackTheme

class MainActivity : ComponentActivity() {

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Log.d(Constants.BROADCAST_RECEIVER_TAG, it.action.toString())
                Log.d(Constants.BROADCAST_RECEIVER_TAG, it.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA).toString())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel()

        setContent {
            ColocviuJetpackTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ScreenUI(viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        Constants.actionTypes.forEach { action ->
            intentFilter.addAction(action)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(messageBroadcastReceiver) }

}

@Composable
fun ScreenUI(viewModel: MainViewModel) {
    val context = LocalContext.current
    val input1 by rememberSaveable {
        viewModel.input1
    }
    val input2 by rememberSaveable {
        viewModel.input2
    }

    LaunchedEffect(input1, input2) {
        if (input1 + input2 > Constants.NUMBER_OF_CLICKS_THRESHOLD) {
            val intent = Intent(context, PracticalTest01Service::class.java)
            context.startService(intent)
        }
    }

    val activityResultsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "The activity returned with result OK", Toast.LENGTH_LONG).show()
            }
            else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "The activity returned with result CANCELED", Toast.LENGTH_LONG).show()
            }
        }

    Column {
        Button(
            onClick = {
                val intent = Intent(context, SecondActivity::class.java)
                intent.putExtra(INPUT1, input1)
                intent.putExtra(INPUT2, input2)
                activityResultsLauncher.launch(intent)
            },
        ) {
            Text(text = "Navigate to secondary activity")
        }
        Row {
            Text(text = "Count1 : $input1")
            Spacer(modifier = Modifier.width(40.dp))
            Text(text = "Count2: $input2")
        }
        Row {
            Button(onClick = {
                viewModel.incrementInput1()
            }) {
                Text(text = "Press me!")
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(onClick = {
                viewModel.incrementInput2()
            }) {
                Text(text = "Press me too!")
            }
        }
    }
}
