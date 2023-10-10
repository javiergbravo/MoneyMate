package com.jgbravo.moneymate.user.data

import com.jgbravo.moneymate.core.data.Result
import com.jgbravo.moneymate.core.utils.CommonStateFlow
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {

    val currentUser: UserInfoData?

    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): Result

    suspend fun sendEmailVerification(): Result

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Result

    suspend fun reloadFirebaseUser(): Result

    suspend fun sendPasswordResetEmail(email: String): Result

    fun signOut()

    suspend fun revokeAccess(): Result

    fun getAuthState(viewModelScope: CoroutineScope): CommonStateFlow<Boolean>
}