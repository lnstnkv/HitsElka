package com.tsu.bubblesortrun

import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceHolder

internal class DrawThread(private val surfaceHolder: SurfaceHolder, resources: Resources?) :
    Thread() {
    private var runFlag = false
    private val hodhedhog: Bitmap
    private val fatherFrost: Bitmap
    private val winterMaiden: Bitmap
    private val university: Bitmap
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
                    canvas.drawBitmap(hodhedhog, 0f,0f, null)
                    canvas.drawBitmap(fatherFrost, 250f,250f, null)
                    canvas.drawBitmap(winterMaiden, 350f,350f, null)
                    canvas.drawBitmap(university, 200f,350f, null)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            } finally {

            }
        }
    }

    init {

        // загружаем картинку, которую будем отрисовывать
        hodhedhog = BitmapFactory.decodeResource(
            resources, R.drawable.hodhedhog
        )
        fatherFrost = BitmapFactory.decodeResource(
            resources, R.drawable.father_frost
        )
        winterMaiden= BitmapFactory.decodeResource(
            resources,R.drawable.winter_maiden
        )
        university= BitmapFactory.decodeResource(
            resources,R.drawable.university
        )
    }
}