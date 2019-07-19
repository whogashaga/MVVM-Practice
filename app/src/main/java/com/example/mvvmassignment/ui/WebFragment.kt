package com.example.mvvmassignment.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

import com.example.mvvmassignment.R
import com.example.mvvmassignment.constant.Constants

private const val ARG_URL = "web_url"

class WebFragment : Fragment() {

    private var url: String? = null
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_web, container, false)
        progressBar = root.findViewById(R.id.progressBar2)
        webView = root.findViewById(R.id.web_view)
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                }
            }
        }
        Log.d(Constants.TAG, "url = $url")
        webView.loadUrl(url)

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, param)
                }
            }
    }
}
