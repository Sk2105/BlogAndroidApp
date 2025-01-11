package com.composeapp.blogapp.presentation.profile.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.Files
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.composeapp.blogapp.data.remote.filepart.prepareFilePart
import com.composeapp.blogapp.presentation.profile.ProfileViewModel
import com.composeapp.blogapp.presentation.viewmodel.MainViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun createFilePart(context: Context, uri: Uri, partName: String): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val tempFile = File(context.cacheDir, "upload_temp_file.jpg") // Create a temporary file
    tempFile.outputStream().use { outputStream ->
        inputStream?.copyTo(outputStream)
    }

    val requestFile = RequestBody.create(
        contentResolver.getType(uri)?.toMediaTypeOrNull(),
        tempFile
    )
    return MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    isShowEditButton: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    signOutPressed:  () -> Unit,
    onBackPressed: () -> Unit
) {

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data
                imageUri?.let { uri ->
                    val body = createFilePart(context, uri, "file")
                    profileViewModel.uploadFile(body)

                }
            }
        }
    )
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(0.2f))
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xff212121),
            scrolledContainerColor = Color(0xff212121),
            navigationIconContentColor = Color.White
        ),
        actions = {

            if (isShowEditButton) {
                IconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        launcher.launch(intent)
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "")
                }

                // sign out
                IconButton(
                    onClick = {
                        signOutPressed()

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Rounded.ExitToApp, contentDescription = "")
                }
            }
        },

        )
}