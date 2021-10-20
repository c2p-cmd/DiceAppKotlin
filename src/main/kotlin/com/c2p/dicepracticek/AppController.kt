package com.c2p.dicepracticek

import javafx.animation.AnimationTimer
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.image.ImageView

import java.net.URL
import java.util.*

class AppController: Initializable {
    @FXML
    lateinit var diceComboBox: ComboBox<String>

    @FXML
    lateinit var diceDisplayArea: ImageView

    @FXML
    lateinit var previousNumberLabel: Label

    @FXML
    lateinit var rollBtn: Button

    @FXML
    lateinit var clearBtn: Button

    private lateinit var diceCount: Number
    private lateinit var clock: Roller

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        clock = Roller()

        diceComboBox.items.addAll(
            "1 Die", "2 Dice", "3 Dice"
        )

        diceComboBox.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
            diceCount = (newValue as Int) + 1
            when (diceCount as Int) {
                1 -> {
                    // showing a random dice
                    clock.previousNumber = (1..6).random()
                    diceDisplayArea.image = diceImages[(clock.previousNumber as Int) - 1]
                    rollBtn.isDisable = false
                }
                2 -> {
                    alertUser()
                    diceComboBox.value = "1 Die"
                    diceCount = 1
                }
                3 -> {
                    alertUser()
                    diceComboBox.value = "1 Die"
                    diceCount = 1
                }
                else -> println("Error.")
            }
        }

        rollBtn.setOnAction {
            diceRollingAnimation()
        }

        clearBtn.setOnAction {
            this.clearOptions()
        }
    }

    inner class Roller : AnimationTimer() {
        private val FRAMES_PER_SEC = 24L
        private val INTERVAL = 1000000000L / FRAMES_PER_SEC
        private val MAX_ROLLS = 12

        private var last = 0L
        private var count = 0L

        var previousNumber: Number = -1
        private var currentNumber: Number = -1
            get() {
                if (field != -1) {
                    previousNumber = (field as Int) + 1
                }
                return field
            }

        override fun handle(now: Long) {
            if (now - last > INTERVAL) {
                currentNumber = (0..5).random()

                diceDisplayArea.image = diceImages[currentNumber as Int]
                last = now
                count++
                if (count > MAX_ROLLS) {
                    clock.stop()
                    count = 0
                    disableButtons(false)
                    if (currentNumber != -1) {
                        previousNumber = (currentNumber as Int) + 1
                    }
                }
            }
        }
    }

    private fun diceRollingAnimation() {
        if (clock.previousNumber != -1)
            previousNumberLabel.text = "Previous: ${clock.previousNumber}"
        clock.start()
        disableButtons(true)
    }

    private fun clearOptions() {
        diceComboBox.value = null
        diceDisplayArea.image = null
        clearBtn.isDisable = true
        previousNumberLabel.text = "Previous: N/A"
    }

    private fun disableButtons(value: Boolean) {
        clearBtn.isDisable = value
        rollBtn.isDisable = value
    }
}

fun alertUser() {
    val alert = Alert(Alert.AlertType.INFORMATION)
    alert.title = "Not yet implemented yet."
    alert.contentText = "This part of the app is not ready yet."
    alert.show()
}