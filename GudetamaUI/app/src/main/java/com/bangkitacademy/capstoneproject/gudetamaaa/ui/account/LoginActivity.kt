package com.bangkitacademy.capstoneproject.gudetamaaa.ui.account

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bangkitacademy.capstoneproject.gudetamaaa.R
import com.bangkitacademy.capstoneproject.gudetamaaa.databinding.ActivityLoginBinding
import com.bangkitacademy.capstoneproject.gudetamaaa.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var editUsername: EditText
    lateinit var editPassword: EditText
    lateinit var btnLogin: Button
    lateinit var textRegister: TextView
    lateinit var linkRegister: TextView

    var firebaseAuth =  FirebaseAuth.getInstance()

    lateinit var progressDialog: ProgressDialog

    private lateinit var binding: ActivityLoginBinding

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editUsername = binding.usernameField
        editPassword = binding.passwordField
        btnLogin = binding.loginBtn
        textRegister = binding.textRegister
        linkRegister = binding.textLinkRegister

        linkRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            if (editUsername.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                proccessLogin()
            }else{
                Toast.makeText(this, "Silakan isi Username dan Paassword", Toast.LENGTH_SHORT).show()
            }
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silakan tunggu...")
    }

    private fun proccessLogin(){
        val username = editUsername.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener{
                progressDialog.dismiss()
            }
    }
}