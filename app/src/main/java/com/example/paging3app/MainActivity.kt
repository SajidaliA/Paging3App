package com.example.paging3app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3app.adapter.LoaderAdapter
import com.example.paging3app.adapter.UserAdapter
import com.example.paging3app.viewmodel.UserViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewmodel
    private lateinit var userAdapter: UserAdapter

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel = ViewModelProvider(this)[UserViewmodel::class.java]
        recyclerView = findViewById(R.id.rv_users)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userAdapter = UserAdapter()
        recyclerView.adapter = userAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        userViewModel.userList.observe(this){
            userAdapter.submitData(lifecycle, it)
        }
    }
}