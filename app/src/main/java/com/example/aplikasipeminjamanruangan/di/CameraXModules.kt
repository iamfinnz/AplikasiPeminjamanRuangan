package com.example.aplikasipeminjamanruangan.di

import android.app.Application
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.aplikasipeminjamanruangan.data.CameraXRepositoryImp
import com.example.aplikasipeminjamanruangan.domain.repository.ICustomCameraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CameraXModules {
    @Provides
    @Singleton
    fun provideCameraSelector(): CameraSelector {
        return CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
    }

    @Provides
    @Singleton
    fun provideCameraProvider(application: Application): ProcessCameraProvider {
        return ProcessCameraProvider.getInstance(application).get()
    }

    @Provides
    @Singleton
    fun provideCameraPreview(): Preview {
        return Preview.Builder().build()
    }

    @Provides
    @Singleton
    fun provideImageCapture(): ImageCapture {
        return ImageCapture.Builder().setFlashMode(FLASH_MODE_OFF)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9).build()
    }

    @Provides
    @Singleton
    fun provideImageAnalysis(): ImageAnalysis {
        return ImageAnalysis.Builder().setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    @Provides
    @Singleton
    fun provideCustomCameraRepo(
        cameraProvider: ProcessCameraProvider,
        selector: CameraSelector,
        imageCapture: ImageCapture,
        imageAnalysis: ImageAnalysis,
        preview: Preview
    ): ICustomCameraRepository {
        return CameraXRepositoryImp(
            cameraProvider = cameraProvider,
            selector = selector,
            imageCapture = imageCapture,
            imageAnalysis = imageAnalysis,
            preview = preview
        )
    }

}