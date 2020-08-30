package OnBoard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyFragmentAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {   // we have to pass fragment manager here

    override fun getItem(position: Int): Fragment {
        return when(position){
                    0 -> FirstFragment()
                    1 -> SecondFragment()
                    2 -> ThirdFragment()
                    else -> ThirdFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}