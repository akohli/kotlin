package templates

import templates.Family.*

fun mapping(): List<GenericFunction> {
    val templates = arrayListOf<GenericFunction>()

    templates add f("map(transform : (T) -> R)") {
        doc { "Returns a list containing the results of applying the given *transform* function to each element of the original collection" }
        typeParam("R")
        returns("List<R>")
        body {
            "return mapTo(ArrayList<R>(), transform)"
        }

        returns(Streams) { "Stream<R>" }
        doc(Streams) { "Returns a stream containing the results of applying the given *transform* function to each element of the original stream" }
        body(Streams) {
            "return TransformingStream(this, transform) "
        }

    }

    templates add f("mapTo(collection: C, transform : (T) -> R)") {
        doc {
            """
            Appends transformed elements of original collection using the given *transform* function
            to the given *collection*
            """
        }
        typeParam("R")
        typeParam("C: MutableCollection<in R>")
        returns("C")

        body {
            """
                for (item in this)
                    collection.add(transform(item))
                return collection
            """
        }
    }

    templates add f("flatMap(transform: (T)-> Iterable<R>)") {
        exclude(Streams)
        doc { "Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection" }
        typeParam("R")
        returns("List<R>")
        body {
            "return flatMapTo(ArrayList<R>(), transform)"
        }
    }

    templates add f("flatMap(transform: (T)-> Stream<R>)") {
        only(Streams)
        doc { "Returns a single stream of all elements streamed from results of *transform* function being invoked on each element of original stream" }
        typeParam("R")
        returns("Stream<R>")
        body {
            "return FlatteningStream(this, transform)"
        }
    }

    templates add f("flatMapTo(collection: C, transform: (T) -> Iterable<R>)") {
        exclude(Streams)
        doc { "Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *collection*" }
        typeParam("R")
        typeParam("C: MutableCollection<in R>")
        returns("C")
        body {
            """
                for (element in this) {
                    val list = transform(element)
                    collection.addAll(list)
                }
                return collection
            """
        }
    }

    templates add f("flatMapTo(collection: C, transform: (T) -> Stream<R>)") {
        only(Streams)
        doc { "Appends all elements yielded from results of *transform* function being invoked on each element of original stream, to the given *collection*" }
        typeParam("R")
        typeParam("C: MutableCollection<in R>")
        returns("C")
        body {
            """
                for (element in this) {
                    val list = transform(element)
                    collection.addAll(list)
                }
                return collection
            """
        }
    }

    templates add f("groupBy(toKey: (T) -> K)") {
        doc { "Returns a map of the elements in original collection grouped by the result of given *toKey* function" }
        typeParam("K")
        returns("Map<K, List<T>>")
        body { "return groupByTo(HashMap<K, MutableList<T>>(), toKey)" }
    }

    templates add f("groupByTo(map: MutableMap<K, MutableList<T>>, toKey: (T) -> K)") {
        typeParam("K")
        doc { "Appends elements from original collection grouped by the result of given *toKey* function to the given *map*" }
        returns("Map<K, MutableList<T>>")
        body {
            """
                for (element in this) {
                    val key = toKey(element)
                    val list = map.getOrPut(key) { ArrayList<T>() }
                    list.add(element)
                }
                return map
            """
        }
    }
    return templates
}