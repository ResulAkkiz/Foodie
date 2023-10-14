package com.project.foodie.utils

fun String.getImage(): String = "http://kasimadalan.pe.hu/yemekler/resimler/$this"
fun String.getStar(): String = Constants.starMap[this].toString()
fun String.getLocation(): String = Constants.districtMap[this].toString()
fun String.getDescription(): String = Constants.descriptionMap[this].toString()