package OnBoard

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todonotes.R
import kotlinx.android.synthetic.*

class ThirdFragment : Fragment() {

    private lateinit var finishButton: TextView
    private lateinit var backButton3: TextView
    lateinit var onOptionClick3: OnOptionClick3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView(view)
        clickListner()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick3 = context as OnOptionClick3
    }

    private fun clickListner() {
        finishButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick3.onFinishButton3()
            }

        })
        backButton3.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick3.onBackButton3()
            }

        })
    }

    interface OnOptionClick3{
        fun onFinishButton3()
        fun onBackButton3()
    }
    private fun bindView(view: View) {
        finishButton = view.findViewById(R.id.finishButtonID)
        backButton3 = view.findViewById(R.id.backButton3ID)
    }
}

