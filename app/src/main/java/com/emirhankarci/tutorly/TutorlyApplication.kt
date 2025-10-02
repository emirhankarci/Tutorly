package com.emirhankarci.tutorly

import android.app.Application
import com.adapty.Adapty
import com.adapty.models.AdaptyConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TutorlyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Adapty.activate(
            applicationContext,
            AdaptyConfig.Builder("public_live_Zb5iflSN.7tfhd9p3Vjd9xNzPLpbu")
                .build()
        )
    }
}
