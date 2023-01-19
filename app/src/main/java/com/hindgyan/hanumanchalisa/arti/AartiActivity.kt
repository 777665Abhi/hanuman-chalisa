package com.hindgyan.hanumanchalisa.arti

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hindgyan.hanumanchalisa.R
import com.hindgyan.hanumanchalisa.databinding.ActivityAartiBinding
import com.hindgyan.hanumanchalisa.home.HomeAdapter
import com.hindgyan.hanumanchalisa.utils.AartiData
import com.hindgyan.hanumanchalisa.utils.LanguageUtil.Companion.setAppLocale
import com.hindgyan.hanumanchalisa.utils.SharePreData

class AartiActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityAartiBinding
    private var meaning: Boolean = false
    var englishVersion: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_aarti)
        // Registering listeners
        binding.bnLanguage.setOnClickListener(this)
        binding.bnMeaning.setOnClickListener(this)

        settingData()
    }

    /**Setting language dynamically*/
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale("hi")))
    }

    private fun settingData() {
        //Initializing rv
        val homeAdapter = if (SharePreData.getLanguage(this) == "hi") {
            HomeAdapter(AartiData.hindiAartiArrayData(), AartiData.hindiMeaningList(), false)
        } else {
            HomeAdapter(AartiData.englishAartiList(), AartiData.englishMeaningList(), false)
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
                        AartiData.englishAartiList(),
                        AartiData.englishMeaningList(),
                        meaning
                    )
                } else {
                    //                 IntentUtil.startActivity(this, HomeActivity::class.java, true)
                    binding.bnLanguage.text = "अंग्रेज़ी"
                    HomeAdapter(
                        AartiData.hindiAartiArrayData(),
                        AartiData.hindiMeaningList(),
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
                        AartiData.englishAartiList(),
                        AartiData.englishMeaningList(),
                        meaning
                    )
                else
                    HomeAdapter(
                        AartiData.hindiAartiArrayData(),
                        AartiData.hindiMeaningList(),
                        meaning
                    )
                binding.rvData.layoutManager = LinearLayoutManager(this)
                binding.rvData.adapter = homeAdapter
                homeAdapter.notifyDataSetChanged()
            }
        }

    }
}