package com.hindgyan.hanumanchalisa.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hindgyan.hanumanchalisa.R
import com.hindgyan.hanumanchalisa.databinding.FragmentContactBinding
import com.hindgyan.hanumanchalisa.databinding.FragmentSuggestionBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactFragment : DialogFragment(),View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(layoutInflater)
        init()
        return binding.root    }

    private fun init() {
        binding.lnEmail.setOnClickListener(this)
        binding.lnPhone.setOnClickListener(this)
    }
    override fun onResume() {
        // Get existing layout params for the window
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        // Call super onResume after sizing
        super.onResume()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.lnEmail -> {
            }
            binding.lnPhone -> {
            }
        }
        dismiss()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}