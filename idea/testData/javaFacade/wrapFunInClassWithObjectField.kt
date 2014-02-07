package test

class A<T> {
    fun foo(t: T) {}
}

class Test {
    val a = object: A {
        override fun foo() {}
    }

    fun <caret>foo() {}
}
