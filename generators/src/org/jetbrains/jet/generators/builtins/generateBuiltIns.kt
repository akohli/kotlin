/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.generators.builtins.generateBuiltIns

import org.jetbrains.jet.generators.builtins.functions.*
import org.jetbrains.jet.generators.builtins.iterators.*
import org.jetbrains.jet.generators.builtins.progressionIterators.*
import org.jetbrains.jet.generators.builtins.progressions.*
import org.jetbrains.jet.generators.builtins.ranges.*
import java.io.PrintWriter
import java.io.File

fun assertExists(file: File): Unit =
        if (!file.exists()) error("Output dir does not exist: ${file.getAbsolutePath()}")

val BUILT_INS_DIR = File("core/builtins/src/jet/")
val RUNTIME_JVM_DIR = File("core/runtime.jvm/src/jet/")

abstract class BuiltInsSourceGenerator(val out: PrintWriter) {
    protected abstract fun generateBody(): Unit

    final fun generate() {
        out.println(File("injector-generator/copyright.txt").readText())
        // Don't include generator class name in the message: these are built-in sources,
        // and we don't want to scare users with any internal information about our project
        out.println("// Auto-generated file. DO NOT EDIT!")
        out.println()
        out.println("package jet")
        out.println()

        generateBody()
    }
}

fun generateBuiltIns(generate: (File, (PrintWriter) -> BuiltInsSourceGenerator) -> Unit) {
    assertExists(BUILT_INS_DIR)
    assertExists(RUNTIME_JVM_DIR)

    for (kind in FunctionKind.values()) {
        generate(File(BUILT_INS_DIR, kind.getFileName())) { GenerateFunctions(it, kind) }
        generate(File(RUNTIME_JVM_DIR, kind.getImplFileName()), { GenerateFunctionsImpl(it, kind) })
    }

    generate(File(BUILT_INS_DIR, "Iterators.kt")) { GenerateIterators(it) }
    generate(File(BUILT_INS_DIR, "ProgressionIterators.kt")) { GenerateProgressionIterators(it) }
    generate(File(BUILT_INS_DIR, "Progressions.kt")) { GenerateProgressions(it) }
    generate(File(BUILT_INS_DIR, "Ranges.kt")) { GenerateRanges(it) }
}

fun main(args: Array<String>) {
    generateBuiltIns { file, generator ->
        println("generating $file")
        PrintWriter(file) use {
            generator(it).generate()
        }
    }
}
