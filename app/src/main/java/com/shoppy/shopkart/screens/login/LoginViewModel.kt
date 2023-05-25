package com.shoppy.shopkart.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.shoppy.shopkart.data.SuccessOrError
import com.shoppy.shopkart.models.MUser
import com.shoppy.shopkart.models.SignInResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val mAuth: FirebaseAuth = Firebase.auth

//    val successOrError: MutableState<SuccessOrError<Boolean,String>> = mutableStateOf(SuccessOrError(false,""))

    private val _state = MutableStateFlow(SuccessOrError())
    val state = _state.asStateFlow()

    fun loginUser(
        email: String, password: String,
        except: (String) -> Unit = {message ->},
        nav: () -> Unit = {}
    ) {
        viewModelScope.launch {

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    nav()
                }
                .addOnFailureListener {
                    except(it.message.toString())
                }
        }
    }

//    fun onSignInResult(resultData: SignInResultData){
//
//        successOrError.value.isSuccess = resultData.data != null
//        Log.d("RESULT", "onSignInResult: ${resultData.data != null}")
//        successOrError.value.error = resultData.errorMessage.toString()
//    }
//
//    fun resetState(){
//        successOrError.value = successOrError.value
//    }

    fun onSignInResult(resultData: SignInResultData) {
        _state.update { it.copy(
            isSuccess = resultData.data != null,
            error = resultData.errorMessage
        ) }
    }

    fun addUserToDB(){
        viewModelScope.launch {

            val userId = mAuth.currentUser?.uid

            val currentUser = mAuth.currentUser

            val user = MUser(id = currentUser?.uid, name = currentUser?.displayName, email = currentUser?.email, password = "Google SignIn", phone_no = "", address = "", profile_image = currentUser?.photoUrl.toString()).convertToMap()
//            delay(800)
            val fb = FirebaseFirestore.getInstance().collection("Users").document(userId!!)

            fb.set(user)
        }

    }

    fun forgotPassword(email: String,success:() -> Unit,error:(String) -> Unit){
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener { success() }.addOnFailureListener{ e -> error(e.message.toString()) }
    }
}