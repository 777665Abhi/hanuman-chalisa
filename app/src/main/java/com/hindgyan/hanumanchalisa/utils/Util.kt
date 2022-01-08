package com.hindgyan.hanumanchalisa.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class Util {
    companion object{
    /**Email to feedback*/
     fun makeEmail(context: Context) {
        try {
            val emailIntent =  Intent(Intent.ACTION_SEND);
            emailIntent.type = "text/plain";
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("abhi777665@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion to developer");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi Daily app developer,");
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (e: Exception) {
            ToastUtil.showToast(context,"Unable to email at this time")
        }
    }


        /**Call to phone*/
         fun makeCall(context: Context, mob: String) {
            try {
                val intent = Intent(Intent.ACTION_DIAL)

                intent.data = Uri.parse("tel:$mob")
                context.startActivity(intent)
            } catch (e: java.lang.Exception) {
                ToastUtil.showToast(context,"Unable to call at this time")
            }
        }
    }
}