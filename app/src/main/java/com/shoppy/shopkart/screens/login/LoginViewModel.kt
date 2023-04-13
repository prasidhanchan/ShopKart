package com.shoppy.shopkart.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val mAuth: FirebaseAuth = Firebase.auth

    var errorState :String = ""


    fun loginUser(
        email: String, password: String,
        toast: () -> Unit = {},
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
}