package ro.pub.cs.systems.eim.colocviukotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        val sumaDisplay = findViewById<TextView>(R.id.suma)
        val input1 = intent.getIntExtra(Constants.INPUT_1, 0)
        val input2 = intent.getIntExtra(Constants.INPUT_2, 0)
        val suma = input1 + input2
        sumaDisplay.text = suma.toString()


        val okButton = findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}