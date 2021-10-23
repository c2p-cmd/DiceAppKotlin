package com.c2p.dicepracticek

import javafx.animation.AnimationTimer
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.image.ImageView

import java.net.URL
import java.util.*

const val FRAMES_PER_SEC = 24L
const val INTERVAL = 1000000000L / FRAMES_PER_SEC
const val MAX_ROLLS = 12

class AppController: Initializable {
    @FXML
    lateinit var diceComboBox: ComboBox<String>

    @FXML
    lateinit var diceDisplayAreaMid: ImageView

    @FXML
    lateinit var diceDisplayAreaLeft: ImageView

    @FXML
    lateinit var diceDisplayAreaRight: ImageView

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
            "1 Die", "2 Dice"
        )

        diceComboBox.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
            diceCount = (newValue as Int) + 1
            when (diceCount as Int) {
                1 -> {
                    // showing a random dice
                    clock.previousNumber = (1..6).random()

                    diceDisplayAreaMid.image = diceImages[(clock.previousNumber as Int) - 1]
                    diceDisplayAreaLeft.image = null
                    diceDisplayAreaRight.image = null

                    previousNumberLabel.text = "Previous: N/A"
                }
                2 -> {
                    clock.previousPair = Pair(
                        first = (1..6).random(),
                        second = (1..6).random()
                    )

                    diceDisplayAreaLeft.image = diceImages[clock.previousPair.first - 1]
                    diceDisplayAreaMid.image = null
                    diceDisplayAreaRight.image = diceImages[clock.previousPair.second - 1]

                    previousNumberLabel.text = "Previous: N/A"
                }
                else -> println("Error.")
            }
            rollBtn.isDisable = false
        }

        rollBtn.setOnAction {
            diceRollingAnimation()
        }

        clearBtn.setOnAction {
            this.clearOptions()
        }
    }

    inner class Roller : AnimationTimer() {
        private var last = 0L
        private var count = 0L

        var previousPair: Pair<Int, Int> = Pair(-1, -1)
        private var currentPair: Pair<Int, Int> = Pair(-1, -1)
            get() {
                if (field != Pair(-1, -1)) {
                    previousPair = field + Pair(1,1)
                }
                return field
            }

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
                when (diceCount) {
                    1 -> {
                        currentNumber = (0..5).random()
                        diceDisplayAreaMid.image = diceImages[currentNumber as Int]
                    }
                    2 -> {
                        val n1 = (0..5).random()
                        val n2 = (0..5).random()
                        currentPair = Pair(n1, n2)
                        diceDisplayAreaLeft.image = diceImages[n1]
                        diceDisplayAreaRight.image = diceImages[n2]
                    }
                }

                // break condition
                last = now
                count++
                if (count > MAX_ROLLS) {
                    // stopping animation
                    clock.stop()
                    count = 0

                    // re-enabling buttons
                    disableButtons(false)

                    // updating the values of labels
                    if (currentNumber != -1) {
                        previousNumber = (currentNumber as Int) + 1
                    }
                    if (currentPair != Pair(-1, -1)) {
                        previousPair = Pair(
                            currentPair.first+1,
                            currentPair.second+1
                        )
                    }
                }
            }
        }
    }

    private fun diceRollingAnimation() {
        if (clock.previousNumber != -1 && diceCount == 1)
            previousNumberLabel.text = "Previous: ${clock.previousNumber}"
        else if (clock.previousPair != Pair(-1, -1) && diceCount == 2)
            previousNumberLabel.text = "Previous: ${clock.previousPair.first}, ${clock.previousPair.second}"

        clock.start()
        disableButtons(true)
    }

    private fun clearOptions() {
        diceComboBox.value = null

        diceDisplayAreaLeft.image = null
        diceDisplayAreaMid.image = null
        diceDisplayAreaRight.image = null

        clearBtn.isDisable = true

        previousNumberLabel.text = "Previous: N/A"
    }

    private fun disableButtons(value: Boolean) {
        clearBtn.isDisable = value
        rollBtn.isDisable = value
    }

    operator fun Pair<Int, Int>.plus(
        otherPair: Pair<Int, Int>
    ): Pair<Int, Int> =
        Pair(
            first=this.first+otherPair.first,
            second=this.second+otherPair.second
        )
}

/*
fun alertUser() {
    val alert = Alert(Alert.AlertType.INFORMATION)
    alert.title = "Not yet implemented."
    alert.contentText = "This part of the app is not ready yet."
    alert.show()
}
*/