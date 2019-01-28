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
import com.sample.aacsample.data.db.ClippedArticle
import com.sample.aacsample.databinding.ViewNewsItemBinding
import com.sample.aacsample.ext.transitModal
import com.sample.aacsample.ui.activity.DetailActivity
import com.sample.aacsample.ui.fragment.DetailFragment
import com.sample.aacsample.ui.viewmodel.ClippedNewsViewModel
import com.squareup.picasso.Picasso

/**
 * Created by y_hisano on 2018/08/10.
 */
class ClippedNewsAdapter(private val fragment: Fragment, val viewModel: ClippedNewsViewModel) : RecyclerView.Adapter<ClippedNewsViewHolder>() {

    private val articles = mutableListOf<ClippedArticle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClippedNewsViewHolder {
        return ClippedNewsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(fragment.context), R.layout.view_news_item, parent, false))
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ClippedNewsViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.title.text = article.title
        holder.binding.body.text = article.description
        holder.binding.author.text = article.author
        holder.binding.date.text = article.publishedAt
        if (! TextUtils.isEmpty(article.urlToImage)) {
            holder.binding.image.visibility = View.VISIBLE
            Picasso.with(fragment.context).load(article.urlToImage).into(holder.binding.image)
        } else {
            holder.binding.image.visibility = View.GONE
        }
        holder.binding.root.setOnClickListener {
            if (!TextUtils.isEmpty(article.url)) {
//                fragment.activity?.transitPush(DetailFragment.newInstance(article.url, article.title))
                fragment.activity?.transitModal<DetailActivity>(DetailFragment.createModal(article.url, article.title))
            }
        }
        holder.binding.root.setOnLongClickListener {
            viewModel.unclipArticle(article)
            Toast.makeText(fragment.context, "Unclipped!", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }

    fun setArticles(articles: List<ClippedArticle>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }
}

class ClippedNewsViewHolder(val binding: ViewNewsItemBinding) : RecyclerView.ViewHolder(binding.root)
