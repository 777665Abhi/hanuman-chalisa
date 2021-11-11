package com.hindgyan.hanumanchalisa.home

import android.content.Context
import android.content.ContextWrapper
import android.media.MediaPlayer
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
import com.google.android.material.navigation.NavigationView
import com.hindgyan.hanumanchalisa.R
import com.hindgyan.hanumanchalisa.databinding.ActivityHomeBinding
import com.hindgyan.hanumanchalisa.dialog.ContactFragment
import com.hindgyan.hanumanchalisa.dialog.ExitFragment
import com.hindgyan.hanumanchalisa.dialog.SuggestionFragment
import com.hindgyan.hanumanchalisa.utils.Data
import com.hindgyan.hanumanchalisa.utils.FragmentUtil
import com.hindgyan.hanumanchalisa.utils.LanguageUtil.Companion.setAppLocale
import com.hindgyan.hanumanchalisa.utils.SharePreData


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

    private fun init() {
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            R.id.nav_rate -> {
            }

            R.id.nav_suggestion -> {
                FragmentUtil.showDialog(
                    supportFragmentManager,
                    SuggestionFragment(),
                    "Suggestion_Tag"
                )
            }

            R.id.nav_contact -> {
                FragmentUtil.showDialog(supportFragmentManager, ContactFragment(), "Contact_Tag")
            }

            R.id.nav_about -> {
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
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

    /**On click listener*/
    override fun onClick(v: View?) {
        when (v) {

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

            binding.bnPlay -> {
                playAudio()
            }
        }
    }

    private fun playAudio() {
        if (mediaPlayer.isPlaying) {
            binding.bnPlay.setImageResource(R.drawable.ic_play_24)
            mediaPlayer.pause()
        }
        else {
            binding.bnPlay.setImageResource(R.drawable.ic_pause_24)
            mediaPlayer.start()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        // var language=SharePreData.getLanguage(this)
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale("hi")))
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
}