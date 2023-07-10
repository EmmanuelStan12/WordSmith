package com.codedev.dictionary

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.codedev.base.UIDestination
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class SplashActivity : FragmentActivity() {

    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var requestSinglePermissionLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition{ true }
        }

        super.onCreate(savedInstanceState)

        Intent(UIDestination.HOME).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

}

