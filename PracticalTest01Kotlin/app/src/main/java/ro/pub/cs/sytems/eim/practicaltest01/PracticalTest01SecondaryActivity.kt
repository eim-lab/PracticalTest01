package ro.pub.cs.sytems.eim.practicaltest01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_secondary)

        val totalText: TextView = findViewById(R.id.total_text)
        val okButton: Button = findViewById(R.id.ok_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        if (intent != null && intent.hasExtra("totalClicks")) {
            val clicks = intent.getIntExtra("totalClicks", 0)
            totalText.text = "Total clicks: $clicks"
        }

        okButton.setOnClickListener {
            setResult(RESULT_OK, Intent())
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED, Intent())
            finish()
        }
    }
}