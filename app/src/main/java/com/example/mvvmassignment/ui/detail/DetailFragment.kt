package com.example.mvvmassignment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.mvvmassignment.NavigateCallback
import com.example.mvvmassignment.R
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.ui.main.MainViewModel
import com.example.mvvmassignment.utils.InjectUtils
import com.google.android.material.snackbar.Snackbar

private const val ARG_TITLE = "title"
private const val ARG_OBJECT = "arg_object"
private const val ARG_POSITION = "arg_position"

class DetailFragment : Fragment() {

    private var mTitle: String = ""
    private var mAnimalResults: AnimalResults? = null

    private lateinit var mImagePicture: ImageView
    private lateinit var mTextDescription: TextView
    private lateinit var mTextMemo: TextView
    private lateinit var mTextCategory: TextView
    private lateinit var mTextOpenWeb: TextView
    private val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString(ARG_TITLE)?.let {
            mTitle = it
        }
        arguments?.getSerializable(ARG_OBJECT).let {
            mAnimalResults = it as? AnimalResults
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

        Glide.with(root).load(mAnimalResults?.E_Pic_URL).into(mImagePicture)
        mTextDescription.text = mAnimalResults?.E_Info
        mTextMemo.text = viewModel.filterString(mAnimalResults)
        mTextCategory.text = mAnimalResults?.E_Category

        mTextOpenWeb.setOnClickListener { view ->
            viewModel.onClickOpenWebView(mAnimalResults, object : NavigateCallback {
                override fun onNavigateAction(action: NavDirections) {
                    Snackbar.make(
                        view,
                        "歡迎蒞臨 " + mAnimalResults?.E_Name.toString(),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null)
                        .show()
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            animalResults: AnimalResults,
            position: Int
        ) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putSerializable(ARG_OBJECT, animalResults)
                    putInt(ARG_POSITION, position)
                }
            }
    }

}