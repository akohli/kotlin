// "class com.intellij.codeInsight.daemon.impl.quickfix.ImportClassFixBase" "false"

package Teting

class Some() {
//    fun get(i : Int) : Int {
//        return i
//    }
}

fun main(args : Array<String>) {
    val some = Some()
    // Nothing should be changed
    <caret>some[12]
}