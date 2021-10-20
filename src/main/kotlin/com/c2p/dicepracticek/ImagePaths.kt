package com.c2p.dicepracticek

import javafx.scene.image.Image

import java.io.FileInputStream

const val basePath = "/mnt/NewVolume/Code/InternshalaTrainings/DicePracticeK/src/main/resources/com/c2p/dicepracticek/DiceImages/"
const val path1 = "${com.c2p.dicepracticek.basePath}1.png"
const val path2 = "${com.c2p.dicepracticek.basePath}2.png"
const val path3 = "${com.c2p.dicepracticek.basePath}3.png"
const val path4 = "${com.c2p.dicepracticek.basePath}4.png"
const val path5 = "${com.c2p.dicepracticek.basePath}5.png"
const val path6 = "${com.c2p.dicepracticek.basePath}6.png"

const val SIDE_VALUE = 5.0

val diceImages = listOf(
    Image(FileInputStream(path1)),
    Image(FileInputStream(path2)),
    Image(FileInputStream(path3)),
    Image(FileInputStream(path4)),
    Image(FileInputStream(path5)),
    Image(FileInputStream(path6))
)