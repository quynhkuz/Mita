package com.globits.mita.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.LoadStateAdapter
import com.globits.mita.databinding.ItemLoadingStateBinding

class QltsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<QltsLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.visibility=
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.retryButton.visibility=if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            binding.errorMsg.visibility=if (loadState is LoadState.Error) View.VISIBLE else View.GONE

            binding.retryButton.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(
        ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}