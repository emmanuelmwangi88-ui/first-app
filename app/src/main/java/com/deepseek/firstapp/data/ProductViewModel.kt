package com.deepseek.firstapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.deepseek.firstapp.models.Product
import com.deepseek.firstapp.navigation.ROUTE_PRODUCTSLISTSCREEN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class ProductViewModel(var navController: NavHostController, var context: Context) {
    private val cloudinaryUrl = "https://api.cloudinary.com/v1_1/dnt3lcyoj/image/upload"
    private val uploadPreset = "products"
    private val databaseReference = FirebaseDatabase.getInstance().getReference("Products")

    fun uploadProduct(
        imageUri: Uri?,
        name: String,
        price: String,
        description: String
    ) {
        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || imageUri == null) {
            Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            return
        }

        val ref = databaseReference.push()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 1. Upload to Cloudinary
                val imageUrl = uploadToCloudinary(context, imageUri)

                // 2. Prepare Product Data
                val productData = Product(
                    id = ref.key ?: "",
                    name = name,
                    price = price,
                    description = description,
                    imageUrl = imageUrl,
                    userId = userId
                )

                // 3. Save to Firebase
                withContext(Dispatchers.Main) {
                    ref.setValue(productData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUTE_PRODUCTSLISTSCREEN) {
                                popUpTo(ROUTE_PRODUCTSLISTSCREEN) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Database error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw Exception("Could not read image file")

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url(cloudinaryUrl)
            .post(requestBody)
            .build()

        val response = OkHttpClient().newCall(request).execute()
        val responseBody = response.body?.string() ?: ""

        if (!response.isSuccessful) {
            val errorMessage = Regex("\"message\":\"(.*?)\"").find(responseBody)?.groupValues?.get(1)
            throw Exception(errorMessage ?: "Cloudinary upload failed")
        }

        val secureUrl = Regex("\"secure_url\":\"(.*?)\"").find(responseBody)?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to extract image URL from response")
    }

    //fetch Products
    fun allProducts(
        products: SnapshotStateList<Product>
    ): SnapshotStateList<Product>{
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val retrievedProduct = snap.getValue(Product::class.java)
                    if (retrievedProduct != null) {
                        products.add(retrievedProduct)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }

    fun deleteProduct(id: String) {
        databaseReference.child(id).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Delete failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
