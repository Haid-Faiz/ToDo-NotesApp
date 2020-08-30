package MainScreenFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyMainFragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> NoItemAddedFrag()
            1 -> OnItemAddedFrag()
            else -> NoItemAddedFrag()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}