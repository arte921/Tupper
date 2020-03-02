package arte921.tupper

import java.math.BigInteger
import kotlin.math.floor

class Pixels(val xw: Float, val yw: Float) {
    var drawing = true
    var canvash = 0f
    var canvasw = 0f
    var size = 0f
    var kString = ""

    var grid: Array<BooleanArray> = Array(xw.toInt()) { BooleanArray(yw.toInt()) { false } }

    var cache = ""
    var oldGrid = Array(xw.toInt()) { BooleanArray(yw.toInt()) { false } }

    init{
        getFromString("960 939 379 918 958 884 971 672 962 127 852 754 715 004 339 660 129 306 651 505 519 271 702 802 395 266 424 689 642 842 174 350 718 121 267 153 782 770 623 355 993 237 280 874 144 307 891 325 963 941 337 723 487 857 735 749 823 926 629 715 517 173 716 995 165 232 890 538 221 612 403 238 855 866 184 013 235 585 136 048 828 693 337 902 491 454 229 288 667 081 096 184 496 091 705 183 454 067 827 731 551 705 405 381 627 380 967 602 565 625 016 981 482 083 418 783 163 849 115 590 225 610 003 652 351 370 343 874 461 848 378 737 238 198 224 849 863 465 033 159 410 054 974 700 593 138 339 226 497 249 461 751 545 728 366 702 369 745 461 014 655 997 933 798 537 483 143 786 841 806 593 422 227 898 388 722 980 000 748 404 719")
    }

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
        for(x in 0 until xw.toInt()) for(y in 0 until yw.toInt()){
            grid[x][y] = false
        }
    }

    fun getFromString(input: String){
        val bigK = BigInteger(input.replace(Regex("[^0-9]"), ""),10)
        for(xi in 0 until xw.toInt()) for(yi in 0 until yw.toInt()){
            val x: Int = xi
            val y: BigInteger = yi.toBigInteger() + bigK
            grid[(xw-1-xi).toInt()][yi] = 1/2 < floor(((y.divideAndRemainder(17.toBigInteger())[0].divide(2.toBigInteger().pow((17*x).toBigInteger().plus(y.mod(17.toBigInteger())).toInt()))).mod(2.toBigInteger())).toDouble())
        }
    }

    fun getK(): String{
        if(grid.contentEquals(oldGrid)) return cache
        var binaryString = ""
        for(x in 0 until xw.toInt()) for(y in yw.toInt()-1 downTo 0){
            binaryString += if(grid[x][y]) "1" else "0"
        }
        val kBigInt = BigInteger(binaryString,2)
        kString = (kBigInt * yw.toInt().toString().toBigInteger()).toString(10)
        cache = kString
        return kString
    }


}