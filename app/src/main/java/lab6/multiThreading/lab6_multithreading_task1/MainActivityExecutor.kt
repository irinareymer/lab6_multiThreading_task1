package lab6.multiThreading.lab6_multithreading_task1

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Future

class MainActivityExecutor: AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var myApplication: MyApplication
    private lateinit var future: Future<*>

    companion object{
        const val SECONDS_COUNT = "secondsElapsed"
    }

    private fun initExecutor(): Future<*> {
        return myApplication.getExecutor().submit {
            while (!myApplication.getExecutor().isShutdown) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        myApplication = application as MyApplication
        Log.i("LifecycleCallbacks", "Activity onCreate()")
    }

    override fun onStart(){
        future = initExecutor()
        super.onStart()
        Log.i("LifecycleCallbacks", "Activity onStart()")
    }

    override fun onStop() {
        future.cancel(true)
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