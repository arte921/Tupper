package arte921.tupper

import java.math.BigInteger

val pixels = Pixels(106f,17f)
var kString = ""

fun getK(): String{
    var binaryString = ""
    for(x in 0 until pixels.xw.toInt()) for(y in pixels.yw.toInt()-1 downTo 0){
        binaryString += if(pixels.grid[x][y]) "1" else "0"
    }
    var kBigInt = BigInteger(binaryString,2)
    kString = (kBigInt * pixels.yw.toInt().toString().toBigInteger()).toString(10)

    return kString
}








