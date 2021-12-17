package lab6.multiThreading.lab6_multithreading_task1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivityCoroutines2: AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences

    companion object{
        const val SECONDS_COUNT = "secondsElapsed"
    }

    private fun initCoroutines(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                sec = sharedPref.getInt(SECONDS_COUNT, sec)
                secondsElapsed = sec
                while(isActive){
                    delay(1000)
                    textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        initCoroutines()
        Log.i("LifecycleCallbacks", "Activity onCreate()")
    }

    override fun onStop() {
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sharedPref.edit().putInt(SECONDS_COUNT, secondsElapsed).apply()
        super.onStop()
        Log.i("LifecycleCallbacks", "Activity onStop()")
    }
}