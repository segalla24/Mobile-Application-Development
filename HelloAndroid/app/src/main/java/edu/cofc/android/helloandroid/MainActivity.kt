package edu.cofc.android.helloandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.cofc.android.helloandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}