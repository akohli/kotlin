package templates

import java.util.ArrayList
import templates.Family.*

fun guards(): List<GenericFunction> {
    val THIS = "\$this"

    val templates = ArrayList<GenericFunction>()

    templates add f("requireNoNulls()") {
        include(Lists)
        exclude(ArraysOfPrimitives)
        doc { "Returns an original collection containing all the non-*null* elements, throwing an [[IllegalArgumentException]] if there are any null elements" }
        typeParam("T:Any")
        toNullableT = true
        returns("SELF")
        body {
            """
            for (element in this) {
                if (element == null) {
                    throw IllegalArgumentException("null element found in $THIS")
                }
            }
            return this as SELF
            """
        }
        body(Streams) {
            """
            return FilteringStream(this) {
                if (it == null) {
                    throw IllegalArgumentException("null element found in $THIS")
                }
                true
            } as Stream<T>
            """
        }
    }

    return templates
}