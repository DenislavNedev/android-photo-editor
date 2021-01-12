package com.dnedev.photoeditor.navigation

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.ui.main.MainActivity
import com.dnedev.photoeditor.utils.AUTHORITY
import com.dnedev.photoeditor.utils.CACHE_IMAGES_DIR
import com.dnedev.photoeditor.utils.CACHE_IMAGE_FILE_NAME
import com.dnedev.photoeditor.utils.IMAGE_QUALITY
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

fun FragmentActivity.navigateTo(navigateTo: NavigateTo) {
    when (navigateTo) {
        is GraphNav -> navigateWithNavGraph(navigateTo.actionId, navigateTo.bundle)
        is SharePhoto -> sharePhoto(navigateTo.bitmap)
    }
}

private fun FragmentActivity.navigateWithNavGraph(@IdRes actionId: Int, bundle: Bundle?) {
    if (this is MainActivity) {
        nav_host_fragment.findNavController().navigate(actionId, bundle)
    }
}

private fun FragmentActivity.sharePhoto(bitmap: Bitmap) {

    try {
        File(this.cacheDir, CACHE_IMAGES_DIR).let { cachePath ->
            cachePath.mkdirs()
            FileOutputStream(
                File(
                    cachePath,
                    CACHE_IMAGE_FILE_NAME
                )
            ).let { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, stream)
                stream.close()


                FileProvider.getUriForFile(
                    this,
                    AUTHORITY,
                    File(File(cacheDir, CACHE_IMAGES_DIR), CACHE_IMAGE_FILE_NAME)
                ).let { contentUri ->
                    with(Intent()) {
                        action = Intent.ACTION_SEND
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        setDataAndType(
                            contentUri,
                            contentResolver.getType(contentUri)
                        )
                        putExtra(Intent.EXTRA_STREAM, contentUri)
                        startActivity(Intent.createChooser(this, getString(R.string.choose_an_app)))
                    }
                }
            }
        }
    } catch (e: Exception) {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
    }
}