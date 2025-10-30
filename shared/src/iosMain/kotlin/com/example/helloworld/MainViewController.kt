package com.example.helloworld

import androidx.compose.ui.window.ComposeUIViewController
import kotlin.native.ObjCName

@ObjCName("MainViewController", exact = true)
fun MainViewController() = ComposeUIViewController { HelloWorldApp() }