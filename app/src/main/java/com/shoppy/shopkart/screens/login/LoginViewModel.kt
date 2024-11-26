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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val mAuth: FirebaseAuth = Firebase.auth

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

    fun onSignInResult(resultData: SignInResultData) {
        _state.update { it.copy(
            isSuccess = resultData.data != null,
            error = resultData.errorMessage
        ) }
    }

    //Only used for Google Login
    fun addUserToDB(){

        viewModelScope.launch {

//            val userId = mAuth.currentUser?.uid

            val currentUser = mAuth.currentUser

            val fb = FirebaseFirestore.getInstance().collection("Users").document(currentUser?.email!!)

            var image: String? = ""

            var phone_no: String? = ""
            var address: String? = ""

            fb.get().addOnSuccessListener { docSnap ->
                phone_no = docSnap.data?.getValue("phone_no").toString()
                address = docSnap.data?.getValue("address").toString()
                image = docSnap.data?.getValue("profile_image").toString()
            }.await()

            //Giving delay because it takes time to load data from FB and the app will crash otherwise
            delay(800)

            val user = MUser(id = currentUser.uid,
                name = currentUser.displayName,
                email = currentUser.email,
                password = "Google SignIn",
                phone_no = phone_no?.ifEmpty { "" },
                address =  address?.ifEmpty { "" },
                profile_image = image?.ifEmpty { currentUser.photoUrl.toString() }).convertToMap()

            fb.set(user)
        }

    }

    fun forgotPassword(email: String, success:() -> Unit, newPassword: String, error:(String) -> Unit){

        viewModelScope.launch {

            mAuth.sendPasswordResetEmail(email).addOnSuccessListener { success()

                FirebaseFirestore.getInstance().collection("Users").document(email).update("password",newPassword)

            }.addOnFailureListener{ e -> error(e.message.toString()) }
        }
    }
}