package com.sample.aacsample.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.graphics.Bitmap
import android.os.Message
import android.view.View
import android.webkit.*
import android.widget.ProgressBar

/**
 * Created by y_hisano on 2018/08/15.
 */
class DetailViewModel(application: Application): AndroidViewModel(application) {

    fun configureVebView(view: WebView, progressBar: ProgressBar, noInternet: View) {
        view.settings.javaScriptEnabled = true
        view.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                progressBar.progress = newProgress
                progressBar.visibility = if (newProgress < 100) View.VISIBLE else View.INVISIBLE
            }
        }

        view.webViewClient = object : WebViewClient() {
            private var mError: Boolean = false

            override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
                resend?.sendToTarget()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                view?.visibility = if (mError) View.INVISIBLE else View.VISIBLE
                noInternet.visibility = if (mError) View.VISIBLE else View.INVISIBLE
                mError = false
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                mError = true
                super.onReceivedError(view, request, error)
            }
        }
    }

    class Factory(private val application: Application): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(application) as T
        }
    }
}