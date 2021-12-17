package lab6.multiThreading.lab6_multithreading_task1

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {
    private val executor: ExecutorService = Executors.newFixedThreadPool(1)

    fun getExecutor(): ExecutorService{
        return executor
    }
}