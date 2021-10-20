package com.c2p.dicepracticek

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class DiceApplication : Application() {
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
}

fun main() {
    Application.launch(DiceApplication::class.java)
}