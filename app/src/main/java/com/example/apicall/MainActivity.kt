package com.example.apicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.myRecycleview)
        val ServiceGenrator = ServiceGenrator.buildService(ApiService::class.java)
        val call = ServiceGenrator.getPosts()

        call.enqueue(object : retrofit2.Callback<MutableList<PostModel>> {
            override fun onResponse(
                call: Call<MutableList<PostModel>>,
                response: Response<MutableList<PostModel>>
            ) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PostAdapter(response.body()!!)
                    }
                    Log.e("Success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("error", t.message.toString())

            }

        })


    }
}