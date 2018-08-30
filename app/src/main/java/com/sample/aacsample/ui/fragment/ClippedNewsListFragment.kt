package com.sample.aacsample.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.data.db.ClippedArticle
import com.sample.aacsample.databinding.FragmentNewsListBinding
import com.sample.aacsample.ui.adapter.ClippedNewsAdapter
import com.sample.aacsample.ui.viewmodel.ClippedNewsViewModel

/**
 * Created by y_hisano on 2018/07/24.
 */
class ClippedNewsListFragment : BaseFragment() {

    private var newsAdapter: ClippedNewsAdapter? = null
    private lateinit var viewModel: ClippedNewsViewModel
    private lateinit var binding: FragmentNewsListBinding

    companion object {
        fun newInstance() = ClippedNewsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ClippedNewsViewModel.Factory(activity!!.application)
        viewModel = ViewModelProviders.of(this, factory).get(ClippedNewsViewModel::class.java)
        observableViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentNewsListBinding>(inflater, R.layout.fragment_news_list, container, false)

        binding.recyclerView.adapter = ClippedNewsAdapter(this, viewModel).also { newsAdapter = it }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

//        binding.refreshLayout.setColorSchemeColors(0xFF800000.toInt(), 0xFF008000.toInt(), 0xFF000080.toInt(), 0xFF808000.toInt())
//        binding.refreshLayout.setOnRefreshListener {
//            Log.d(getFragmentTag(), "onRefresh")
//            binding.refreshLayout.isRefreshing = false
//        }
        binding.refreshLayout.isEnabled = false

        return binding.root
    }

    private fun observableViewModel(viewModel: ClippedNewsViewModel) {
        viewModel.getObservableObject().observe(this, Observer<List<ClippedArticle>> {
            if (it != null) {
                Log.d(getFragmentTag(), "onChanged")
                newsAdapter?.setArticles(it)
            }
        })
    }
}