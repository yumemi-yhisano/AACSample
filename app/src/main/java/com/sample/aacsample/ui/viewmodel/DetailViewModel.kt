package com.sample.aacsample.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.os.Message
import android.view.View
import android.webkit.*

/**
 * Created by y_hisano on 2018/08/15.
 */
class DetailViewModel: ViewModel() {

    val progress = ObservableInt(100)
    val progressVisible = ObservableBoolean(false)

    fun configureVebView(view: WebView, noInternet: View) {
        view.settings.javaScriptEnabled = true
        view.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                progress.set(newProgress)
                progressVisible.set(newProgress < 100)
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
}