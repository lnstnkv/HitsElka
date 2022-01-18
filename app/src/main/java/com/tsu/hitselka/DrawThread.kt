package com.tsu.hitselka

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder

internal class DrawThread(private val surfaceHolder: SurfaceHolder, resources: Resources?) :
    Thread() {
    private var runFlag = false
    private val hedhedhog: Bitmap
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
                    canvas.drawBitmap(hedhedhog, 0f, 0f, null)
                    canvas.drawBitmap(fatherFrost, 250f, 250f, null)
                    canvas.drawBitmap(winterMaiden, 350f, 350f, null)
                    canvas.drawBitmap(university, 200f, 350f, null)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            } finally {

            }
        }
    }

    init {

        // загружаем картинку, которую будем отрисовывать
        hedhedhog = BitmapFactory.decodeResource(
            resources, R.drawable.hedhedhog
        )
        fatherFrost = BitmapFactory.decodeResource(
            resources, R.drawable.father_frost
        )
        winterMaiden = BitmapFactory.decodeResource(
            resources, R.drawable.winter_maiden
        )
        university = BitmapFactory.decodeResource(
            resources, R.drawable.university
        )
    }
}