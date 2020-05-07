package com.alexk.nadlansales.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*

/**
 * Created by alexkorolov on 28/04/2020.
 */
class SplashViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userAuth: MutableLiveData<Boolean> = MutableLiveData()
    val userAuthLiveData: LiveData<Boolean>
        get() = userAuth

    fun checkIfUserIsAuthenticatedInFirebase() {
        if (firebaseAuth.currentUser == null) {
            userAuth.setValue(false)
        } else {
            userAuth.setValue(true)
        }
    }

    fun firebaseAuthWithFacebook(loginResult: LoginResult) {
        val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
        fireBaseAuthenticate(credential)
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        fireBaseAuthenticate(credential)
    }

    private fun fireBaseAuthenticate(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                userAuth.value = task.isSuccessful
                if (task.isSuccessful) {
                    Log.d("TAG", "onComplete: successful")
                } else {
                    Log.d("TAG", "onComplete: fail")
                }
            }

    }
}