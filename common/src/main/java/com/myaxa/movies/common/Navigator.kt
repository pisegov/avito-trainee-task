package com.myaxa.movies.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import jakarta.inject.Inject

class Navigator @Inject constructor(private val containerID: Int) {
    fun navigateToFragment(
        fragmentManager: FragmentManager,
        destinationFragment: Fragment
    ) {
        fragmentManager.commit {
            val tag = destinationFragment.javaClass.simpleName
            replace(containerID, destinationFragment, tag)
            addToBackStack(tag)
        }
    }
}