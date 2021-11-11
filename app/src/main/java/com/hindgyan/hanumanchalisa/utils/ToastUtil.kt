package com.hindgyan.hanumanchalisa.utils

import android.content.Context
import android.widget.Toast

class ToastUtil {
    companion object {
        /**Show toast
         * @param context Context of view
         * @param massage Massage want to toast*/
        fun showToast(context: Context, massage: String) {
            Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
        }

        /**Show toast for long duration
         * @param context Context of view
         * @param massage Massage want to toast*/
        fun showToastLongDuration(context: Context, massage: String) {
            Toast.makeText(context, massage, Toast.LENGTH_LONG).show()
        }
    }
}