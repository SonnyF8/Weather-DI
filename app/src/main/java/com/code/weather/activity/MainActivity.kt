package com.code.weather.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.code.weather.databinding.ActivityMainBinding
import com.code.weather.di.DialogAlert
import com.code.weather.di.NetCheck
import com.code.weather.fragment.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    @Inject lateinit var netCheck: NetCheck
    @Inject lateinit var dialogAlert: DialogAlert

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.mainLayout)

        if (netCheck.isInternetAvailable()) {
            if (savedInstanceState == null)
                supportFragmentManager.beginTransaction()
                    .replace(activityMainBinding.mainLayout.id, MainFragment()).commit()
        } else {
            dialogAlert.networkAlert()
        }
    }
}
