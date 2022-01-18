package com.tsu.bubblesortrun


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.tsu.bubblesortrun.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewbinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)
        viewbinding.buttonStart.setOnClickListener {
            val intent =
                Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }


}
