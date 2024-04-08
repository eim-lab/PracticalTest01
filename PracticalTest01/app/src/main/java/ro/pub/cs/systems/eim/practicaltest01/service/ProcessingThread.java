package ro.pub.cs.systems.eim.practicaltest01.service;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class ProcessingThread extends Thread {

    private final Context context;
    private boolean isRunning = true;

    private final Random random = new Random();

    private final double arithmeticMean;
    private final double geometricMean;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        arithmeticMean = (double) (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
