package com.app.egyptlegacy.first

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.egyptlegacy.MainViewModel
import com.app.egyptlegacy.R
import com.app.egyptlegacy.databinding.ActivityLoadingBinding
import com.app.egyptlegacy.play.PlActivity
import com.app.egyptlegacy.w.WView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textAnimation()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest { url ->
                    if (url.isEmpty()) return@collectLatest
                    val intet = Intent(this@FirstActivity, WView::class.java)
                    intet.putExtra("web_url", url)
                    startActivity(intet)
                    finish()
                }
            }
        }

        viewModel.isNotADB.observe(this) {
            if (it == "true") {
                viewModel.init(this)
            } else if (it == "false") {
                startActivity(Intent(this, PlActivity::class.java))
                finish()
            }
        }
    }


    private fun textAnimation() {
        val animation: Animation = AnimationUtils
            .loadAnimation(this, R.anim.animation_text)
        binding.txtLoadingText.startAnimation(animation)
    }
}