package com.dicoding.kenalikan.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.ResultActivity
import com.dicoding.kenalikan.databinding.FragmentCameraBinding
import com.dicoding.kenalikan.retrofit.ApiConfig
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    // Registering for activity result in property initialization
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.analyzeButton.setOnClickListener { uploadImage() }

        binding.cropButton.setOnClickListener {
            if (currentImageUri != null) {
                cropImage()
            } else {
                showToast(getString(R.string.not_found_img))
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image Classification File", "showImage: ${imageFile.path}")
            showLoading(true)

            val requestImageFile = imageFile.asRequestBody("image/jpg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val fishResponse = apiService.uploadImage(multipartBody)

                    val fishName = fishResponse.name
                    val fishDescription = fishResponse.description

                    val intent = Intent(requireContext(), ResultActivity::class.java)
                    intent.putExtra(EXTRA_IMAGE_URI, currentImageUri.toString())
                    intent.putExtra(EXTRA_FISH_NAME, fishName)
                    intent.putExtra(EXTRA_FISH_DESCRIPTION, fishDescription)
                    startActivity(intent)
                    showLoading(false)
                } catch (e: HttpException) {
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun cropImage() {
        currentImageUri?.let { uri ->
            val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_image"))
            val options = UCrop.Options().apply {
                setCompressionFormat(Bitmap.CompressFormat.PNG)
                setCompressionQuality(100)
                setToolbarColor(ContextCompat.getColor(requireContext(), R.color.white))
                setActiveControlsWidgetColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            val intent = UCrop.of(uri, destinationUri)
                .withAspectRatio(16F, 9F)
                .withMaxResultSize(200, 200)
                .withOptions(options)
                .getIntent(requireContext())

            // Start UCrop activity for result
            startActivityForResult(intent, UCrop.REQUEST_CROP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            currentImageUri = resultUri
            showImage()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast("Crop failed: $cropError")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
        const val EXTRA_FISH_NAME = "EXTRA_FISH_NAME"
        const val EXTRA_FISH_DESCRIPTION = "EXTRA_FISH_DESCRIPTION"
    }
}