package com.brunooa.mygymprogress.data.local.database

import android.content.Context

actual fun getApplicationContext(): Context {
    // iOS doesn't use Android Context, but we need to return something
    // This will be handled by Room's iOS driver
    throw UnsupportedOperationException("getApplicationContext not supported on iOS")
}
