package com.example.flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.setHasFixedSize(true)
        getMovieData { movies: List<Movie> ->
            rvMovies.adapter = MovieAdapter(movies)
        }
    }
    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieAPIService.getInstance()!!.create(MovieAPIInterface::class.java)
        apiService.getList().enqueue(object: Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {

            }

        })
    }
}