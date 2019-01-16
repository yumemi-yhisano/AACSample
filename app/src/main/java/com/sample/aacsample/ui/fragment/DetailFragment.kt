package com.sample.aacsample.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.databinding.FragmentDetailBinding
import com.sample.aacsample.ui.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by y_hisano on 2018/08/15.
 */
class DetailFragment: Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    companion object {
        private const val ARG_URL = "ARG_URL"
        private const val ARG_TITLE = "ARG_TITLE"

        fun newInstance(url: String, title: String) =
                DetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_URL, url)
                        putString(ARG_TITLE, title)
                    }
                }

        fun newInstance(bundle: Bundle) =
                DetailFragment().apply {
                    arguments = bundle
                }

        fun createModal(url: String, title: String) =
                Bundle().apply {
                    putString(ARG_URL, url)
                    putString(ARG_TITLE, title)
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbar?.title?.text = arguments!!.getString(ARG_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater, R.layout.fragment_detail, container, false)
        binding.model = viewModel
        initToolbar()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webview.saveState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.configureVebView(binding.webview, binding.noInternet!!.root)

        if (savedInstanceState == null) {
            binding.webview.loadUrl(arguments!!.getString(ARG_URL))
        } else {
            binding.webview.restoreState(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.webview.onResume()
        updateToolbar()
    }

    override fun onPause() {
        binding.webview.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        (binding.webview.parent as? ViewGroup)?.removeView(binding.webview)
        binding.webview.removeAllViews()
        binding.webview.destroy()
        super.onDestroy()
    }

    private fun initToolbar() {
        binding.toolbar?.leftArrow?.setOnClickListener {
            val fragmentPushed = getActivityFragmentManager().backStackEntryCount > 0
            if (fragmentPushed) {
                getActivityFragmentManager().popBackStack()
            }
        }
        binding.toolbar?.menu?.visibility = View.GONE
    }

    private fun updateToolbar() {
        val fragmentPushed = getActivityFragmentManager().backStackEntryCount > 0
        binding.toolbar?.leftArrow?.visibility = if (fragmentPushed) View.VISIBLE else View.GONE
    }

    private fun getActivityFragmentManager() = activity!!.supportFragmentManager!!
}