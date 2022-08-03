package com.example.customview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.PathParser
import androidx.core.graphics.drawable.toBitmap


class MyCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var eraser: Paint


    val bounds = Rect(100, 100, 300, 300)
    val vectorPath = Path().apply {
        moveTo(6.5f, 79.99f)
        lineTo(37.21f, 50.5f)
        lineTo(6.5f, 19.79f)
        lineTo(18.79f, 7.5f)
        lineTo(49.5f, 38.21f)
        lineTo(80.21f, 7.5f)
        lineTo(92.5f, 19.79f)
        lineTo(61.79f, 50.5f)
        lineTo(92.5f, 79.99f)
        lineTo(80.21f, 93.5f)
        lineTo(49.5f, 62.79f)
        lineTo(18.79f, 93.5f)

//        addCircle(10f,10f,60f,Path.Direction.CCW)
        close()
    }


    private fun setupEraser() {
        eraser = Paint().apply {
//            style = Paint.Style.FILL_AND_STROKE
            color = ContextCompat.getColor(context, android.R.color.transparent)
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

            isAntiAlias = true
        }


        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    init {
        setupEraser()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        val d =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_android_black_24dp, context.theme)
                ?.apply {

//                    setTintBlendMode(BlendMode.valueOf("DST_ATOP"))
//                    setTint(Color.LTGRAY)
//                    setTintMode(PorterDuff.Mode.SRC_ATOP)
                }?.toBitmap()

        val b = BitmapFactory.decodeResource(resources, R.drawable.ic_vtyping_share1)

//        d?.bounds = Rect(100, 100, 300, 300)
//        d?.draw(canvas!!)

//        canvas?.drawBitmap(d!!, 100f, 100f, eraser)


        val width = bounds.width()
        val height = bounds.height()

// Calculate a transformation scale between [0, 0, 100, 100] and [0, 0, width, height].
        val scaleX = width / 100.0f
        val scaleY = height / 100.0f

// Create the transformation matrix.
        val drawMatrix = Matrix()
        drawMatrix.setScale(scaleX, scaleY)

// Now transform the vector path.
        vectorPath.transform(drawMatrix)


        val fillPaint = Paint()
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = Color.GREEN
        fillPaint.isAntiAlias = true
        fillPaint.isDither = true

        val borderPaint = Paint()
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 10f
        borderPaint.color = Color.BLACK
        borderPaint.isAntiAlias = true
        borderPaint.isDither = true


        ///////////////  this did work//////////////////

        val data =
            VectorDrawableParser.parsedVectorDrawable(resources, R.drawable.ic_android_black_24dp)

        val path = PathParser.createPathFromPathData(data?.pathData)
        val m = Matrix()
        m.setScale(24f, 24f)

// Now transform the vector path.
        path.transform(m)
        canvas?.drawPath(path, eraser)

//
// Then overlap this with the border path.
//        canvas.drawPath(borderPath, borderPaint)
    }


}