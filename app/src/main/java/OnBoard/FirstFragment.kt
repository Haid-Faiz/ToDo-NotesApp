package OnBoard

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todonotes.R

class FirstFragment : Fragment() {

    lateinit var nextButton : TextView
    lateinit var onNextClick: OnNextClick

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            nextButton = view.findViewById(R.id.nextButton1ID)
            nextButton.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    onNextClick.onNextButton1Click()
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }

    interface OnNextClick{
        fun onNextButton1Click()
    }
}
