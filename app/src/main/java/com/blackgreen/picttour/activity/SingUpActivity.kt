package com.blackgreen.picttour.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.blackgreen.picttour.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class SingUpActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUp:Button = findViewById(R.id.btnSignUp)
        val btnLogin:Button = findViewById(R.id.btnLogin)
        val userName: EditText = findViewById(R.id.etName)
        val userEmail:EditText = findViewById(R.id.etEmail)
        val userPassword:EditText = findViewById(R.id.etPassword)
        val userConfirmPassword:EditText = findViewById(R.id.etConfirmPassword)

        auth = FirebaseAuth.getInstance()



        btnSignUp.setOnClickListener{
            val vUserName = userName.text.toString()
            val vUserEmail = userEmail.text.toString()
            val vUserPassword = userPassword.text.toString()
            val vUserConfirmPassword = userConfirmPassword.text.toString()


            /*hace falta acomodar los if para que cumplan la funcionalidad que poseen*/
            if(TextUtils.isEmpty(vUserName)){
                Toast.makeText(applicationContext,"userName is required",Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(vUserEmail)){
                Toast.makeText(applicationContext,"userEmail is required",Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(vUserPassword)){
                Toast.makeText(applicationContext,"Password is required",Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(vUserConfirmPassword)){
                Toast.makeText(applicationContext,"Confirm Password is  required",Toast.LENGTH_SHORT).show()
            }

            if(!vUserPassword.equals(vUserConfirmPassword)){
                Toast.makeText(applicationContext,"Confirm Password is required",Toast.LENGTH_SHORT).show()
            }
            registerUser(vUserName,vUserEmail,vUserPassword)
            userName.setText("")
            userEmail.setText("")
            userPassword.setText("")
            userConfirmPassword.setText("")
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this@SingUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun registerUser(userName:String,email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ it ->
                if (it.isSuccessful){
                    val user:FirebaseUser? = auth.currentUser
                    val userId:String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap:HashMap<String,String> = HashMap()
                    hashMap["userId"] = userId
                    hashMap["userName"] = userName
                    hashMap["profileImage"] = ""

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if (it.isSuccessful){
                            //open home activity
                            val intent = Intent(this@SingUpActivity, UsersActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
    }
}