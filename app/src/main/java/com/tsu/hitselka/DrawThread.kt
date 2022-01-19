package com.tsu.hitselka

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder

internal class DrawThread(private val surfaceHolder: SurfaceHolder, resources: Resources?) : Thread() {
    private var runFlag = false

    private val hedgehog: Bitmap = BitmapFactory.decodeResource(
        resources, R.drawable.hedgehog
    )
    private val fatherFrost: Bitmap = BitmapFactory.decodeResource(
        resources, R.drawable.father_frost
    )
    private val winterMaiden: Bitmap = BitmapFactory.decodeResource(
        resources, R.drawable.winter_maiden
    )
    private val university: Bitmap = BitmapFactory.decodeResource(
        resources, R.drawable.university
    )

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
                    canvas.drawBitmap(hedgehog, 0f, 0f, null)
                    canvas.drawBitmap(fatherFrost, 250f, 250f, null)
                    canvas.drawBitmap(winterMaiden, 350f, 350f, null)
                    canvas.drawBitmap(university, 200f, 350f, null)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            } finally {

            }
        }
    }
}