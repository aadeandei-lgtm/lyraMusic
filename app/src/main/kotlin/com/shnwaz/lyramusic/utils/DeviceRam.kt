package com.shnwaz.lyramusic.utils

import android.app.ActivityManager
import android.content.Context

object DeviceRam {
    @Volatile
    private var cached: Boolean? = null

    fun isLowRam(context: Context): Boolean {
        cached?.let { return it }
        val low = try {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            if (am == null) {
                false
            } else {
                val mi = ActivityManager.MemoryInfo()
                am.getMemoryInfo(mi)
                am.isLowRamDevice || (mi.totalMem > 0 && mi.totalMem <= 1_500_000_000L)
            }
        } catch (t: Throwable) {
            false
        }
        cached = low
        return low
    }

    fun memoryCacheBytes(): Long {
        val maxHeap = Runtime.getRuntime().maxMemory()
        return (maxHeap * 0.10).toLong().coerceAtLeast(8L * 1024 * 1024)
    }
}
