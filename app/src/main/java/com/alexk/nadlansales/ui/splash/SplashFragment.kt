package com.alexk.nadlansales.ui.splash

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alexk.nadlansales.R
import com.alexk.nadlansales.ui.BaseFragment
import com.alexk.nadlansales.ui.activities.MainActivity
import com.alexk.nadlansales.utils.AppConsts
import com.alexk.nadlansales.utils.shortToast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.ext.android.inject
import java.util.*


class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val splashViewModel: SplashViewModel by inject()
    private var callbackManager = CallbackManager.Factory.create()
    private var socialLayoutAnimationDone = false


    private fun checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticatedInFirebase()
        splashViewModel.userAuthLiveData.observe(viewLifecycleOwner, Observer { userAuthorized ->
            if (userAuthorized) {
                startMainActivity()
            } else {
                showUserAuth()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateLabel()
    }

    private fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private fun animateLabel() {

        val animationType = "alpha"
        val animationDuration = 750L
        val animationTargetValue = 1f
        val animationRepeatCount = 1

        ObjectAnimator.ofFloat(loadingLabel, animationType, animationTargetValue).apply {
            duration = animationDuration
            repeatCount = animationRepeatCount
            repeatMode = ValueAnimator.REVERSE
            doOnEnd {
                checkIfUserIsAuthenticated()
            }
            start()
        }
    }

    /* Show user social sign in buttons */
    private fun showUserAuth() {

        if (socialLayoutAnimationDone) return

        // TODO replace hardcoded values
        val startDelay = 300L
        val animationDuration = 1000L

        ViewCompat.animate(img_logo)
            .translationY(-calculateImageTranslationY())
            .setStartDelay(startDelay)
            .setDuration(animationDuration)
            .withEndAction { socialLayoutAnimationDone = true }
            .start()

        for (i in 1 until socialContainer.childCount + 1) {
            val v = socialContainer.getChildAt(i - 1)
            var viewAnimator: ViewPropertyAnimatorCompat
            viewAnimator = if (v !is Button && v !is SignInButton) {
                ViewCompat.animate(v)
                    .alpha(1f)
                    .setStartDelay(startDelay * i + 500)
                    .setDuration(1000)
            } else {
                ViewCompat.animate(v)
                    .scaleY(1f).scaleX(1f)
                    .setStartDelay(startDelay * i + 500)
                    .setDuration(500)
            }
            viewAnimator.setInterpolator(DecelerateInterpolator()).start()
        }

        login_btn_google.setOnClickListener {
            continueWithGoogle()
        }

        login_btn_facebook.setOnClickListener {
            continueWithFacebook()
        }
    }

    private fun calculateImageTranslationY(): Float {
        val dip = 175f
        val r: Resources = resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.displayMetrics)
    }

    private fun continueWithGoogle() {
        val signInClient = buildGoogleSignInClient()
        val intent = signInClient.signInIntent
        startActivityForResult(intent, AppConsts.RC_SIGN_IN)
    }

    private fun buildGoogleSignInClient(): GoogleSignInClient {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        return GoogleSignIn.getClient(activity as SplashActivity, gso)
    }

    private fun continueWithFacebook(){
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                splashViewModel.firebaseAuthWithFacebook(loginResult)
            }

            override fun onCancel() {
                println("ALEX_TAG - SplashFragment->onCancel")
            }
            override fun onError(error: FacebookException) {
                error.printStackTrace()
            }
        })

        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConsts.RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result!!.isSuccess) {
                val account = result.signInAccount
                splashViewModel.firebaseAuthWithGoogle(account)
            } else {

                shortToast("Sign In Failed")
            }
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

}
