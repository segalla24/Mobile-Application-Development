package edu.cofc.android.huttesetranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import edu.cofc.android.huttesetranslator.databinding.ActivityMainBinding
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
                val yodaStr = getHutteseString(englishText)
                withContext(Dispatchers.Main) {
                    binding.translatedText.text = yodaStr
                }
            }
        }
    }
    private suspend fun getHutteseString(englishText: String): String {
        var hutteseStr: String
        val baseUrl =
            "https://api.funtranslations.com/translate/huttese.json?text="
        val encodedEnglishText = withContext(Dispatchers.IO) {
            URLEncoder.encode(englishText, "UTF-8")
        }
        val url = URL(baseUrl + encodedEnglishText)
        try {
            val hutesseJSON = url.readText()
            // extract yoda text from json response
            val jsonObj = JSONObject(hutesseJSON)
            if (jsonObj.has("success")) {
                val contents = jsonObj.getJSONObject("contents")
                hutteseStr = contents.getString("translated")
            }
            else {
                hutteseStr = resources.getString(R.string.translation_failed)
            } }

        catch (ex : Exception) {
            Log.e("HutesseTranslation", ex.stackTraceToString())
            hutteseStr = "${ex.message}"
        }
        return hutteseStr
    }
}