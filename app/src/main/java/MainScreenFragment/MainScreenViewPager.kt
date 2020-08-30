package MainScreenFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.todonotes.R

class MainScreenViewPager : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var myMainFragmentAdapter: MyMainFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen_view_pager)

        bindView()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        myMainFragmentAdapter = MyMainFragmentAdapter(supportFragmentManager)
        viewPager.adapter = myMainFragmentAdapter
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPagerID)
    }
}