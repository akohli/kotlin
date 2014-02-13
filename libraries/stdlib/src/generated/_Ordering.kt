package kotlin

//
// NOTE THIS FILE IS AUTO-GENERATED by the GenerateStandardLib.kt
// See: https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib
//

import java.util.*

/**
 * Returns a list with elements in reversed order
 */
public fun <T> Array<out T>.reverse() : List<T> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun BooleanArray.reverse() : List<Boolean> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun ByteArray.reverse() : List<Byte> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun CharArray.reverse() : List<Char> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun DoubleArray.reverse() : List<Double> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun FloatArray.reverse() : List<Float> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun IntArray.reverse() : List<Int> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun LongArray.reverse() : List<Long> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun ShortArray.reverse() : List<Short> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a list with elements in reversed order
 */
public fun <T> Iterable<T>.reverse() : List<T> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
    
}

/**
 * Returns a sorted list of all elements
 */
public fun <T: Comparable<T>> Iterable<T>.sort() : List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = comparator<T> {(x: T, y: T) -> x.compareTo(y)}
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
    
}

/**
 * Returns a list of all elements, sorted by the specified *comparator*
 */
public fun <T> Array<out T>.sortBy(comparator : Comparator<T>) : List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList, comparator)
    return sortedList
    
}

/**
 * Returns a list of all elements, sorted by the specified *comparator*
 */
public fun <T> Iterable<T>.sortBy(comparator : Comparator<T>) : List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList, comparator)
    return sortedList
    
}

/**
 * Returns a list of all elements, sorted by results of specified *order* function.
 */
public inline fun <T, R: Comparable<R>> Array<out T>.sortBy(order: (T) -> R) : List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = comparator<T> {(x: T, y: T) -> order(x).compareTo(order(y))}
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
    
}

/**
 * Returns a list of all elements, sorted by results of specified *order* function.
 */
public inline fun <T, R: Comparable<R>> Iterable<T>.sortBy(order: (T) -> R) : List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = comparator<T> {(x: T, y: T) -> order(x).compareTo(order(y))}
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
    
}

/**
 * Returns a sorted list of all elements
 */
public fun <T: Comparable<T>> Iterable<T>.sortDescending() : List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = comparator<T> {(x: T, y: T) -> -x.compareTo(y)}
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
    
}

/**
 * Returns a list of all elements, sorted by results of specified *order* function.
 */
public inline fun <T, R: Comparable<R>> Array<out T>.sortDescendingBy(order: (T) -> R) : List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = comparator<T> {(x: T, y: T) -> -order(x).compareTo(order(y))}
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
    
}

/**
 * Returns a list of all elements, sorted by results of specified *order* function.
 */
public inline fun <T, R: Comparable<R>> Iterable<T>.sortDescendingBy(order: (T) -> R) : List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = comparator<T> {(x: T, y: T) -> -order(x).compareTo(order(y))}
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
    
}

