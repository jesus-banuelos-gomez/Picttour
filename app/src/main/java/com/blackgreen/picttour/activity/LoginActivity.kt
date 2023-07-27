package com.blackgreen.picttour.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blackgreen.picttour.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {


    private var auth: FirebaseAuth? = null
    private  var firebaseUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth!!.currentUser

        //check if user login then navigate to use screen
        if (firebaseUser != null){
            val intent = Intent(this@LoginActivity,UsersActivity::class.java)
            startActivity(intent)
            finish()

        }


        val etEmail:EditText = findViewById(R.id.etEmail)
        val etPassword:EditText = findViewById(R.id.etPassword)
        val btnSignUp:Button = findViewById(R.id.btnSignUp)
        val btnLogin:Button = findViewById(R.id.btnLogin)

        if (firebaseUser != null) {
            val intent = Intent(
                this@LoginActivity,
                UsersActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        btnLogin.setOnClickListener{

            val vEmail = etEmail.text.toString()
            val vPassword = etPassword.text.toString()

            if(TextUtils.isEmpty(vEmail) && TextUtils.isEmpty(vPassword)){
                Toast.makeText(applicationContext,"email and password are required",Toast.LENGTH_SHORT).show()
            }else{
                auth!!.signInWithEmailAndPassword(vEmail,vPassword)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            etEmail.setText("")
                            etPassword.setText("")
                            val intent = Intent(this@LoginActivity,UsersActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(applicationContext,"email or password invalid",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        btnSignUp.setOnClickListener{
            val intent = Intent(this@LoginActivity,SingUpActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}
