package com.example.uf1_act19_entregableretrofit_guillemdehon

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private val posts = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputNumber = findViewById<EditText>(R.id.inputNumber)
        val fetchButton = findViewById<Button>(R.id.fetchButton)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = PostAdapter(posts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchButton.setOnClickListener {
            val id = inputNumber.text.toString().toIntOrNull()
            if (id != null) {
                fetchPostById(id)
            } else {
                Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchPostById(id: Int) {
        RetrofitClient.instance.getPostById(id).enqueue(object : Callback<Post> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        posts.clear()
                        posts.add(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Post not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


