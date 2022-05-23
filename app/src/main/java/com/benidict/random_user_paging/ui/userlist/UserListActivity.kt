package com.benidict.random_user_paging.ui.userlist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.benidict.random_user_paging.R
import com.benidict.random_user_paging.databinding.ActivityUserListBinding
import com.benidict.random_user_paging.ui.ext.createItemSeparator
import com.benidict.random_user_paging.ui.menu.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding

    private var isMediator: Boolean = false

    private val viewModel: UserListViewModel by viewModels()

    private val adapter: UserPagingAdapter by lazy {
        UserPagingAdapter {
            Toast.makeText(this, "User ${it.first} ${it.last}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            isMediator =
                (it.getStringExtra(MainActivity.KEY_ENTRY_POINT) == MainActivity.MEDIATOR_SOURCE)
        }

        setUpToolbar()
        setUpView()
        setUpObserver()
    }

    private fun setUpToolbar() {
        binding.toolbarUser.apply {
            title = getString(
                if (isMediator)
                    R.string.title_mediator
                else
                    R.string.title_source
            )
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
        supportActionBar?.apply {
            setSupportActionBar(binding.toolbarUser)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setUpView() {
        val separator = this.createItemSeparator()
        binding.rvUser.apply {
            addItemDecoration(separator)
        }.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            val refreshState = if (isMediator)
                loadState.mediator?.refresh
            else
                loadState.source.refresh

            binding.rvUser.isVisible = refreshState is LoadState.NotLoading
            binding.progressBar.isVisible = refreshState is LoadState.Loading
            handleError(loadState)
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(this, "${it.error}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            if (isMediator)
                viewModel.userStateMediator.collectLatest {
                    adapter.submitData(it)
                }
            else
                viewModel.userState.collectLatest {
                    adapter.submitData(it)
                }
        }
    }
}