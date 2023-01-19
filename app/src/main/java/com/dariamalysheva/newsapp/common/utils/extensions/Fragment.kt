package com.dariamalysheva.newsapp.common.utils.extensions

import androidx.fragment.app.Fragment
import com.dariamalysheva.newsapp.R

fun Fragment.navigateToFragment(fragment: Fragment, addToBackStack: Boolean) {
    if (addToBackStack) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    } else {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}