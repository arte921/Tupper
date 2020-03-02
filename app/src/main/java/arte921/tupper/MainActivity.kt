package arte921.tupper

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawSwitch.setOnCheckedChangeListener { _, isChecked ->
            pixels.drawing = isChecked
        }
    }

    fun clear(view: View){
        pixels.clear()
        canvasView.invalidate()
    }

    fun updateString(view: View) {
        kView.text = getK()
    }

    fun copy(view: View){
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("K", getK())
        clipboard.setPrimaryClip(clip)
    }

    fun paste(view: View){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val ktext = clipboard?.primaryClip?.getItemAt(0)?.text.toString().replace(Regex("[^0-9]"), "")
        if(!ktext.isBlank()){
            kView.text = ktext
            pixels.getFromString(ktext)
        } else kView.text = getString(R.string.nonuminclip)

        canvasView.invalidate()
    }
}
