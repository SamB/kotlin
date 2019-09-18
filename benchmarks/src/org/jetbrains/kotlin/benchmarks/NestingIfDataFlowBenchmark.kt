/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.benchmarks

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
open class NestingIfDataFlowBenchmark : AbstractSimpleFileBenchmark() {
    @Param("1", "10", "100", "1000", "3000", "5000", "7000", "10000")
    private var size: Int = 0

    @Benchmark
    fun benchmark(bh: Blackhole) {
        analyzeGreenFile(bh)
    }

    override fun buildText(): String = buildString {
        for (i in 1..size) {
            appendln("interface A$i {")
            appendln("    fun foo$i() {}")
            appendln("}")
        }
        appendln("fun foo(x: Any) {")
        for (i in 1..size) {
            appendln("if (x is A$i) {")
        }
        for (i in 1..size) {
            appendln("x.foo$i()")
        }
        for (i in 1..size) {
            appendln("}")
        }
        appendln("}")
    }

}