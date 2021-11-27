package lab6.multiThreading.lab6_multithreading_task1

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityThreads : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView
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
        initThread()
        super.onStart()
        Log.i("LifecycleCallbacks", "Activity onStart()")
    }

    override fun onStop() {
        backgroundThread.interrupt()
        super.onStop()
        Log.i("LifecycleCallbacks", "Activity onStop()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        sec = secondsElapsed
        outState.run {
            putInt(SECONDS_COUNT, sec)
        }
        super.onSaveInstanceState(outState)
        Log.i("LifecycleCallbacks", "Activity onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            sec = getInt(SECONDS_COUNT)
        }
        secondsElapsed = sec
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("LifecycleCallbacks", "Activity onRestoreInstanceState()")
    }
}