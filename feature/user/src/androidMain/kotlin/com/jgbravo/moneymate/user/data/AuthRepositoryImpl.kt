package com.jgbravo.moneymate.user.data

import com.google.firebase.auth.FirebaseAuth
import com.jgbravo.moneymate.core.data.Result
import com.jgbravo.moneymate.core.utils.CommonStateFlow
import com.jgbravo.moneymate.core.utils.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {

    override val currentUser
        get() = UserInfoData(
            photoUrl = auth.currentUser?.photoUrl.toString(),
            displayName = auth.currentUser?.displayName,
            email = auth.currentUser?.email,
            phoneNumber = auth.currentUser?.phoneNumber,
            providerId = auth.currentUser?.providerId,
            userId = auth.currentUser?.uid,
            isEmailVerified = auth.currentUser?.isEmailVerified ?: false,
        )

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ): Result {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun sendEmailVerification(): Result {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ): Result {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun reloadFirebaseUser(): Result {
        return try {
            auth.currentUser?.reload()?.await()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): Result {
        return try {
            auth.currentUser?.delete()?.await()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun getAuthState(viewModelScope: CoroutineScope): CommonStateFlow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null).toCommonStateFlow()
}