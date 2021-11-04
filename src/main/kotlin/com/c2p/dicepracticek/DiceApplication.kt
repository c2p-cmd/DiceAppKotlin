package com.c2p.dicepracticek

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlin.concurrent.thread

class DiceApplication : Application(), Runnable {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(DiceApplication::class.java.getResource("app-layout.fxml"))
        val scene = Scene(fxmlLoader.load(), 420.0, 420.0)
        stage.title = "Dice App"
        stage.scene = scene
        stage.icons.add(
            Image(
                DiceApplication::class.java.getResourceAsStream(
                    "DiceRoller.jpg"
                )
            )
        )
        stage.isResizable = false
        stage.show()
    }

    override fun run() {
        launch(DiceApplication::class.java)
    }
}

fun main() {
    thread(
        start = true,
        name = "Dice App Thread."
    ) {
        println("Currently Running Thread: ${Thread.currentThread().name}")
        DiceApplication().run()
    }
}