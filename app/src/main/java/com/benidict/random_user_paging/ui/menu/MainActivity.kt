package com.benidict.random_user_paging.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.benidict.random_user_paging.databinding.ActivityMainBinding
import com.benidict.random_user_paging.ui.userlist.UserListActivity

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_ENTRY_POINT = "entry-point"
        const val MEDIATOR_SOURCE = "mediator-source"
        private const val REMOTE_SOURCE = "remote-soruce"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPagingSource.setOnClickListener {
            REMOTE_SOURCE.navigateToPassengerList()
        }

        binding.btnPagingMediator.setOnClickListener {
            MEDIATOR_SOURCE.navigateToPassengerList()
        }
    }

    private fun String.navigateToPassengerList() {
        val intent = Intent(this@MainActivity, UserListActivity::class.java)
        intent.putExtra(KEY_ENTRY_POINT, this)
        startActivity(intent)
    }
}