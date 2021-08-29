package com.example

import com.example.view.LoginView
import com.example.view.MainView
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(LoginView::class, Styles::class)  {
    override fun start(stage: Stage) {
        with(stage) {
            width = 1100.0
            height = 800.0
        }
        super.start(stage)
    }
}