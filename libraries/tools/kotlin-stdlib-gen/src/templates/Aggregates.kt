package templates

import templates.Family.*

fun aggregates(): List<GenericFunction> {
    val templates = arrayListOf<GenericFunction>()

    templates add f("all(predicate: (T) -> Boolean)") {
        doc { "Returns *true* if all elements match the given *predicate*" }
        returns("Boolean")
        body {
            """
            for (element in this) if (!predicate(element)) return false
            return true
            """
        }
    }

    templates add f("none(predicate: (T) -> Boolean)") {
        doc { "Returns *true* if no elements match the given *predicate*" }
        returns("Boolean")
        body {
            """
            for (element in this) if (predicate(element)) return false
            return true
            """
        }
    }

    templates add f("any(predicate: (T) -> Boolean)") {
        doc { "Returns *true* if any element matches the given *predicate*" }
        returns("Boolean")
        body {
            """
            for (element in this) if (predicate(element)) return true
            return false
            """
        }
    }

    templates add f("count(predicate: (T) -> Boolean)") {
        doc { "Returns the number of elements matching the given *predicate*" }
        returns("Int")
        body {
            """
            var count = 0
            for (element in this) if (predicate(element)) count++
            return count
            """
        }
    }

    templates add f("count()") {
        doc { "Returns the number of elements" }
        returns("Int")
        body {
            """
            var count = 0
            for (element in this) count++
            return count
            """
        }
        body(Collections, ArraysOfObjects, ArraysOfPrimitives) {
            "return size"
        }
    }

    templates add f("min()") {
        doc { "Returns the smallest element or null if there are no elements" }
        returns("T?")
        exclude(PrimitiveType.Boolean)
        typeParam("T: Comparable<T>")
        body {
            """
            val iterator = iterator()
            if (!iterator.hasNext()) return null

            var min = iterator.next()
            while (iterator.hasNext()) {
                val e = iterator.next()
                if (min > e) min = e
            }
            return min
            """
        }
        body(ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (isEmpty()) return null
            var min = this[0]
            for (i in 1..lastIndex) {
                val e = this[i]
                if (min > e) min = e
            }
            return min
            """
        }
    }

    templates add f("minBy(f: (T) -> R)") {
        doc { "Returns the first element yielding the smallest value of the given function or null if there are no elements" }
        typeParam("R: Comparable<R>")
        typeParam("T: Any")
        returns("T?")
        body {
            """
            val iterator = iterator()
            if (!iterator.hasNext()) return null

            var minElem = iterator.next()
            var minValue = f(minElem)
            while (iterator.hasNext()) {
                val e = iterator.next()
                val v = f(e)
                if (minValue > v) {
                   minElem = e
                   minValue = v
                }
            }
            return minElem
            """
        }
        body(ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (size == 0) return null

            var minElem = this[0]
            var minValue = f(minElem)
            for (i in 1..lastIndex) {
                val e = this[i]
                val v = f(e)
                if (minValue > v) {
                   minElem = e
                   minValue = v
                }
            }
            return minElem
            """
        }
    }

    templates add f("max()") {
        doc { "Returns the largest element or null if there are no elements" }
        returns("T?")
        exclude(PrimitiveType.Boolean)
        typeParam("T: Comparable<T>")
        body {
            """
            val iterator = iterator()
            if (!iterator.hasNext()) return null

            var max = iterator.next()
            while (iterator.hasNext()) {
                val e = iterator.next()
                if (max < e) max = e
            }
            return max
            """
        }

        body(ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (isEmpty()) return null

            var max = this[0]
            for (i in 1..lastIndex) {
                val e = this[i]
                if (max < e) max = e
            }
            return max
            """
        }
    }

    templates add f("maxBy(f: (T) -> R)") {
        doc { "Returns the first element yielding the largest value of the given function or null if there are no elements" }
        typeParam("R: Comparable<R>")
        typeParam("T: Any")
        returns("T?")
        body {
            """
            val iterator = iterator()
            if (!iterator.hasNext()) return null

            var maxElem = iterator.next()
            var maxValue = f(maxElem)
            while (iterator.hasNext()) {
                val e = iterator.next()
                val v = f(e)
                if (maxValue < v) {
                   maxElem = e
                   maxValue = v
                }
            }
            return maxElem
            """
        }
        body(ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (isEmpty()) return null

            var maxElem = this[0]
            var maxValue = f(maxElem)
            for (i in 1..lastIndex) {
                val e = this[i]
                val v = f(e)
                if (maxValue < v) {
                   maxElem = e
                   maxValue = v
                }
            }
            return maxElem
            """
        }
    }



    templates add f("fold(initial: R, operation: (R, T) -> R)") {
        doc { "Accumulates value starting with *initial* value and applying *operation* from left to right to current accumulator value and each element" }
        typeParam("R")
        returns("R")
        body {
            """
            var accumulator = initial
            for (element in this) accumulator = operation(accumulator, element)
            return accumulator
            """
        }
    }

    templates add f("foldRight(initial: R, operation: (T, R) -> R)") {
        only(Lists, ArraysOfObjects, ArraysOfPrimitives)
        doc { "Accumulates value starting with *initial* value and applying *operation* from right to left to each element and current accumulator value" }
        typeParam("R")
        returns("R")
        body {
            """
            var index = size - 1
            var accumulator = initial
            while (index >= 0) {
                accumulator = operation(get(index--), accumulator)
            }
            return accumulator
            """
        }
    }


    templates add f("reduce(operation: (T, T) -> T)") {
        doc { "Accumulates value starting with the first element and applying *operation* from left to right to current accumulator value and each element" }
        returns("T")
        body {
            """
            val iterator = this.iterator()
            if (!iterator.hasNext()) throw UnsupportedOperationException("Empty iterable can't be reduced")

            var accumulator = iterator.next()
            while (iterator.hasNext()) {
                accumulator = operation(accumulator, iterator.next())
            }
            return accumulator
            """
        }
    }

    templates add f("reduceRight(operation: (T, T) -> T)") {
        only(Lists, ArraysOfObjects, ArraysOfPrimitives)
        doc { "Accumulates value starting with last element and applying *operation* from right to left to each element and current accumulator value" }
        returns("T")
        body {
            """
            var index = size - 1
            if (index < 0) throw UnsupportedOperationException("Empty iterable can't be reduced")

            var accumulator = get(index--)
            while (index >= 0) {
                accumulator = operation(get(index--), accumulator)
            }

            return accumulator
            """
        }
    }


    templates add f("forEach(operation: (T) -> Unit)") {
        doc { "Performs the given *operation* on each element" }
        returns("Unit")
        body {
            """
            for (element in this) operation(element)
            """
        }
    }

    return templates
}