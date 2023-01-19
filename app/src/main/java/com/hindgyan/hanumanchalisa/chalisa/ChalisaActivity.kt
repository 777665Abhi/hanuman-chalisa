package com.hindgyan.hanumanchalisa.chalisa

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hindgyan.hanumanchalisa.R
import com.hindgyan.hanumanchalisa.databinding.ActivityChalisaBinding
import com.hindgyan.hanumanchalisa.home.HomeAdapter
import com.hindgyan.hanumanchalisa.utils.AartiData
import com.hindgyan.hanumanchalisa.utils.Data
import com.hindgyan.hanumanchalisa.utils.LanguageUtil.Companion.setAppLocale
import com.hindgyan.hanumanchalisa.utils.SharePreData

class ChalisaActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var binding:ActivityChalisaBinding
    private var meaning: Boolean = false
    var englishVersion: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_chalisa)
        // Registering listeners
        binding.bnLanguage.setOnClickListener(this)
        binding.bnMeaning.setOnClickListener(this)

        settingData()

    }
    private fun settingData()
    {
        //Initializing rv
        val homeAdapter = if (SharePreData.getLanguage(this) == "hi") {
            HomeAdapter(Data.hindiArrayData(), Data.hindiMeaningArrayData(), false)
        } else {
            HomeAdapter(Data.englishArrayData(), Data.englishArrayMeaningData(), false)
        }
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = homeAdapter
    }
    /**Setting language dynamically*/
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale("hi")))
    }
    override fun onClick(view: View?) {
        when(view)
        {
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
            binding.bnMeaning -> {
                meaning = !meaning
                var homeAdapter = if (englishVersion)
                    HomeAdapter(Data.englishArrayData(), Data.englishArrayMeaningData(), meaning)
                else
                    HomeAdapter(Data.hindiArrayData(), Data.hindiMeaningArrayData(), meaning)
                binding.rvData.layoutManager = LinearLayoutManager(this)
                binding.rvData.adapter = homeAdapter
                homeAdapter.notifyDataSetChanged()
            }
        }

    }
}