package com.code.weather.di

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.code.weather.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class DialogAlertModule {

    @Provides
    fun provideDailogAlert(
        @ActivityContext context: Context
    ): DialogAlert {
        return DialogAlert(context)
    }
}

class DialogAlert(val context: Context) {
    fun networkAlert(): AlertDialog =
        alertDialogBuilder(
            context,
            android.R.attr.alertDialogIcon,
            context.getString(R.string.network_error),
            context.getString(R.string.check_settings),
            context.getString(R.string.exit)
        )

    private fun alertDialogBuilder(
        context: Context,
        icon: Int,
        title: String,
        message: String,
        buttonTxt: String
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setIconAttribute(icon)
            .setMessage(message)
            .setPositiveButton(buttonTxt) { _, _ ->
                (context as Activity).finish()
            }.setCancelable(false).show()
    }
}
