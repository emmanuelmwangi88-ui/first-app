package com.deepseek.firstapp.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.deepseek.firstapp.models.User
import com.deepseek.firstapp.navigation.ROUTE_DASHBOARD
import com.deepseek.firstapp.navigation.ROUTE_LOGIN
import com.deepseek.firstapp.navigation.ROUTE_REGISTER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel(var navController: NavHostController,var context: Context){
    var mAuth= FirebaseAuth.getInstance()
    //register function
    fun signup(fullname : String,email: String,password: String,confirmpass : String) {
        //validation
        if (email.isBlank() || password.isBlank() || confirmpass.isBlank()) {
            Toast.makeText(context, "email and password cannot be blank", Toast.LENGTH_LONG)
            return
        } else if (password != confirmpass) {
            Toast.makeText(context, "password does not match", Toast.LENGTH_LONG).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userData = User(fullname, email, password, mAuth.currentUser!!.uid)
                        val regRef = FirebaseDatabase.getInstance().getReference()
                            .child("Users/" + mAuth.currentUser!!.uid)
                        regRef.setValue(userData).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "user registered successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(ROUTE_LOGIN)
                            } else {
                                Toast.makeText(
                                    context,
                                    "${it.exception!!.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(ROUTE_LOGIN)
                            }
                        }
                    } else {
                        navController.navigate(ROUTE_REGISTER)
                    }
                }
        }
    }
//login funtion
fun login(email: String,password: String) {
    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
        if (it.isSuccessful) {
            Toast.makeText(context, "successful login", Toast.LENGTH_LONG).show()
            navController.navigate(ROUTE_DASHBOARD)
        }
        else{
            Toast.makeText(context,"error logging in", Toast.LENGTH_SHORT).show()
        }
    }
}
    //log out function
}