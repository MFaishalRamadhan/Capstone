package com.bangkitacademy.capstoneproject.loviso.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bangkitacademy.capstoneproject.loviso.databinding.ActivityRegisterBinding
import com.bangkitacademy.capstoneproject.loviso.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editConfirmation: EditText
    lateinit var btnRegister: Button
    lateinit var textLogin: TextView
    lateinit var linkLogin: TextView

    var firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        supportActionBar?.hide()

        editName = registerBinding.usernameField
        editEmail = registerBinding.emailField
        editPassword = registerBinding.passwordField
        editConfirmation = registerBinding.passwordConfirmField
        btnRegister = registerBinding.registerBtn
        textLogin = registerBinding.textLogin
        linkLogin = registerBinding.textLinkLogin

        btnRegister.setOnClickListener {
            if (editName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                if(editPassword.text.toString() == editConfirmation.text.toString()){
                    processRegister()
                }
            }
        }

        linkLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun processRegister(){
        val username = editName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener{ task ->
                val userUpdateProfile = userProfileChangeRequest {
                    displayName = username
                }
                val user = task.user

                user?.updateProfile(userUpdateProfile)?.addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }?.addOnFailureListener { error->
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { error->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}