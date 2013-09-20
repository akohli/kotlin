import test.*
import kotlin.io.*
import kotlin.util.*
import java.io.*
import java.util.*

fun test1(s: Long): String {
    return doSmth(s)
}

fun box(): String {
    val result = test1(11.toLong())
    if (result != "11") return "fail1: ${result}"

    return "OK"
}