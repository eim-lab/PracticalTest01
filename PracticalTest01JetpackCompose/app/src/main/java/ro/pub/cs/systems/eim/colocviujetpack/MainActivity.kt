package ro.pub.cs.systems.eim.colocviujetpack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import ro.pub.cs.systems.eim.colocviujetpack.Constants.INPUT_1
import ro.pub.cs.systems.eim.colocviujetpack.Constants.INPUT_2
import ro.pub.cs.systems.eim.colocviujetpack.ui.theme.ColocviuJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel()

        setContent {
            ColocviuJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ScreenUI(viewModel)
                }
            }
        }
    }
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
                intent.putExtra(INPUT_1, input1)
                intent.putExtra(INPUT_2, input2)
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