package com.hindgyan.hanumanchalisa.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hindgyan.hanumanchalisa.databinding.FragmentExitBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExitFragment : DialogFragment(), View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentExitBinding
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
        binding = FragmentExitBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        binding.bnExit.setOnClickListener(this)
        binding.bnCancel.setOnClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.bnExit -> {
                dismiss()
                requireActivity().finish()
            }
            binding.bnCancel -> {
                dismiss()
            }
        }
    }
}