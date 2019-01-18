package com.sample.aacsample.ui.base

import android.os.Handler
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import java.util.LinkedList
import java.util.Queue
import kotlin.coroutines.CoroutineContext

class PausableDispatcher(private val handler: Handler) : CoroutineDispatcher() {
    private val queue: Queue<Runnable> = LinkedList()
    private var paused: Boolean = false

    @Synchronized
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        Log.d("PausableDispatcher", "dispatch:$block")
        if (paused) {
            queue.add(block)
        } else {
            handler.post(block)
        }
    }

    @Synchronized fun pause() {
        Log.d("PausableDispatcher", "pause")
        paused = true
    }

    @Synchronized fun resume() {
        Log.d("PausableDispatcher", "resume")
        paused = false
        runQueue()
    }

    private fun runQueue() {
        queue.iterator().let {
            while (it.hasNext()) {
                val block = it.next()
                it.remove()
                handler.post(block)
            }
        }
    }
}
