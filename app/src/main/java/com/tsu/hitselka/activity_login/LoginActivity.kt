package com.tsu.hitselka.activity_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_video.VideoActivity
import com.tsu.hitselka.databinding.ActivityLoginBinding
import com.tsu.hitselka.model.SharedPrefs
import com.tsu.hitselka.model.setFullscreen

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        SharedPrefs.init(this)
        checkLoginState()
        setFullscreen()

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                    Log.d("Auth", e.toString())
                }
            }

        binding.signInButton.setOnClickListener {
            login()
        }
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // ?????? ???????????????????? ??????, ?????? ????????????????
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, gso)
    }

    private fun login() {
        val client = getClient()
        launcher.launch(client.signInIntent)
    }

    private fun firebaseLogin(idToken: String) {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credentials).addOnCompleteListener {
            if (it.isSuccessful) {
                saveUID()
                checkLoginState()
            } else {
                // ????????????????
            }
        }
    }

    private fun checkLoginState() {
        if (auth.currentUser != null) {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        } else {
            binding.signInButton.visibility = View.VISIBLE
        }
    }

    private fun saveUID() {
        val user = auth.currentUser
        if (user != null) {
            SharedPrefs.saveUID(user.uid)
        }
    }
}