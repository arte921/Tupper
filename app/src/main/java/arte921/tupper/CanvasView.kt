package arte921.tupper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CanvasView(context: Context, attrs: AttributeSet): View(context, attrs) {
    private val gridColor = ResourcesCompat.getColor(resources,R.color.gridColor,null)
    private val pxColor = ResourcesCompat.getColor(resources,R.color.pxColor,null)
    private val bgColor = ResourcesCompat.getColor(resources,R.color.bgColor,null)

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val gridPaint = Paint().apply {
        color = gridColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 1f
    }

    private val pixelPaint = Paint().apply {
        color = pxColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 1f
        textSize = 100f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(bgColor)

        pixels.updateSize(w,h)
        invalidate()
    }


    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0F,0F,null)

        for(xi in 0 until pixels.xw.toInt()) for(yi in 0 until pixels.yw.toInt()){
            var gx = xi.toFloat()
            var gy = yi.toFloat()
            if(pixels.grid[xi][yi]) canvas.drawRect(pixels.xtoc(gx),pixels.ytoc(gy),pixels.xtoc(gx)+pixels.size,pixels.ytoc(gy)+pixels.size,pixelPaint)
        }

        for(xi in 0..pixels.xw.toInt()){
            val x = xi.toFloat() * pixels.size
            canvas.drawLine(x+0.5f,0f,x+0.5f,pixels.yw*pixels.size,gridPaint)
        }

        for(yi in 0..pixels.yw.toInt()){
            val y = yi.toFloat() * pixels.size
            canvas.drawLine(0f,y+0.5f,pixels.xw * pixels.size,y+0.5f,gridPaint)
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        /*
        when(e.action){
            MotionEvent.ACTION_UP -> {
                pixels.touched(e.x,e.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                GlobalScope.launch{
                    pixels.touched(e.x,e.y)
                }
                invalidate()
            }
        }*/
        pixels.touched(e.x,e.y)
        invalidate()
        return true
    }
}