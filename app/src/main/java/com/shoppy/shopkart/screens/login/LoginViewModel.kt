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

class LoginViewModel : ViewModel() {

    private val mAuth: FirebaseAuth = Firebase.auth
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

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

            val fb = FirebaseFirestore.getInstance().collection("Users").document(userId!!)

            var phone_no: String? = ""
            var address: String? = ""

            fb.get().addOnSuccessListener { phoneNo -> phone_no = phoneNo.data?.getValue("phone_no").toString()
                address = phoneNo.data?.getValue("address").toString()}

            //Giving delay because it takes time to load data from FB and the app will crash otherwise
            delay(800)

            val user = MUser(id = currentUser?.uid,
                name = currentUser?.displayName,
                email = currentUser?.email, password = "Google SignIn",
                phone_no = phone_no?.ifEmpty { "" },
                address = address?.ifEmpty { "" },
                profile_image = currentUser?.photoUrl.toString()).convertToMap()

            fb.set(user)
        }

    }

    fun forgotPassword(email: String,success:() -> Unit,error:(String) -> Unit){
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener { success() }.addOnFailureListener{ e -> error(e.message.toString()) }
    }
}