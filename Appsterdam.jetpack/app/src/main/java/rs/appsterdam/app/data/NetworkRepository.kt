package rs.appsterdam.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

class NetworkRepository(private val context: Context) {

    private val memoryCache = ConcurrentHashMap<String, String>()
    private val cacheDir: File by lazy {
        File(context.cacheDir, "network_cache").apply {
            if (!exists()) mkdirs()
        }
    }

    fun fetchData(url: String): Flow<String> = flow {
        // 1. Emit from memory cache if available
        memoryCache[url]?.let {
            emit(it)
        } ?: run {
            // 2. Emit from disk cache if available
            val cachedContent = getCacheFromDisk(url)
            if (cachedContent != null) {
                memoryCache[url] = cachedContent
                emit(cachedContent)
            }
        }

        // 3. Always fetch from network to update
        try {
            val networkResponse = URL(url).readText()
            memoryCache[url] = networkResponse
            saveToDisk(url, networkResponse)
            emit(networkResponse)
        } catch (_: Exception) {
            // Error fetching from network
        }
    }.flowOn(Dispatchers.IO)

    private fun getCacheFromDisk(url: String): String? {
        val file = getCacheFile(url)
        return if (file.exists()) {
            try {
                file.readText()
            } catch (_: Exception) {
                null
            }
        } else null
    }

    private fun getCacheFile(url: String): File {
        val fileName = url.hashCode().toString()
        return File(cacheDir, fileName)
    }

    private fun saveToDisk(url: String, content: String) {
        try {
            getCacheFile(url).writeText(content)
        } catch (_: Exception) {
            // Error saving to disk
        }
    }
}
