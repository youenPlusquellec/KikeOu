package com.example.kikeou

//Naming convention: camera_layout.xml layout -> CameraLayoutBinding
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.kikeou.databinding.CameraLayoutBinding
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage


class CameraActivity : AppCompatActivity() {
    private val REQUEST_CODE = 123456

    private lateinit var binding: CameraLayoutBinding
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding view element from layout
        binding =  CameraLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initCameraAndTF()
                } else {
                    Toast.makeText(this, this.getString(R.string.permissionToGrant), Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Do not forget to declare  <uses-permission android:name="android.permission.CAMERA"/> in your manifest
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initCameraAndTF()
        } else {
            val permissions = arrayOf(android.Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        }
    }

    //Only the original thread that created a view can touch its views.
    private fun initCameraAndTF() = binding.previewView.post {
        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )

        // future do not block
        // get() is used to get the instance of the future when available
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener( {

            val cameraProvider = cameraProviderFuture.get()

            // Set up the view finder use case to display camera preview
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()

            // Set up the image analysis use case which will process frames in real time
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1080, 2340))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this),
                @androidx.camera.core.ExperimentalGetImage { imageProxy ->
                    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                    val image = imageProxy.image
                    if(image != null) {
                        val processImage = InputImage.fromMediaImage(image, rotationDegrees)

                        //See https://developers.google.com/ml-kit/vision/barcode-scanning/android
                        barcodeScanner.process(processImage).addOnFailureListener {
                            Log.e("ScannerActivity", "Error: $it.message")
                            imageProxy.close()
                        }.addOnSuccessListener { barcodes ->
                            var found = false

                            for (it in barcodes) {
                                if(it.displayValue != null)
                                {
                                    found = true

                                    if(binding.layout.childCount > 1)  {
                                        binding.layout.removeViewAt(1)
                                    }

                                    val element = Draw(this, it.boundingBox)
                                    binding.layout.addView(element,1)

                                    val data = Intent()
                                    data.putExtra("json", it.displayValue)

                                    setResult(Activity.RESULT_OK, data)
                                }
                            }
                            imageProxy.close()

                            if(found)
                                finish()
                        }
                    }
                })

            // Create a new camera selector each time, enforcing lens facing
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            // Apply declared configs to CameraX using the same lifecycle owner
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, imageAnalysis)

            // Use the camera object to link our preview use case with the view
            preview.setSurfaceProvider(binding.previewView.surfaceProvider)
        },
            ContextCompat.getMainExecutor(this)
        )
    }

    private class Draw(context: Context?, var rect: Rect) : View(context) {
        lateinit var paint: Paint

        init {
            init()
        }

        private fun init() {
            paint = Paint()
            paint.color = Color.BLUE
            paint.strokeWidth = 20f
            paint.style = Paint.Style.STROKE
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawRect(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat(), paint)
        }
    }
}