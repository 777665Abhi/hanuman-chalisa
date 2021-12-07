package com.hindgyan.hanumanchalisa

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.hindgyan.hanumanchalisa.home.HomeActivity
import com.hindgyan.hanumanchalisa.utils.IntentUtil


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {

        //Moving to Home screen
        Handler(Looper.getMainLooper()).postDelayed({
            IntentUtil.startActivity(this, HomeActivity::class.java, true)
        }, 3000)
    }
}