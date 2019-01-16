package com.sample.aacsample.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sample.aacsample.R
import com.sample.aacsample.data.api.model.Article
import com.sample.aacsample.databinding.ViewNewsItemBinding
import com.sample.aacsample.databinding.ViewNoItemBinding
import com.sample.aacsample.ext.modalFragment
import com.sample.aacsample.ui.activity.DetailActivity
import com.sample.aacsample.ui.fragment.DetailFragment
import com.sample.aacsample.ui.viewmodel.NewsViewModel
import com.squareup.picasso.Picasso

/**
 * Created by y_hisano on 2018/08/10.
 */
class NewsAdapter(private val fragment: Fragment, val viewModel: NewsViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val articles = mutableListOf<Article>()

    companion object {
        private const val VIEW_TYPE_NO_INTERNET = 0
        private const val VIEW_TYPE_ARTICLE = 1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_NO_INTERNET) {
            return NewsNoItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(fragment.context), R.layout.view_no_item, parent, false))
        }
        return NewsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(fragment.context), R.layout.view_news_item, parent, false))
    }
    override fun getItemCount() = if (isNoItem()) 1 else articles.size

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && isNoItem()) return VIEW_TYPE_NO_INTERNET
        return VIEW_TYPE_ARTICLE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            val article = articles[position]
            holder.binding.title.text = article.title
            holder.binding.body.text = article.description
            holder.binding.author.text = article.author
            holder.binding.date.text = article.publishedAt
            if (!TextUtils.isEmpty(article.urlToImage)) {
                holder.binding.image.visibility = View.VISIBLE
                Picasso.with(fragment.context).load(article.urlToImage).into(holder.binding.image)
            } else {
                holder.binding.image.visibility = View.GONE
            }
            holder.binding.root.setOnClickListener {
                if (!TextUtils.isEmpty(article.url)) {
//                fragment.activity?.pushFragment(DetailFragment.newInstance(article.url, article.title))
                    fragment.activity?.modalFragment<DetailActivity>(DetailFragment.createModal(article.url, article.title))
                }
            }
            holder.binding.root.setOnLongClickListener {
                viewModel.clipArticle(article)
                Toast.makeText(fragment.context, "Clipped!", Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener true
            }
        }
    }

    private fun isNoItem() = articles.size == 0

    fun setArticles(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(val binding: ViewNewsItemBinding) : RecyclerView.ViewHolder(binding.root)

class NewsNoItemViewHolder(val binding: ViewNoItemBinding) : RecyclerView.ViewHolder(binding.root)