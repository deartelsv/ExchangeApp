package ru.artelsv.exchangeapp.utils

fun <A, B> Map<A, B>.take(n: Int) = toList().take(n).toMap()