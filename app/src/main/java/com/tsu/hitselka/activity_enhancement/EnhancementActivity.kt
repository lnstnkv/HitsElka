package com.tsu.hitselka.activity_enhancement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityEnhancementBinding
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.setFullscreen

class EnhancementActivity : AppCompatActivity(R.layout.activity_enhancement) {
    private val binding by lazy { ActivityEnhancementBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullscreen()

        val intent = intent
        val item = intent.getParcelableExtra<Object>("ObjectInfo")
        if (item == null) {
            finish()
            return
        }

        binding.titleTextView.setText(item.name)
        binding.objectImageView.setImageResource(item.image)
        binding.stageTextView.text = resources.getString(R.string.stage, item.level, item.maxLevel)

        val wandsRemain = item.wandsNeeded - item.wandsSpent
        binding.progressTextView.text = resources.getString(R.string.upgrade, wandsRemain)

        val percentage = item.wandsSpent * 100 / item.wandsNeeded
        val width = getProgressBarWidth(percentage)
        if (width == 0) {
            binding.progressView.visibility = View.INVISIBLE
        } else {
            binding.progressView.layoutParams.width = width
        }

        binding.closeButton.setOnClickListener {
            finish()
        }
    }

    private fun getProgressBarWidth(percentage: Int): Int {
        val maxWidth = resources.getDimensionPixelSize(R.dimen.midterms_progress_width)
        return maxWidth * percentage / 100
    }
}