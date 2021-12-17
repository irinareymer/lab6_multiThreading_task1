package lab6.multiThreading.lab6_multithreading_task1

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

class MainActivityCoroutines: AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView

    companion object{
        const val SECONDS_COUNT = "secondsElapsed"
    }

    private fun initCoroutines(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
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
        Log.i("LifecycleCallbacks", "Activity onCreate()")
        initCoroutines()
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