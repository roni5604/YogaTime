package com.example.yogatime.data.gallery

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID

class GalleryScreenViewModel : ViewModel() {

    private val _images = mutableStateListOf<String>()
    private var delImageName : String? = null
    val imageList = mutableStateListOf<GallertUIStateForDisplay>()
    val popupMessage = mutableStateOf<String?>(null)


    init {
        fetchImages()
    }

    private fun fetchImages() {
        val storageRef = Firebase.storage.reference.child("images")
        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener { uri ->
                        _images.add(uri.toString())
                    }
                }
            }
            .addOnFailureListener {
                // Handle any errors
            }
    }

    companion object {
        fun uploadToStorage(uri: Uri, context: Context) {
            val storage = Firebase.storage

            // Create a storage reference from our app
            val storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            val spaceRef: StorageReference
            spaceRef = storageRef.child("images/$unique_image_name.jpg")

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {

                val uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
    }


    /*
    // Function to upload image to Firebase Storage and then save its metadata to Firebase Realtime Database
    fun uploadImageToFirebaseStorage(imageStream: InputStream, fileName: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            val storageReference = FirebaseStorage.getInstance().reference.child("images/$uid/$fileName")
            val uploadTask = storageReference.putStream(imageStream)

            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Image upload successful, get the download URL
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    // Save the image URL to Firebase Realtime Database
                    val database = FirebaseDatabase.getInstance()
                    val usersReference = database.reference.child("gallery/$uid")

                    val imageId = usersReference.push().key // Generate a unique ID for the image
                    imageId?.let { id ->
                        usersReference.child(id).setValue(imageUrl).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Image URL successfully saved to the database
                                Log.d("UploadImage", "Image URL saved to database successfully.")
                            } else {
                                // Failed to save the image URL to the database
                                Log.e("UploadImage", "Failed to save image URL to database.", task.exception)
                            }
                        }
                    }
                }?.addOnFailureListener { e ->
                    // Handle any errors
                    Log.e("UploadImage", "Failed to get the download URL.", e)
                }
            }.addOnFailureListener { e ->
                // Handle unsuccessful uploads
                Log.e("UploadImage", "Failed to upload image.", e)
            }
        }
    }


    private fun saveImageInfoToDatabase(imageUrl: String, userId: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("gallery")
        val imageId = databaseRef.push().key ?: return

        val imageInfo = hashMapOf(
            "imageUrl" to imageUrl,
            "timestamp" to System.currentTimeMillis()
        )

        databaseRef.child(imageId).setValue(imageInfo).addOnSuccessListener {
            // Optionally handle success, such as logging or additional UI feedback

        }.addOnFailureListener {
            // Optionally handle failure, such as retry mechanisms
        }
    }
    */

    fun onEvent(event : GalleryUIEvent) {
        when (event) {
            is GalleryUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  GalleryUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerProfileScreen)
            }
            is  GalleryUIEvent.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }
            is GalleryUIEvent.delImageName ->{
                delImageName = event.name
            }
            is GalleryUIEvent.DelButtonClicked ->{
                dellImage()
            }
            is GalleryUIEvent.BackButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }
        }
    }

    private fun dellImage() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("images/$delImageName")
        storageRef.delete().addOnSuccessListener {
            popupMessage.value = "delete image successfully"
        }.addOnFailureListener{
            popupMessage.value = "Failure delete image"
        }
    }

    fun getImage() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("images")
        imageList.clear()
        storageRef.listAll().addOnSuccessListener { listResult ->
            for (item in listResult.items) {
                val image = GallertUIStateForDisplay("", "")
                item.downloadUrl.addOnSuccessListener { uri ->
                    if(!imageList.any { it.name == item.name}) {
                        image.url = uri.toString()
                        image.name = item.name
                        imageList.add(image)
                    }
                }
            }
        }
    }
}
