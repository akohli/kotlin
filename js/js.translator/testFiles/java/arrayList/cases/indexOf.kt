package foo

import java.util.ArrayList;

// TODO: drop when listOf will be available here.
fun listOf<T>(vararg a: T): List<T> {
    val list = ArrayList<T>();

    for (e in a) {
        list.add(e)
    }

    return list
}

 fun box(): String {
     val list = listOf(3, "2", -1, null, 0, "2", -1, null,  0,   8)
     val data = listOf(3, "2", -1, null, 0,   8,  5,  "3", 77, -15)
     val indexes  = listOf(4, 1,  6, 0,  7, 5,  9, 3,  8, 2)
     val inList = 0..5

     for (i in indexes) {
         val expected =
                 if (i == inList.start) 0
                 else if (i == inList.end) list.size() - 1
                 else if (i in inList ) i
                 else -1

         val actual = list.indexOf(data[i])
         if (actual != expected) return "indexOf failed when find: ${data[i]}, expected: $expected, actual: $actual, data[$i]=${data[i]}, list: $list"
     }

     for (i in indexes) {
         val expected =
                 if (i == inList.start) 0
                 else if (i == inList.end) list.size() - 1
                 else if (i in inList ) i + inList.end - 1
                 else -1

         val actual = list.lastIndexOf(data[i])
         if (actual != expected) return "lastIndexOf failed when find: ${data[i]}, expected: $expected, actual: $actual, data[$i]=${data[i]}, list: $list"
     }

     return "OK"
 }