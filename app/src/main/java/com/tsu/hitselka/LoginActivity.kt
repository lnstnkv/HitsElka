package com.tsu.hitselka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    account.idToken?.let {
                        firebaseLogin(it)
                    }
                }
            } catch (e: ApiException) {
                Log.d("Auth", "Api exception")
            }
        }
        auth = Firebase.auth

        binding.signInButton.setOnClickListener {
            login()
        }
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, gso)
    }

    private fun login() {
        val client = getClient()
        launcher.launch(client.signInIntent)
    }

    private fun firebaseLogin(idToken: String) {
        val credintials = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credintials).addOnCompleteListener {
            if (it.isSuccessful) {
                checkLoginState()
            } else {
                // неудачно
            }
        }
    }

    private fun checkLoginState() {
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}