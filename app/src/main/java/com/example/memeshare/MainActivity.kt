package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var currentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
        nextMeme.setOnClickListener { loadMeme() }
        shareMeme.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type  = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"$currentImageUrl")
            startActivity(Intent.createChooser(intent,"share with: "))
        }
    }
    private fun loadMeme(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).into(imageMeme)
            },
            {
                Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show()
            })
            queue.add(jsonObjectRequest)

    }
}
