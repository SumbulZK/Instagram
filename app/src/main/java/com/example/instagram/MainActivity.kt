package com.example.instagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.instagram.fragments.ComposeFragment
import com.example.instagram.fragments.FeedFragment
import com.example.instagram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager : FragmentManager = supportFragmentManager
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->
            lateinit var fragmentToShow: Fragment
                when (item.itemId){
                    R.id.action_home ->{
                        fragmentToShow = FeedFragment()
                        Log.i(TAG, "Home")
                    }
                    R.id.action_compose -> fragmentToShow = ComposeFragment()
                    R.id.action_profile ->{
                        Log.i(TAG, "Profile")
                        fragmentToShow = ProfileFragment()
                    }
                }
            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.framelayout, fragmentToShow).commit()
            }

            true

        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home


        //queryPosts()

    }



    fun logout(){

        goLoginActivity()
    }

    private fun goLoginActivity(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun queryPosts(){

        val query: ParseQuery<post> = ParseQuery.getQuery(post::class.java)
        query.include(post.KEY_USER)
        query.findInBackground(object : FindCallback<post>{
            override fun done(posts: MutableList<post>?, e: ParseException?) {
                if (e!= null)
                {
                    Log.e(TAG, "error fetching posts")
                }
                else{
                    if (posts!= null){
                        for (post in posts){
                            Log.i(TAG, "Post: "+ post.getDescription() + " username: " + post.getUser()?.username)
                        }
                    }
                }
            }
        })
    }



    companion object{
        const val TAG = "Main Activity"
        val APP_TAG = "MyCustomApp"


    }
}