package ro.pub.cs.systems.eim.colocviukotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.NonCancellable.start
import ro.pub.cs.systems.eim.colocviukotlin.Constants.INPUT_1
import ro.pub.cs.systems.eim.colocviukotlin.Constants.INPUT_2

class MainActivity : AppCompatActivity() {

    private lateinit var input1: EditText
    private lateinit var input2: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input1.setText("0")
        input2.setText("0")
        val pressMeButton = findViewById<Button>(R.id.press_me_button)
        pressMeButton.setOnClickListener {
            val sum = input1.text.toString().toInt() + 1
            input1.setText(sum.toString())
        }

        val pressMeToo = findViewById<Button>(R.id.press_me_too_button)
        pressMeToo.setOnClickListener {
            val sum = input2.text.toString().toInt() + 1
            input2.setText(sum.toString())
        }

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
              Toast.makeText(this, "The activity returned with result OK", Toast.LENGTH_LONG).show()
            }
            else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "The activity returned with result CANCELED", Toast.LENGTH_LONG).show()
            }
        }

        val navigateToSecondaryActivityButton = findViewById<Button>(R.id.navigate_to_second_activity)
        navigateToSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra(INPUT_1, Integer.valueOf(input1.text.toString()))
            intent.putExtra(INPUT_2, Integer.valueOf(input2.text.toString()))
            activityResultsLauncher.launch(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(INPUT_1, input1.text.toString())
        outState.putString(INPUT_2, input2.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(INPUT_1) && savedInstanceState.containsKey(INPUT_2)) {
            input1.setText(savedInstanceState.getString(INPUT_1))
            input2.setText(savedInstanceState.getString(INPUT_2))
        }
    }
}