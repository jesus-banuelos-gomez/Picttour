package com.blackgreen.picttour.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackgreen.picttour.R
import com.blackgreen.picttour.adapter.UserAdapter
import com.blackgreen.picttour.model.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class UsersActivity : AppCompatActivity() {


    var userList = ArrayList<User>()
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val userRecyclerView: RecyclerView = findViewById(R.id.userRecyclerView)
        val imgBack : ImageView = findViewById(R.id.imgBack)
        val imgProfile :CircleImageView  = findViewById(R.id.imgProfile)
        val btnSalida : TextView = findViewById(R.id.btnSalida)

        userRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        auth = FirebaseAuth.getInstance()


            btnSalida.setOnClickListener{
                auth.signOut()
                val intent = Intent(this@UsersActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }



        imgBack.setOnClickListener{
            onBackPressed()
        }
        imgProfile.setOnClickListener {
            val intent = Intent(this@UsersActivity,ProfileActivity::class.java)
            startActivity(intent)

        }
        getUserList(userRecyclerView,imgProfile)
    }

    fun getUserList(userRecyclerView: RecyclerView, imgProfile: CircleImageView) {

        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                val currentUser = snapshot.getValue(User::class.java)
                if (currentUser!!.userImage ==""){
                    imgProfile.setImageResource(R.drawable.perfil_img)
                }else{
                    Glide.with(this@UsersActivity).load(currentUser.userImage).into(imgProfile)
                }

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    if (!user!!.userId.equals(firebase.uid)) {
                        userList.add(user)
                    }
                }

                val userAdapter = UserAdapter(this@UsersActivity, userList)
                userRecyclerView.adapter = userAdapter
            }

        })
    }

}