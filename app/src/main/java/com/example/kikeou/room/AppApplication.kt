package com.example.kikeou.room

import android.app.Application


class AppApplication : Application() {
    val database by lazy {AppDatabase.getDatabase(this)}
}