package com.jgbravo.moneymate.user.signin

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.core.data.Response
import com.jgbravo.moneymate.user.data.UserInfoData
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val oneTapClient: SignInClient
) {

    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (cancellationException: CancellationException) {
            Logger.e(cancellationException)
            throw cancellationException
        } catch (e: Exception) {
            Logger.e(e)
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): Response<UserInfoData> {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            user?.let {
                Response.Success(
                    data = UserInfoData(
                        userId = it.uid,
                        displayName = it.displayName,
                        photoUrl = it.photoUrl?.toString()
                    )
                )
            } ?: Response.Failure(Exception("User is null"))
        } catch (cancellationException: CancellationException) {
            Logger.e(cancellationException)
            throw cancellationException
        } catch (e: Exception) {
            Logger.e(e)
            Response.Failure(Exception(e.message))
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (cancellationException: CancellationException) {
            Logger.e(cancellationException)
            throw cancellationException
        } catch (e: Exception) {
            Logger.e(e)
        }
    }

    fun getSignedInUser(): UserInfoData? = auth.currentUser?.run {
        UserInfoData(
            userId = uid,
            displayName = displayName,
            photoUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("364686885666-eldaon7etsjjolr0ljj6t28ne2gc9t7d.apps.googleusercontent.com") //TODO: drop from here
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}