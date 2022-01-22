package com.tsu.hitselka.activity_game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder
import com.tsu.hitselka.R

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
    private val forest: Bitmap = BitmapFactory.decodeResource(
        resources, R.drawable.forest
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
                    canvas.drawBitmap(hedgehog, 880f, 750f, null)
                    canvas.drawBitmap(fatherFrost, 580f, 450f, null)
                    canvas.drawBitmap(winterMaiden, 1050f, 480f, null)
                    canvas.drawBitmap(university, 851f, 154f, null)
                    canvas.drawBitmap(forest, 1250f, 260f, null)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            } finally {

            }
        }
    }
}