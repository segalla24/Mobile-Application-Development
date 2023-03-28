package edu.cofc.android.gungantranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import edu.cofc.android.gungantranslator.databinding.ActivityMainBinding
import java.net.URL
import java.net.URLEncoder
import org.json.JSONObject
import kotlinx.coroutines.*
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.enterText.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        binding.translateButton.setOnClickListener {
            val englishText = binding.enterText.text.toString()
            ioScope.launch {
                val gunganStr = getGunganString(englishText)
                withContext(Dispatchers.Main) {
                    binding.translatedText.text = gunganStr
                }
            }
        }
    }
    private suspend fun getGunganString(englishText: String): String {
        var gunganStr: String
        val baseUrl =
            "https://api.funtranslations.com/translate/gungan.json?text="
        val encodedEnglishText = withContext(Dispatchers.IO) {
            URLEncoder.encode(englishText, "UTF-8")
        }
        val url = URL(baseUrl + encodedEnglishText)
        try {
            val gunganJSON = url.readText()
            // extract gungan text from json response
            val jsonObj = JSONObject(gunganJSON)
            if (jsonObj.has("success")) {
                val contents = jsonObj.getJSONObject("contents")
                gunganStr = contents.getString("translated")
            }
            else {
                gunganStr = resources.getString(R.string.translation_failed)
            }
        }
        catch (ex : Exception) {
            Log.e("GunganTranslation", ex.stackTraceToString())
            gunganStr = "${ex.message}"
        }
        return gunganStr
    }
}


