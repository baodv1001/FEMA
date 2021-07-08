package com.example.fashionecommercemobileapp.views

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionecommercemobileapp.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        progress_bar_splash.max=2000
        object : CountDownTimer(2000, 10) {

            override fun onTick(millisUntilFinished: Long) {
                progress_bar_splash.progress = 2000-millisUntilFinished.toInt()
            }

            override fun onFinish() {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java).apply { }
                startActivity(intent)
                this@SplashActivity.finish()
            }
        }.start()
    }
    private fun setProgressAnimate(pb: ProgressBar, progressTo: Int) {
        val animation = ObjectAnimator.ofInt(pb, "progress", pb.progress, progressTo)
        animation.duration = 500
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }
}