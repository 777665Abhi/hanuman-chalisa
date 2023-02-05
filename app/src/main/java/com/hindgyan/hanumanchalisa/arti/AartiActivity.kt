package com.hindgyan.hanumanchalisa.arti

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hindgyan.hanumanchalisa.R
import com.hindgyan.hanumanchalisa.databinding.ActivityAartiBinding
import com.hindgyan.hanumanchalisa.home.HomeAdapter
import com.hindgyan.hanumanchalisa.utils.*
import com.hindgyan.hanumanchalisa.utils.LanguageUtil.Companion.setAppLocale

class AartiActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityAartiBinding
    private var meaning: Boolean = false
    var englishVersion: Boolean = false

    var hindiList = ArrayList<String>()
    var hindiMeanList = ArrayList<String>()
    var englishList = ArrayList<String>()
    var englishMeanList = ArrayList<String>()

    var code=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_aarti)
        // Registering listeners
        binding.bnLanguage.setOnClickListener(this)
        binding.bnMeaning.setOnClickListener(this)
        code=intent.getIntExtra(Constants.OPTION,1)
        init(code)
    }

    private fun init(code: Int) {

        //enabling back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        when (code) {

            1 -> {
                this@AartiActivity.title = "हनुमान चालीसा"
                hindiList.addAll(Data.hindiArrayData())
                hindiMeanList.addAll(Data.hindiMeaningArrayData())
                englishList.addAll(Data.englishArrayData())
                englishMeanList.addAll(Data.englishArrayData())
            }
            2 -> {
                this@AartiActivity.title = "संकट मोचन हनुमानाष्टक"
                hindiList.addAll(AshtakData.hindiList())
                hindiMeanList.addAll(AshtakData.hindiMeaningList())
                englishList.addAll(AshtakData.englishList())
                englishMeanList.addAll(AshtakData.englishMeaningList())
            }
            3 -> {
                this@AartiActivity.title="श्री बजरंग बाण"
                hindiList.addAll(BaanData.hindiList())
                hindiMeanList.addAll(BaanData.hindiMeanList())
                englishList.addAll(BaanData.englishList())
                englishMeanList.addAll(BaanData.hindiMeanList())
            }
            4 -> {
                this@AartiActivity.title="हनुमान आरती"
                hindiList.addAll(AartiData.hindiList())
                hindiMeanList.addAll(AartiData.hindiMeaningList())
                englishList.addAll(AartiData.englishList())
                englishMeanList.addAll(AartiData.englishMeaningList())
            }
        }
        settingData()
    }

    /**Setting language dynamically*/
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale("hi")))
    }

    private fun settingData() {

        //Initializing rv
        val homeAdapter = if (SharePreData.getLanguage(this) == "hi") {
            HomeAdapter(hindiList, hindiMeanList, false)
        } else {
            HomeAdapter(englishList, englishMeanList, false)
        }
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = homeAdapter
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.bnLanguage -> {
                englishVersion = !englishVersion
                var homeAdapter = if (englishVersion) {
                    binding.bnLanguage.text = "हिंदी"
                    HomeAdapter(
                       englishList,
                       englishMeanList,
                        meaning
                    )
                } else {
                    //                 IntentUtil.startActivity(this, HomeActivity::class.java, true)
                    binding.bnLanguage.text = "अंग्रेज़ी"
                    HomeAdapter(
                       hindiList,
                        hindiMeanList,
                        meaning
                    )
                }
                binding.rvData.layoutManager = LinearLayoutManager(this)
                binding.rvData.adapter = homeAdapter
                homeAdapter.notifyDataSetChanged()
            }

            //Switch the meaning
            binding.bnMeaning -> {
                meaning = !meaning
                var homeAdapter = if (englishVersion)
                    HomeAdapter(
                        englishList,
                        englishMeanList,
                        meaning
                    )
                else
                    HomeAdapter(
                        hindiList,
                        hindiMeanList,
                        meaning
                    )
                binding.rvData.layoutManager = LinearLayoutManager(this)
                binding.rvData.adapter = homeAdapter
                homeAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}