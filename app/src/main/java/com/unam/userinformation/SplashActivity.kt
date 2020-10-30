package com.unam.userinformation

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieDrawable
import com.unam.userinformation.databinding.ActivityUnamSplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnamSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnamSplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.liveData.observe(this, Observer {
            if (it is SplashState.SplashActivity) {
                goToMainActivity()
            }
        })

        setupView()
    }

    private fun setupView() {
        binding.apply {
            imageViewUnam.setImageResource(R.drawable.unam_logo)
            imageViewFi.setImageResource(R.drawable.facultad_ingenieria_logo)
            textViewInfo.text = getString(R.string.app_info)
            animationViewSplashAndroid.apply {
                removeAllAnimatorListeners()
                setAnimation(context.getString(R.string.unam_lot_android_logo))
                repeatCount = LottieDrawable.INFINITE
                playAnimation()
            }
        }
        animationStart()
    }

    private fun animationStart() {
        binding.layoutSplash.background = getDrawable(R.drawable.gradient_flow)
        var animationDrawable = binding.layoutSplash.background as AnimationDrawable
        animationDrawable.start()
    }

    private fun goToMainActivity() {
        finish()
        startActivity(Intent(this, UserDataActivity::class.java))
    }
}