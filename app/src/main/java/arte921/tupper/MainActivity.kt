package arte921.tupper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
        kView.text = pixels.getK()
    }

    fun copy(view: View){
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("K", pixels.getK())
        clipboard.setPrimaryClip(clip)
        kView.text = getString(R.string.ctoclip)
    }

    fun paste(view: View){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val ktext = clipboard?.primaryClip?.getItemAt(0)?.text.toString()
        if(!ktext.isBlank()){
            kView.text = getString(R.string.pfromclip)
            pixels.getFromString(ktext)
        } else kView.text = getString(R.string.nonuminclip)
        canvasView.invalidate()
    }

    fun share(view: View){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "K")
        shareIntent.putExtra(Intent.EXTRA_TEXT,pixels.getK())
        startActivity(Intent.createChooser(shareIntent, getString(R.string.sharevia)))
    }


}
