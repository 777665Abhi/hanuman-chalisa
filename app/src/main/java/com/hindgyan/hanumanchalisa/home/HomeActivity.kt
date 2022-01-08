package com.hindgyan.hanumanchalisa.home

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import com.google.android.play.core.tasks.OnCompleteListener
import com.google.android.play.core.tasks.OnFailureListener
import com.google.android.play.core.tasks.Task
import com.hindgyan.hanumanchalisa.AboutUsActivity
import com.hindgyan.hanumanchalisa.R
import com.hindgyan.hanumanchalisa.databinding.ActivityHomeBinding
import com.hindgyan.hanumanchalisa.dialog.ContactFragment
import com.hindgyan.hanumanchalisa.dialog.ExitFragment
import com.hindgyan.hanumanchalisa.dialog.SuggestionFragment
import com.hindgyan.hanumanchalisa.utils.*
import com.hindgyan.hanumanchalisa.utils.LanguageUtil.Companion.setAppLocale


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    lateinit var binding: ActivityHomeBinding
    var doubleBackToExitPressedOnce = false
    private var meaning: Boolean = false
    var englishVersion: Boolean = false
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    /**Screen initialization*/
    private fun init() {
        //Initialize the Google Mobile Ads SDK
        MobileAds.initialize(this)
        //Load ad
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        /*Hamburg icon sync in toolbar*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        /*Listener*/
        binding.nvView.setNavigationItemSelectedListener(this)
        binding.bnLanguage.setOnClickListener(this)
        binding.bnHide.setOnClickListener(this)
        binding.bnPlay.setOnClickListener(this)

        /*Setting initial data*/
        val homeAdapter = if (SharePreData.getLanguage(this).equals("hi")) {
            HomeAdapter(Data.hindiArrayData(), Data.hindiArrayData(), false)

        } else {
            HomeAdapter(Data.hindiArrayData(), Data.englishArrayData(), false)
        }
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = homeAdapter
        /*Player instance*/
        mediaPlayer = MediaPlayer.create(this, R.raw.hanuman_chalisa)
    }

    /**Hamburg icon functionality*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                else
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**Side navigation click handler*/
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            R.id.nav_rate -> {
                rateApp(this)
            }

            R.id.nav_suggestion -> {
                Util.makeEmail(this)
//                FragmentUtil.showDialog(
//                    supportFragmentManager,
//                    SuggestionFragment(),
//                    "Suggestion_Tag"
//                )
            }

            R.id.nav_contact -> {
                FragmentUtil.showDialog(supportFragmentManager, ContactFragment(), "Contact_Tag")
            }

            R.id.nav_about -> {
                IntentUtil.startActivity(this, AboutUsActivity::class.java, false)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**On click listener*/
    override fun onClick(v: View?) {
        when (v) {

            //Switch language
            binding.bnLanguage -> {
                englishVersion = !englishVersion
                var homeAdapter = if (englishVersion) {
                    binding.bnLanguage.text = "हिंदी"
                    HomeAdapter(Data.englishArrayData(), Data.englishArrayData(), meaning)
                } else {
                    //                 IntentUtil.startActivity(this, HomeActivity::class.java, true)
                    binding.bnLanguage.text = "अंग्रेज़ी"
                    HomeAdapter(Data.hindiArrayData(), Data.hindiArrayData(), meaning)
                }
                binding.rvData.layoutManager = LinearLayoutManager(this)
                binding.rvData.adapter = homeAdapter
                homeAdapter.notifyDataSetChanged()
            }

            //Switch the meaning
            binding.bnHide -> {
                meaning = !meaning
                var homeAdapter = if (englishVersion)
                    HomeAdapter(Data.englishArrayData(), Data.englishArrayMeaningData(), meaning)
                else
                    HomeAdapter(Data.hindiArrayData(), Data.hindiMeaningArrayData(), meaning)
                binding.rvData.layoutManager = LinearLayoutManager(this)
                binding.rvData.adapter = homeAdapter
                homeAdapter.notifyDataSetChanged()
            }

            //Play music
            binding.bnPlay -> {
                playAudio()
            }
        }
    }

    /**Fun play the music*/
    private fun playAudio() {
        if (mediaPlayer.isPlaying) {
            binding.bnPlay.setImageResource(R.drawable.ic_play_24)
            mediaPlayer.pause()
        } else {
            binding.bnPlay.setImageResource(R.drawable.ic_pause_24)
            mediaPlayer.start()
        }
    }

    /**Rate the app*/
    private fun rateApp() {
//        val manager = ReviewManagerFactory.create(this)
        val manager = FakeReviewManager(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = task.result
            } else {
                // There was some problem, log or handle the error code.
                // @ReviewErrorCode val reviewErrorCode = (task.getException() as TaskException).errorCode
            }
        }
    }

    private fun rateApp(mContext: Context?) {
        try {
            val manager = ReviewManagerFactory.create(mContext!!)
            manager.requestReviewFlow()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val reviewInfo: ReviewInfo = it.result
                        manager.launchReviewFlow((mContext as Activity?)!!, reviewInfo)
                            .addOnFailureListener{ToastUtil.showToast(mContext,"Rating Failed")}
                            .addOnCompleteListener{ToastUtil.showToast(mContext,"Review Completed, Thank You!")}
                    }
                }
                .addOnFailureListener{ToastUtil.showToast(mContext,"Inapp rating is failed") }

        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    /**Setting language dynamically*/
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale("hi")))
    }

    /**Back button handling*/
    override fun onBackPressed() {
        val any = if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                // super.onBackPressed()
                FragmentUtil.showDialog(supportFragmentManager, ExitFragment(), "Exit_Tag")
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.double_back), Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)
        }
    }

    /**Close player while destroy*/
    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
}