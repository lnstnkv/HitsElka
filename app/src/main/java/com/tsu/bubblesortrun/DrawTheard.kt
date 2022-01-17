package com.tsu.bubblesortrun

import android.R
import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceHolder

internal class DrawThread(private val surfaceHolder: SurfaceHolder, resources: Resources?) :
    Thread() {
    private var runFlag = false
    private val picture: Bitmap
    private val fatherFrost: Bitmap
    private val winterMaiden: Bitmap
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
                    canvas.drawBitmap(picture, 0f,0f, null)
                    canvas.drawBitmap(fatherFrost, 250f,250f, null)
                    canvas.drawBitmap(winterMaiden, 350f,350f, null)
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
            resources,
            com.tsu.bubblesortrun.R.drawable.hodhedhog
        )
        fatherFrost = BitmapFactory.decodeResource(
            resources,
            com.tsu.bubblesortrun.R.drawable.father_frost
        )
        winterMaiden= BitmapFactory.decodeResource(
            resources,
            com.tsu.bubblesortrun.R.drawable.winter_maiden
        )
        // сохраняем текущее время
        prevTime = System.currentTimeMillis()
    }
}