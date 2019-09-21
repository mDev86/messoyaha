package com.example.qrapp

import android.os.FileObserver
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class RecursiveFileObserver(path: String) : FileObserver(path) {
    override fun onEvent(event: Int, path: String?) {
        Log.d("Watcher", "Сработало ${path}")
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension != null) {
            val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension).toLowerCase().split("/")
            if(type[0] == "image" || type[0] == "video")
//                Toast.makeText(App.context, "Делать скриншоты запрещенно, файл удален", Toast.LENGTH_SHORT).show()
                File(path).delete()
        }
    }

    private var mFileObservers: MutableList<SingleFileObserver>? = null
    private var mPath: String = path
    private var mMask: Int = CREATE

    override fun startWatching() {
        if (mFileObservers != null)
            return

        mFileObservers = ArrayList()

        val stack = Stack<String>()
        stack.push(mPath)

        var file: File
        var path: String
        var files: Array<File>?
        while (!stack.empty()) {
            path = stack.pop()
            mFileObservers!!.add(SingleFileObserver(path, mMask))

            file = File(path)
            files = file.listFiles()
            if (files == null)
                continue

            for (i in files.indices) {
                if (files[i] == null)
                    continue
                if (files[i].isDirectory
                    && files[i].exists()
                    && files[i].name != "."
                    && files[i].name != ".."
                ) {
                    stack.push(files[i].path)
                }
            }
        }

        for (i in mFileObservers!!.indices) {
            Log.d("Watcher", "Старт watching")
            mFileObservers!![i].startWatching()
        }
    }

    @Synchronized
    override fun stopWatching() {
        if (mFileObservers == null)
            return

        for (i in mFileObservers!!.indices) {
            mFileObservers!![i].stopWatching()
        }

        mFileObservers!!.clear()
        mFileObservers = null
    }

    private inner class SingleFileObserver internal constructor(private val mPath: String, mask: Int) :
        FileObserver(mPath, mask) {

        override fun onEvent(event: Int, path: String?) {
            val finalPath = "$mPath/$path"
            this@RecursiveFileObserver.onEvent(event, finalPath)
        }
    }

}