package OnBoard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.todonotes.LoginActivity
import com.example.todonotes.R


class OnBoardActivity : AppCompatActivity(), FirstFragment.OnNextClick, SecondFragment.OnOptionClick2, ThirdFragment.OnOptionClick3 {

    lateinit var viewPager: ViewPager
    lateinit var myFragmentAdapter: MyFragmentAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        //supportActionBar?.hide()
        bindView()
        setAdapter()
        setUpSharedPref()
    }

    private fun setUpSharedPref() {
        // if Mode won't be private, then other app can able to see this sharedPref file..
        sharedPreferences = getSharedPreferences("OnBoardedValue", Context.MODE_PRIVATE)
    }

    private fun setAdapter() {
        myFragmentAdapter = MyFragmentAdapter(supportFragmentManager)      // In Java: getSupportManager();
        viewPager.adapter = myFragmentAdapter                              // In Java: viewPager.setAdapter(myFragmentAdapter);
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPagerID)
    }

    override fun onNextButton1Click() {
        viewPager.currentItem = 1
    }

    override fun onNextButton2Click() {
        viewPager.currentItem = 2
    }

    override fun onBackButton2Click() {
        viewPager.currentItem = 0
    }

    override fun onFinishButton3() {

        var editor = sharedPreferences.edit()
        editor.putBoolean("isOnBoarded", true)
        editor.apply()

        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackButton3() {
        viewPager.currentItem = 1
    }
}