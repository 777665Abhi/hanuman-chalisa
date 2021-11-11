package com.hindgyan.hanumanchalisa.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class IntentUtil {
    companion object {
        /**Start activity with bundle data
         *
         * @param source (Activity) Source of transaction
         * @param destination (Activity) Destination of transaction
         * @param bundle (Bundle) Bundle to transfer data from source to destination
         * @param isFinish (Boolean) to check to finish source
         * */
        fun startActivityWithLoad(
            source: Activity,
            destination: Class<*>,
            bundle: Bundle,
            isFinish: Boolean
        ) {
            val intent = Intent(source, destination)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            source.startActivity(intent)
            if (isFinish!!) {
                source.finish()
            }
        }

        /**Simply start activity without bundle data
         *
         * @param source (Activity) Source of transaction
         * @param destination (Activity) Destination of transaction
         * @param isFinish (Boolean) to check to finish source
         * */
        fun startActivity(source: Activity, destination: Class<*>, isFinish: Boolean) {
            val intent = Intent(source, destination)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            source.startActivity(intent)
            if (isFinish!!) {
                source.finish()
            }
        }
    }
}