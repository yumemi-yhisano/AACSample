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
import com.sample.aacsample.data.api.model.Article
import com.sample.aacsample.data.api.repository.Category
import com.sample.aacsample.databinding.FragmentMainBinding
import com.sample.aacsample.ext.TAG
import com.sample.aacsample.ui.adapter.NewsAdapter
import com.sample.aacsample.ui.viewmodel.NewsViewModel
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by y_hisano on 2018/07/24.
 */
class MainFragment : Fragment() {

    private var newsAdapter: NewsAdapter? = null
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentMainBinding

    companion object {
        private const val ARG_CATEGORY = "ARG_CATEGORY"

        fun newInstance(category: Category) =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_CATEGORY, category)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel { parametersOf(arguments!!.getSerializable(ARG_CATEGORY) as Category) }
        observableViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)

        binding.recyclerView.adapter = NewsAdapter(this, viewModel).also { newsAdapter = it }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.refreshLayout.setColorSchemeColors(0xFF800000.toInt(), 0xFF008000.toInt(), 0xFF000080.toInt(), 0xFF808000.toInt())
        binding.refreshLayout.setOnRefreshListener {
            Log.d(TAG, "onRefresh")
            viewModel.update()
            binding.refreshLayout.isRefreshing = false
        }

        binding.toolbar?.title?.text = ("NEWS")
        binding.toolbar?.leftArrow?.visibility = View.GONE

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.update()
    }

    private fun observableViewModel(viewModel: NewsViewModel) {
        viewModel.getObservableObject().observe(this, Observer<List<Article>> {
            if (it != null) {
                Log.d(TAG, "onChanged")
                newsAdapter?.setArticles(it)
            }
        })
    }
}
