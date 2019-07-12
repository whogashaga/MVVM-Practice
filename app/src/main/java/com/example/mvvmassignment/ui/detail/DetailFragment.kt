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

private const val ARG_PIC_URL = "arg_pic_url"
private const val ARG_DESCRIPTION = "arg_description"
private const val ARG_MEMO = "arg_memo"
private const val ARG_CATEGORY = "arg_category"
private const val ARG_WEB_URL = "arg_web_url"

class DetailFragment : Fragment() {

    private var mPicUrl: String? = null
    private var mDescription: String? = null
    private var mMemo: String? = null
    private var mCategory: String? = null
    private var mWebUrl: String = ""

    private lateinit var mImagePicture: ImageView
    private lateinit var mTextDescription: TextView
    private lateinit var mTextMemo: TextView
    private lateinit var mTextCategory: TextView
    private lateinit var mTextOpenWeb: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPicUrl = it.getString(ARG_PIC_URL)
            mDescription = it.getString(ARG_DESCRIPTION)
            mMemo = it.getString(ARG_MEMO)
            mCategory = it.getString(ARG_CATEGORY)
        }
        arguments?.getString(ARG_WEB_URL)?.let {
            mWebUrl = it
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

        Glide.with(root).load(mPicUrl).into(mImagePicture)
        mTextDescription.text = mDescription
        mTextMemo.text = mMemo
        mTextCategory.text = mCategory

        mTextOpenWeb.setOnClickListener { v ->
            val action = DetailFragmentDirections.actionDetailFragmentToWebFragment()
            action.webUrl = mWebUrl
            Navigation.findNavController(v).navigate(action)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(
            picUrl: String,
            description: String,
            memo: String,
            category: String,
            webUrl: String
        ) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PIC_URL, picUrl)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_MEMO, memo)
                    putString(ARG_CATEGORY, category)
                    putString(ARG_WEB_URL, webUrl)
                }
            }
    }

}