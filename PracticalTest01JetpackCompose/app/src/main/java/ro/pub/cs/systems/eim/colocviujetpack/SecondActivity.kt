package ro.pub.cs.systems.eim.colocviujetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ro.pub.cs.systems.eim.colocviujetpack.ui.theme.ColocviuJetpackTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val input1 = intent.getIntExtra(Constants.INPUT_1, 0)
        val input2 = intent.getIntExtra(Constants.INPUT_2, 0)
        val suma = input1 + input2
        setContent {
            ColocviuJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = suma.toString())

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = {
                                setResult(RESULT_OK)
                                finish()
                            }) {
                                Text(text = "OK")
                            }

                            Button(onClick = {
                                setResult(RESULT_CANCELED)
                                finish()
                            }) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }
            }
        }
    }
}