package com.c10.finalproject.ui.user.profile


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.c10.finalproject.R
import com.c10.finalproject.databinding.FragmentProfileBinding
import com.c10.finalproject.ui.MainActivity
import com.c10.finalproject.ui.UserActivity
import com.c10.finalproject.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


const val REQUEST_CODE_PERMISSION = 201

@Suppress("DEPRECATION")

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangeImage.setOnClickListener { checkingPermission() }
        binding.btnUpdate.setOnClickListener { toUpdateAccount() }
        binding.btnLogout.setOnClickListener { toLogout() }

    }

    private fun setProfilePicture(bitmap: Bitmap) {

    }

    private fun checkingPermission() {
        if (isGranted(
                requireActivity(), Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), REQUEST_CODE_PERMISSION
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Choose Picture")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .show()
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->

        }

    private fun getImageUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "Initial Profile Picture",
            null
        )
        return Uri.parse(path)
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is Denied, pleas Allow Permission from settings")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun toUpdateAccount() {
        val cardNumber = binding.etCardNumber.text.toString()
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val name = binding.etName.text.toString()
        val contact = binding.etContact.text.toString()
        val birthday = binding.etBirthday.text.toString()
        val address = binding.etAddress.text.toString()

    }

    private fun toLogout() {
        profileViewModel.clearToken()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}