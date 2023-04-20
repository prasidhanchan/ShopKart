package com.shoppy.shopkart.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    fun getUserName(user: (String) -> Unit) {

        val mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser!!.uid

        FirebaseFirestore.getInstance().collection("Users").document(currentUser).get()
                .addOnSuccessListener { document ->
                    user(document.data!!.getValue("name").toString())
                }
    }

    fun getSliders(except: (String) -> Unit,sliders: (List<Any?>) -> Unit) {

//        sliders(listOf(
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        ))

        viewModelScope.launch {

            try {
                FirebaseFirestore.getInstance().collection("Sliders").document("sliders").get()
                    .addOnSuccessListener { document ->
                        sliders(document.data!!.values.toList())
                    }
            }catch (ex: Exception){
                except(ex.message.toString())
            }

        }

    }
}