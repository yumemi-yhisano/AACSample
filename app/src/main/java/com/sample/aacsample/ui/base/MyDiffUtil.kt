package com.sample.aacsample.ui.base

import android.support.v7.util.DiffUtil

interface Diffable {
    // otherと同じIDを持つかどうか
    fun isTheSame(other: Diffable): Boolean = equals(other)
    // otherと完全一致するかどうか
    fun isContentsTheSame(other: Diffable): Boolean = equals(other)
}

private class Callback(
    val old: List<Diffable>,
    val new: List<Diffable>
) : DiffUtil.Callback() {

    override fun getOldListSize() = old.size
    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = old[oldItemPosition].isTheSame(new[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = old[oldItemPosition].isContentsTheSame(new[newItemPosition])
}

fun calculateDiff(
    old: List<Diffable>,
    new: List<Diffable>,
    detectMoves: Boolean = false
): DiffUtil.DiffResult = DiffUtil.calculateDiff(Callback(old, new), detectMoves)
