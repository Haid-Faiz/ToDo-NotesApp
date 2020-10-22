package com.example.todonotes

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.drm.DrmStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.airbnb.lottie.BuildConfig
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var inputLayoutTitle: TextInputLayout
    private lateinit var inputLayoutDesc: TextInputLayout
    private lateinit var saveButton: Button
    private val REQUEST_CODE_GALLERY = 1
    private val REQUEST_CODE_CAMERA = 2
    private var picturePath = ""
    private val MY_PERMISSION_CODE = 124

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        bindView()
        clickListener()

        saveButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var title = inputLayoutTitle.editText?.text.toString().trim()
                var desc = inputLayoutDesc.editText?.text.toString().trim()

                if(title.isNotEmpty() and desc.isNotEmpty()) {
                    var intent = Intent()
                    intent.putExtra("title", title)
                    intent.putExtra("desc", desc)
                    intent.putExtra("imagePath", picturePath)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else{
                    if (title.isNotEmpty())
                        inputLayoutTitle.error = null
                    else
                        inputLayoutTitle.error = "Required"

                    if (desc.isNotEmpty())
                        inputLayoutDesc.error = null
                    else
                        inputLayoutDesc.error = "Required"
                }
            }

        })
    }

    private fun clickListener() {
        imageView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if (isPermissionChecked()){
                    setUpDialog()
                }
            }
        })
    }

    private fun isPermissionChecked(): Boolean {

        var cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        var storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        //var listOfPermission = arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        var listOfPermission = ArrayList<String>()

        if (cameraPermission !=  PackageManager.PERMISSION_GRANTED)
            listOfPermission.add(android.Manifest.permission.CAMERA)

        if (storagePermission != PackageManager.PERMISSION_GRANTED)
            listOfPermission.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (listOfPermission.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listOfPermission.toTypedArray<String>(), MY_PERMISSION_CODE) // it requires Array not ArrayList
            return false
        }else
            return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)   // Granted permission grantResults array me aa jaati hai
                    setUpDialog()
                else
                    Toast.makeText(this@AddNotesActivity, "Permission required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpDialog() {
        var view = LayoutInflater.from(this).inflate(R.layout.custom_selector_view, null)
        var galleyText = view.findViewById<TextView>(R.id.galleryTVID)
        var cameraText : TextView = view.findViewById(R.id.cameraTVID)

        var alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()
        alertDialog.show()

        galleyText.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var  galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // var intent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY)
                alertDialog.hide()
            }
        })

        cameraText.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                var photoFile : File = createImage()

                if (photoFile != null) {

                    val photoUri = FileProvider.getUriForFile(this@AddNotesActivity, BuildConfig.APPLICATION_ID +".provider", photoFile)
                    picturePath = photoFile.absolutePath
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)  // we want to generate output of this photoUri
                    startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA)

                    alertDialog.hide()
                }
            }
        })
    }

    private fun createImage(): File {

        var timeStamp = SimpleDateFormat("yyyymmddhhmmss").format(Date())      // also write in formate yyyy-mm-dd-hh-mm-ss
        var fileName = "JPEG _" + timeStamp + "_"
        var storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)      // image will be stored in this directory

        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_GALLERY -> {

                    val imageUri = data?.data
                    // imageView.setImageURI(imageUri)
                    val filePath = arrayOf( MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(imageUri!!, filePath, null, null, null)
                    cursor?.moveToFirst()
                    val columnIndex = cursor?.getColumnIndex(filePath[0])
                    picturePath = cursor?.getString(columnIndex!!) !!
                    cursor?.close()
                    Glide.with(this).load(picturePath).circleCrop().into(imageView)
                }
                REQUEST_CODE_CAMERA -> {
                    Glide.with(this).load(picturePath).circleCrop().into(imageView)
                }
            }
        }
    }

    private fun bindView() {
        imageView = findViewById(R.id.imageID)
        inputLayoutTitle = findViewById(R.id.inputLayID1)
        inputLayoutDesc = findViewById(R.id.inputLayID2)
        saveButton = findViewById(R.id.saveBtnID2)
    }
}