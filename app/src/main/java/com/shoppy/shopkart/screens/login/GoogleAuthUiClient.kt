package com.shoppy.shopkart.screens.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.models.SignInResultData
import com.shoppy.shopkart.models.UserData
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(private val context: Context, private val oneTapClient: SignInClient) {

    private val gAuth = Firebase.auth

    suspend fun signIn(): IntentSender?{
        val result = try {
            oneTapClient.beginSignIn(buildSignInReq()).await()
        }catch (ex: Exception){

            Toast.makeText(context,"${ex.message}",Toast.LENGTH_SHORT).show()
            //If the task is cancelled return null
            if (ex is CancellationException) throw ex
            null
        }
        return result?.pendingIntent?.intentSender
    }

    //Getting Credentials from Intent
    suspend fun signInWithIntent(intent: Intent): SignInResultData{
        val credentials = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credentials.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken,null)

        return try {
            val user = gAuth.signInWithCredential(googleCredentials).await().user

            SignInResultData(data = UserData(userId = user?.uid, userName = user?.displayName, profileUrl = user?.photoUrl.toString()),
                errorMessage = null)

        }catch (ex: Exception){
            Toast.makeText(context,"${ex.message}",Toast.LENGTH_SHORT).show()
            if (ex is CancellationException) throw ex

            SignInResultData(data = null, errorMessage = ex.message)

        }
    }

    private fun buildSignInReq(): BeginSignInRequest{
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
                //If true only accounts signed in will be shown
            .setFilterByAuthorizedAccounts(false)
            //Web Client Id from Firebase
            .setServerClientId(ShopKartUtils.webClientId).build())
                //If Only one account is present directly select that one
            .setAutoSelectEnabled(false).build()
    }
}