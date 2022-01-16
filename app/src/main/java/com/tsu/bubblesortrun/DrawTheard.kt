package com.tsu.bubblesortrun

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder

internal class DrawThread(private val surfaceHolder: SurfaceHolder, resources: Resources?) :
    Thread() {
    private var runFlag = false
    private val picture: Bitmap
    private var prevTime: Long
    fun setRunning(run: Boolean) {
        runFlag = run
    }

    override fun run() {
        var canvas: Canvas?
        while (runFlag) {
            canvas = null
            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas(null)
                synchronized(surfaceHolder) {
                    canvas.drawColor(Color.BLACK)
                    canvas.drawBitmap(picture, 200f,200f, null)
                }
            } finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    init {

        // загружаем картинку, которую будем отрисовывать
        picture = BitmapFactory.decodeResource(
            resources, R.drawable.hodhedhog
        )

        // сохраняем текущее время
        prevTime = System.currentTimeMillis()
    }
}