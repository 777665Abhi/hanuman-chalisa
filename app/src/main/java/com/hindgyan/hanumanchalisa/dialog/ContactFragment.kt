package com.hindgyan.hanumanchalisa.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hindgyan.hanumanchalisa.databinding.FragmentContactBinding
import com.hindgyan.hanumanchalisa.utils.ToastUtil

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactFragment : DialogFragment(), View.OnClickListener {
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
        return binding.root
    }

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
                makeEmail(requireContext())
            }
            binding.lnPhone -> {
                makeCall(requireContext(), "8283820405")
            }
        }
        dismiss()
    }


    /**Call to phone*/
    private fun makeCall(context: Context, mob: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)

            intent.data = Uri.parse("tel:$mob")
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {
            ToastUtil.showToast(context,"Unable to call at this time")
        }
    }

    /**Email to feedback*/
    private fun makeEmail(context: Context) {
        try {
            val emailIntent =  Intent(Intent.ACTION_SEND);
            emailIntent.type = "text/plain";
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("address@domain.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Some Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Some body");
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (e: Exception) {
            ToastUtil.showToast(context,"Unable to email at this time")

        }
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