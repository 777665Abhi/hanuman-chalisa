package com.hindgyan.hanumanchalisa.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FragmentUtil {

    companion object {
        /**Open fragment
         * @param supportFragmentManager SupportFragmentManager
         * @param containerId Container id in which want to open fragment
         * @param fragment Instance of fragment want to open*/
        fun openFragment(
            supportFragmentManager: FragmentManager,
            containerId: Int,
            fragment: Fragment
        ) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.add(containerId, fragment)
            ft.addToBackStack(null)
            ft.commit()
        }

        /**Open fragment with stack record
         * @param supportFragmentManager SupportFragmentManager
         * @param containerId Container id in which want to open fragment
         * @param fragment Instance of fragment want to open*/
        fun openFragmentWithStack(
            supportFragmentManager: FragmentManager,
            containerId: Int,
            fragment: Fragment
        ) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.add(containerId, fragment)
            ft.commit()
        }

        /**Show dialog fragment
         * @param supportFragmentManager SupportFragmentManager
         * @param fragment Instance of fragment want to open
         * @param tag tag for fragment*/
        fun showDialog(
        supportFragmentManager: FragmentManager,
        fragment: DialogFragment,
        tag:String
        ) {
            fragment.show(supportFragmentManager,tag)
        }
    }
}