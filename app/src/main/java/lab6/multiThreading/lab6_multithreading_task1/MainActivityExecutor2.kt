package lab6.multiThreading.lab6_multithreading_task1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Future

class MainActivityExecutor2: AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences
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
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sec = sharedPref.getInt(SECONDS_COUNT, sec)
        secondsElapsed = sec
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        future = initExecutor()
        super.onStart()
        Log.i("LifecycleCallbacks", "Activity onStart()")
    }

    override fun onStop() {
        future.cancel(true)
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sharedPref.edit().putInt(SECONDS_COUNT, secondsElapsed).apply()
        super.onStop()
        Log.i("LifecycleCallbacks", "Activity onStop()")
    }
}