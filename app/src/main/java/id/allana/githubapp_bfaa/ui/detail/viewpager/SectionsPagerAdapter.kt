package id.allana.githubapp_bfaa.ui.detail.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.allana.githubapp_bfaa.ui.detail.viewpager.listfollower.ListFollowerUserFragment
import id.allana.githubapp_bfaa.ui.detail.viewpager.listfollowing.ListFollowingUserFragment

class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    var dataBundle: Bundle = Bundle()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = ListFollowingUserFragment()
                fragment.arguments = dataBundle
            }
            1 -> {
                fragment = ListFollowerUserFragment()
                fragment.arguments = dataBundle
            }
        }
        return fragment as Fragment
    }


}