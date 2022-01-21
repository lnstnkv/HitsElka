package com.tsu.hitselka.activity_enhancement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityEnhancementBinding
import com.tsu.hitselka.model.GameData
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.setFullscreen

class EnhancementActivity : AppCompatActivity(R.layout.activity_enhancement) {
    private val binding by lazy { ActivityEnhancementBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<EnhancementViewModel>()

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

        initListeners()
        viewModel.initViewModel(item)

        binding.titleTextView.setText(item.name)
        binding.objectImageView.setImageResource(item.image)
        binding.stageTextView.text = resources.getString(R.string.stage, item.level, item.maxLevel)

        binding.closeButton.setOnClickListener {
            finish()
        }

        val listener = object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    viewModel.startSpending()
                } else if (event?.action == MotionEvent.ACTION_UP) {
                    viewModel.stopSpending()
                }
                return true
            }
        }
        binding.wandButton.setOnTouchListener(listener)
    }

    private fun initListeners() {
        viewModel.wandsRemain.observe(this) { wandsRemain ->
            binding.progressTextView.text = resources.getString(R.string.upgrade, wandsRemain)
        }

        viewModel.objectProgress.observe(this) { progress ->
            if (progress >= 5) {
                val width = getProgressBarWidth(progress)
                binding.progressView.visibility = View.VISIBLE
                binding.progressView.layoutParams.width = width
            }
        }

        viewModel.levelProgress.observe(this) { progress ->
            val width = getLevelBarWidth(progress)
            if (width > 0) {
                binding.levelFillView.visibility = View.VISIBLE
                binding.levelFillView.layoutParams.width = width
            }
        }

        GameData.stats.observe(this) { stats ->
            viewModel.setStats(stats)
            binding.levelTextView.text = stats.currentLevel.toString()
        }

        GameData.resources.observe(this) { resources ->
            viewModel.setAvailableWands(resources.wands)
        }

        viewModel.wands.observe(this) { wands ->
            binding.wandTextView.text = wands.toString()

            if (wands == 0L) {
                binding.wandButton.isClickable = false
            }
        }
    }

    private fun getProgressBarWidth(percentage: Long): Int {
        val maxWidth = resources.getDimensionPixelSize(R.dimen.midterms_progress_width)
        return maxWidth * percentage.toInt() / 100
    }

    private fun getLevelBarWidth(percentage: Long): Int {
        val maxWidth = resources.getDimensionPixelSize(R.dimen.level_progress_width)
        return maxWidth * percentage.toInt() / 100
    }

}