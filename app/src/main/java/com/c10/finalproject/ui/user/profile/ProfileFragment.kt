package com.c10.finalproject.ui.user.profile


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c10.finalproject.R
import com.c10.finalproject.data.remote.model.user.BodyUpdateUser
import com.c10.finalproject.data.remote.model.user.GetUserResponse
import com.c10.finalproject.databinding.FragmentProfileBinding
import com.c10.finalproject.ui.MainActivity
import com.c10.finalproject.utils.Utils
import com.c10.finalproject.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

private const val REQUEST_CODE_PERMISSION = 3
private const val GALLERY_RESULT_CODE = 15

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener { toLogout() }
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getToken().collect() {
                    if (it.isNotEmpty()) {
                        viewModel.getUserByToken(it)
                    }
                }
            }
        }
        val list = mutableListOf<String>("Male", "Female")
        val adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.list_textview, list
            )
        (binding.etGender as? AutoCompleteTextView)?.setAdapter(adapter)
        birthday()
        binding.btnChangeImage.setOnClickListener { checkPermissions() }
        observeData()
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    //setLoadingState(true)
                }
                is Resource.Error -> {
                    //setLoadingState(true)
                }
                is Resource.Success -> {
                    //setLoadingState(false)
                    setView(it.payload!!)
                    binding.btnUpdate.setOnClickListener { _ -> toUpdateAccount(it.payload) }
                }
                else -> {

                }
            }
        }
    }

    private fun setView(getUserResponse: GetUserResponse) {
        binding.apply {
            etCardNumber.setText(getUserResponse.data?.noKtp ?: "")
            etUsername.setText(getUserResponse.data?.username ?: "")
            tvEmail.text = getUserResponse.data?.email ?: ""
            etName.setText(getUserResponse.data?.name ?: "")
            etContact.setText(getUserResponse.data?.contact ?: "")
            etBirthday.setText(getUserResponse.data?.dateOfBirth?.substring(0, 10) ?: "")
            etAddress.setText(getUserResponse.data?.address ?: "")
            etGender.setText(getUserResponse.data?.gender ?: "")
            isImageEmpty(getUserResponse.data?.photoProfile.toString())
        }
    }

    private fun isImageEmpty(urlImage: String) {
        if (urlImage.isNotEmpty()) {
            Glide.with(requireContext()).load(urlImage).into(binding.imageUser)
        } else {
            binding.imageUser.background =
                resources.getDrawable(R.drawable.ic_photo_profile)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun birthday() {
        binding.apply {
            etBirthday.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        if (dayOfMonth in 1..9 && monthOfYear in 1..9) {
                            etBirthday.setText("$year-0" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (dayOfMonth in 1..9) {
                            etBirthday.setText("$year-" + (monthOfYear + 1) + "-0$dayOfMonth")
                        } else if (monthOfYear in 1..9) {
                            etBirthday.setText("$year-0" + (monthOfYear + 1) + "-$dayOfMonth")
                        } else {
                            etBirthday.setText("$year-" + (monthOfYear + 1) + "-$dayOfMonth")
                        }
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }


    private fun toUpdateAccount(getUserResponse: GetUserResponse) {
        val cardNumber = binding.etCardNumber.text.toString()
        val username = binding.etUsername.text.toString()
        val name = binding.etName.text.toString()
        val contact = binding.etContact.text.toString()
        val birthday = binding.etBirthday.text.toString()
        val address = binding.etAddress.text.toString()
        val gender = binding.etGender.text.toString()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Message Dialog")
        builder.setMessage(
            "Are you sure you want to update profile?"
        )

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.photo.observe(viewLifecycleOwner) {
                viewModel.updateUser(
                    getUserResponse.data?.id!!,
                    BodyUpdateUser(
                        noKtp = cardNumber,
                        username = username,
                        name = name,
                        contact = contact,
                        dateOfBirth = birthday.ifEmpty { null },
                        address = address,
                        photoProfile = it ?: getUserResponse.data.photoProfile,
                        gender = gender
                    )
                )
            }
            findNavController().navigate(R.id.profileFragment)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()

    }

    private fun toLogout() {
        viewModel.clear()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext()).setMessage("Select Picture")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }.show()
    }

    private fun checkPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
            )
        ) {
//            chooseImageDialog()
            openGallery()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun openGallery() {
        val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        imageIntent.type = "image/*"
        imageIntent.action = Intent.ACTION_GET_CONTENT
        galleryResult.launch(imageIntent.type)
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.i("TEST GALERY", result.toString())
            Glide.with(requireContext()).load(result)
                .into(binding.imageUser)
            viewModel.addPhoto(result.toString())
//            viewModel.saveImage(result.toString())
        }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        //Log.i("TEST CAM", Utils.convertBitmapToString(bitmap))
//        binding.imageUser.setImageURI(Utils.convertBitmapToString(bitmap).toUri())
        Glide.with(requireContext()).load(bitmap)
            .into(binding.imageUser)
        viewModel.addPhoto(Utils.convertBitmapToString(bitmap))
    }

    private fun isGranted(
        activity: Activity,
        permission: String, //for camera
        permissions: Array<String>, //for read write storage/gallery
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(requireContext(), permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    permission
                )
            ) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission denied")
            .setMessage("Permission is denied, Please allow app permission from App Settings")
            .setPositiveButton("App Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", "packageName", null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}