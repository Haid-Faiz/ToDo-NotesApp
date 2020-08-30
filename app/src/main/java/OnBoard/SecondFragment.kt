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

class SecondFragment : Fragment() {

    lateinit var nextButton: TextView
    lateinit var backButton: TextView
    lateinit var onOptionClick2: OnOptionClick2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView(view)
        clickListner()
    }

    override fun onAttach(context: Context) {             // when activity opens this fragment, onAttach() will gets called
        super.onAttach(context)
            onOptionClick2 = context as OnOptionClick2    // we have initialised the interface here
    }

    private fun clickListner() {
        nextButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick2.onNextButton2Click()
            }
        })

        backButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick2.onBackButton2Click()
            }

        })
    }

    interface OnOptionClick2{
        fun onNextButton2Click()
        fun onBackButton2Click()
    }

    private fun bindView(view: View) {
        nextButton = view.findViewById(R.id.nextButton2ID)
        backButton = view.findViewById(R.id.backButton2ID)
    }
}
