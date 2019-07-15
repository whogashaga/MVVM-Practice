package com.example.mvvmassignment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.mvvmassignment.R
import com.example.mvvmassignment.data.Results
import com.google.android.material.snackbar.Snackbar

private const val ARG_TITLE = "title"
private const val ARG_OBJECT = "arg_object"

class DetailFragment : Fragment() {

    private var mTitle: String = ""
    private var mResults: Results? = null

    private lateinit var mImagePicture: ImageView
    private lateinit var mTextDescription: TextView
    private lateinit var mTextMemo: TextView
    private lateinit var mTextCategory: TextView
    private lateinit var mTextOpenWeb: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString(ARG_TITLE)?.let {
            mTitle = it
        }
        arguments?.getSerializable(ARG_OBJECT).let {
            mResults = it as? Results
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        mImagePicture = root.findViewById(R.id.image_picture_detail)
        mTextDescription = root.findViewById(R.id.text_detail_content)
        mTextMemo = root.findViewById(R.id.text_detail_memo)
        mTextCategory = root.findViewById(R.id.text_detail_category)
        mTextOpenWeb = root.findViewById(R.id.text_open_web)

        Glide.with(root).load(mResults?.E_Pic_URL).into(mImagePicture)
        mTextDescription.text = mResults?.E_Info
        mTextMemo.text = filterString(mResults)
        mTextCategory.text = mResults?.E_Category

        mTextOpenWeb.setOnClickListener { v ->
            Snackbar.make(v, "目前瀏覽人數眾多 請耐心等候畫面", Snackbar.LENGTH_LONG).setAction("Action", null)
                .show()

            val action = DetailFragmentDirections.actionDetailFragmentToWebFragment()
            action.webUrl = mResults?.E_URL ?: ""
            action.argTitle = mTitle
            Navigation.findNavController(v).navigate(action)
        }
        return root
    }

    private fun filterString(results: Results?): String {
        return if ("" == results?.E_Memo) "無休館資訊" else results?.E_Memo.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            results: Results
        ) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putSerializable(ARG_OBJECT, results)
                }
            }
    }

}