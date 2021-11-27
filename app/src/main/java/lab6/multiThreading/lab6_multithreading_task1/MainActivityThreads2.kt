package lab6.multiThreading.lab6_multithreading_task1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityThreads2: AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences
    private lateinit var backgroundThread: Thread

    companion object{
        const val SECONDS_COUNT = "secondsElapsed"
    }

    private fun initThread(){
        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
                    }
                } catch (e: InterruptedException){
                    Thread.currentThread().interrupt()
                }
            }
        }
        backgroundThread.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        Log.i("LifecycleCallbacks", "Activity onCreate()")
    }

    override fun onStart(){
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sec = sharedPref.getInt(SECONDS_COUNT, sec)
        secondsElapsed = sec
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        initThread()
        super.onStart()
        Log.i("LifecycleCallbacks", "Activity onStart()")
    }

    override fun onStop() {
        backgroundThread.interrupt()
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sharedPref.edit().putInt(SECONDS_COUNT, secondsElapsed).apply()
        super.onStop()
        Log.i("LifecycleCallbacks", "Activity onStop()")
    }
}