package com.benidict.random_user_paging.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.benidict.random_user_paging.R
import com.benidict.random_user_paging.databinding.ItemUserBinding
import com.benidict.random_user_paging.domain.model.User
import com.benidict.random_user_paging.ui.ext.address
import com.benidict.random_user_paging.ui.ext.fullName
import com.benidict.random_user_paging.ui.ext.loadImageUrl

class UserPagingAdapter(
    private val clickItem: (User) -> Unit
) : PagingDataAdapter<User, UserPagingAdapter.UserViewHolder>(
    Comparator
) {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_user, parent, false
            )

        return UserViewHolder(
            clickItem,
            ItemUserBinding.bind(view)
        )
    }

    class UserViewHolder(
        val clickItem: (User) -> Unit,
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvEmail.text = user.email
            binding.tvName.text = user.fullName()
            binding.tvAddress.text = user.address()
            binding.ivProfilePicture.loadImageUrl(user.picture.orEmpty())
            binding.root.setOnClickListener {
                clickItem(user)
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<User>() {
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }
    }
}