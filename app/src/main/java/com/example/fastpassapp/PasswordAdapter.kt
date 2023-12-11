import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.fastpassapp.DatabaseHandler
import com.example.fastpassapp.R

class PasswordAdapter(private val context: Activity, private val passwordList: Array<String>)
    : ArrayAdapter<String>(context, R.layout.item_password, passwordList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.item_password, null, true)

        val passwordText = rowView.findViewById<TextView>(R.id.passwordTextView)
        val btnCopy = rowView.findViewById<ImageButton>(R.id.btnCopy)

        passwordText.text = "${passwordList[position]}"

        btnCopy.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Password", passwordList[position])
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }


        return rowView
    }
}