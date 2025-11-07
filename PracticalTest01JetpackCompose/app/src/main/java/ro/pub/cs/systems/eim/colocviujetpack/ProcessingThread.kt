package ro.pub.cs.systems.eim.colocviujetpack

import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import java.util.Date
import java.util.Random
import kotlin.math.sqrt



class ProcessingThread(private val context: Context, firstNumber: Int, secondNumber: Int) :
    Thread() {
    private var isRunning = true

    private val random = Random()

    private val arithmeticMean: Double
    private val geometricMean: Double

    init {
        arithmeticMean = (firstNumber + secondNumber).toDouble() / 2
        geometricMean = sqrt((firstNumber * secondNumber).toDouble())
    }

    override fun run() {
        Log.d(
            Constants.PROCESSING_THREAD_TAG,
            "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid()
        )
        while (isRunning) {
            sendMessage()
            sleep()
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!")
    }

    private fun sendMessage() {
        val intent = Intent()
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.size)])
        intent.putExtra(
            Constants.BROADCAST_RECEIVER_EXTRA,
            Date(System.currentTimeMillis()).toString() + " " + arithmeticMean + " " + geometricMean
        )
        context.sendBroadcast(intent)
    }

    private fun sleep() {
        try {
            sleep(1000)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }
}
