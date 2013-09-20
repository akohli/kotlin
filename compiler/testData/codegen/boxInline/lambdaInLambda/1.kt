import test.*
import kotlin.io.*
import kotlin.util.*
import java.io.*
import java.util.*


fun sample(): Input {
    return Input("Hello", "World");
}

test fun testForEachLine() {
    val list = ArrayList<String>()
    val reader = sample()

    reader.forEachLine{
        list.add("111")
    }
}


fun box(): String {
    testForEachLine()

    return "OK"
}
