package ro.pub.cs.sytems.eim.practicaltest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01MainActivity : AppCompatActivity() {
    private lateinit var leftText: EditText
    private lateinit var rightText: EditText
    private var leftCount = 0
    private var rightCount = 0

    private val SERVICE_THRESHOLD = 5
    private var serviceStarted = false

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("PracticalTest01Broadcast", intent?.getStringExtra("message") ?: "Empty")
        }
    }

    private val intentFilter = IntentFilter().apply {
        addAction("ro.pub.cs.systems.eim.action1")
        addAction("ro.pub.cs.systems.eim.action2")
        addAction("ro.pub.cs.systems.eim.action3")
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Result from secondary activity: OK", Toast.LENGTH_LONG).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Result from secondary activity: CANCELED", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_main)

        leftText = findViewById(R.id.left_text)
        rightText = findViewById(R.id.right_text)
        val leftButton: Button = findViewById(R.id.left_button)
        val rightButton: Button = findViewById(R.id.right_button)
        val navigateButton: Button = findViewById(R.id.navigate_button)

        leftButton.setOnClickListener {
            leftCount++
            leftText.setText(leftCount.toString())
            checkAndStartService()
        }

        rightButton.setOnClickListener {
            rightCount++
            rightText.setText(rightCount.toString())
            checkAndStartService()
        }

        navigateButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01SecondaryActivity::class.java)
            val totalClicks = leftCount + rightCount
            intent.putExtra("totalClicks", totalClicks)
            activityResultLauncher.launch(intent)
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("leftCount")) {
                leftCount = savedInstanceState.getInt("leftCount")
                leftText.setText(leftCount.toString())
            }
            if (savedInstanceState.containsKey("rightCount")) {
                rightCount = savedInstanceState.getInt("rightCount")
                rightText.setText(rightCount.toString())
            }
        }
    }

    private fun checkAndStartService() {
        if (leftCount + rightCount > SERVICE_THRESHOLD && !serviceStarted) {
            val intent = Intent(this, PracticalTest01Service::class.java)
            intent.putExtra("firstNumber", leftCount)
            intent.putExtra("secondNumber", rightCount)
            startService(intent)
            serviceStarted = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("leftCount", leftCount)
        outState.putInt("rightCount", rightCount)
    }

    override fun onDestroy() {
        val intent = Intent(this, PracticalTest01Service::class.java)
        stopService(intent)
        super.onDestroy()
    }
}