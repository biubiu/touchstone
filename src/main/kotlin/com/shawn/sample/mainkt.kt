package com.shawn.sample

fun main() {
    val a = 3
    val sumObj = ::sum
    println(sumObj(2, 3))
    println("abc")
}
fun m(a:Int) = a * a
fun sum(a:Int, b:Int): (Int) -> Int  {
  return ::m
}

fun identity(id: Int) = id
fun half(num: Int) = num / 2
fun zero(num: Int) = 0
fun generate(functionName: String): (Int) -> Int {
    return when(functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}