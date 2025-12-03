package com.zeynep1yuksel.baseapp.ml

import android.content.Context
import android.graphics.Bitmap
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import org.tensorflow.lite.Interpreter

class SorioClassifier(private val context: Context) {

    private val MODEL_NAME = "sorio_ders_tanima.tflite"

    private val INPUT_SIZE = 224
    private val PIXEL_SIZE = 3


    private val LABELS = listOf("math", "turkish")
    private var interpreter: Interpreter? = null
    init {
        try {
            interpreter = Interpreter(loadModelFile())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun loadModelFile(): ByteBuffer {
        val assetFileDescriptor = context.assets.openFd(MODEL_NAME)
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    fun classify(bitmap: Bitmap): String {
        if (interpreter == null) return "Model Yüklenemedi"

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true)
        val byteBuffer = convertBitmapToByteBuffer(resizedBitmap)

        val output = Array(1) { FloatArray(LABELS.size) }

        interpreter?.run(byteBuffer, output)

        val matPuani = output[0][0]
        val turkcePuani = output[0][1]

        return if (matPuani > turkcePuani) {
            "Matematik 📐\n(Güven: %${(matPuani * 100).toInt()})"
        } else {
            "Türkçe 📚\n(Güven: %${(turkcePuani * 100).toInt()})"
        }
    }
    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val value = intValues[pixel++]
                val r = (value shr 16 and 0xFF)
                val g = (value shr 8 and 0xFF)
                val b = (value and 0xFF)

                byteBuffer.putFloat((r - 127.5f) / 127.5f)
                byteBuffer.putFloat((g - 127.5f) / 127.5f)
                byteBuffer.putFloat((b - 127.5f) / 127.5f)
            }
        }
        return byteBuffer
    }
}