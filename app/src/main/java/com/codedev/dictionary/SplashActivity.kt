package com.codedev.dictionary

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.codedev.base.UIDestination
import com.codedev.room_lib.DictionaryDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
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
        setContentView(R.layout.activity_splash)

        val dialog = ProgressDialogFragment()

        dialog.show(supportFragmentManager, ProgressDialogFragment::class.java.simpleName)

        lifecycleScope.launchWhenCreated {
            val database = (applicationContext as BaseApplication).provideBaseComponent()
                .getDictionaryDatabase()
            val (exception, isSuccess) = DictionaryDatabase.performInitialDBOperations(database, applicationContext)

            if (isSuccess) {
                dialog.dismissNow()
                launchHomeActivity()
            }
        }
    }

    private fun launchHomeActivity() {
        Intent(UIDestination.HOME).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

}

