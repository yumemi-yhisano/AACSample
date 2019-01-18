package com.sample.aacsample.ui.fragment

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.aacsample.R
import com.sample.aacsample.data.db.ClippedArticle
import com.sample.aacsample.databinding.FragmentNewsListBinding
import com.sample.aacsample.ext.TAG
import com.sample.aacsample.ui.adapter.ClippedNewsAdapter
import com.sample.aacsample.ui.viewmodel.ClippedNewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by y_hisano on 2018/07/24.
 */
class ClippedNewsListFragment : Fragment() {

    private var newsAdapter: ClippedNewsAdapter? = null
    private val viewModel: ClippedNewsViewModel by viewModel()
    private lateinit var binding: FragmentNewsListBinding

    companion object {
        fun newInstance() = ClippedNewsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observableViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentNewsListBinding>(inflater, R.layout.fragment_news_list, container, false)

        binding.recyclerView.adapter = ClippedNewsAdapter(this, viewModel).also { newsAdapter = it }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.refreshLayout.isEnabled = false

        return binding.root
    }

    private fun observableViewModel(viewModel: ClippedNewsViewModel) {
        viewModel.getObservableObject().observe(this, Observer<List<ClippedArticle>> {
            if (it != null) {
                Log.d(TAG, "onChanged")
                newsAdapter?.setArticles(it)
            }
        })
    }
}
