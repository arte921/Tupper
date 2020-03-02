package arte921.tupper

import android.content.Context
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.floor

class Pixels(val xw: Float, val yw: Float) {
    var drawing = true
    var canvash = 0f
    var canvasw = 0f
    var size = 0f

    var grid: Array<BooleanArray> = Array(xw.toInt()) { BooleanArray(yw.toInt()) { false } }

    fun updateSize(w: Int,h: Int){
        if(w/xw<h/yw){
            canvasw = w.toFloat()
            canvash = xw*h.toFloat()/yw
            size = canvasw/xw
        }else{
            canvash = h.toFloat()
            canvasw = yw*w.toFloat()/xw
            size = canvash/yw
        }
    }

    fun xtoc(gx: Float): Float = gx*size
    fun ytoc(gy: Float): Float = gy*size

    fun ctox(cx: Float): Float = floor(cx/size)
    fun ctoy(cy: Float): Float = floor(cy/size)

    fun touched(cx: Float, cy: Float) = apply(ctox(cx),ctoy(cy))

    private fun apply(gx: Float, gy: Float) {
        grid[gx.toInt().coerceIn(0 until xw.toInt())][gy.toInt().coerceIn(0 until yw.toInt())] = drawing
    }

    fun clear(){
        for(x in 0 until pixels.xw.toInt()) for(y in 0 until pixels.yw.toInt()){
            grid[x][y] = false
        }
    }

    fun getFromString(input: String){
        val bigK = BigInteger(input,10)
        for(xi in 0 until pixels.xw.toInt()) for(yi in 0 until pixels.yw.toInt()){
            val x: Int = xi
            val y: BigInteger = yi.toBigInteger() + bigK
            grid[(xw-1-xi).toInt()][yi] = 1/2 < floor(((y.divideAndRemainder(17.toBigInteger())[0].divide(2.toBigInteger().pow((17*x).toBigInteger().plus(y.mod(17.toBigInteger())).toInt()))).mod(2.toBigInteger())).toDouble())
        }
    }
}