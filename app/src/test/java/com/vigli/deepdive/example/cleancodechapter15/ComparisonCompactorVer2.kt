package com.vigli.deepdive.example.cleancodechapter15

import junit.framework.Assert
import java.lang.StringBuilder

class ComparisonCompactorVer2(
    private val contextLength: Int,
    private val expected: String?,
    private val actual: String?
) {
    private var prefixLength = 0
    private var suffixLength = 0

    fun formatCompactedComparison(message: String?): String {
        var compactExpected = expected
        var compactActual = actual
        if (shouldBeCompacted()) {
            findCommonPrefixAndSuffix()
            compactExpected = compact(expected!!)
            compactActual = compact(actual!!)
        }
        return Assert.format(message, compactExpected, compactActual)
    }

    private fun shouldBeCompacted(): Boolean = !shouldNotBeCompacted()

    private fun shouldNotBeCompacted(): Boolean = expected == null ||
            actual == null ||
            expected == actual

    private fun findCommonPrefixAndSuffix() {
        findCommonPrefix()
        suffixLength = 0
        while (!suffixOverlapsPrefix(suffixLength)) {
            if (charFromEnd(expected!!, suffixLength) !=
                charFromEnd(actual!!, suffixLength)
            ) break
            suffixLength++
        }
    }

    private fun charFromEnd(s: String, i: Int): Char = s[s.length - i - 1]

    private fun suffixOverlapsPrefix(suffixLength: Int): Boolean =
        actual!!.length - suffixLength <= prefixLength ||
                expected!!.length - suffixLength <= prefixLength

    private fun findCommonPrefix() {
        prefixLength = 0
        val end = expected!!.length.coerceAtMost(actual!!.length)
        while (prefixLength < end) {
            if (expected[prefixLength] != actual[prefixLength]) {
                break
            }
            prefixLength++
        }
    }

    private fun compact(s: String): String {
        return StringBuilder()
            .append(startingEllipsis())
            .append(startingContext())
            .append(DELTA_START)
            .append(delta(s))
            .append(DELTA_END)
            .append(endingContext())
            .append(endingEllipsis())
            .toString()
    }

    private fun startingEllipsis(): String =
        if (prefixLength > contextLength) ELLIPSIS else ""

    private fun startingContext(): String {
        val contextStart = 0.coerceAtLeast(prefixLength - contextLength)
        val contextEnd = prefixLength
        return expected!!.substring(contextStart, contextEnd)
    }

    private fun delta(s: String): String {
        val deltaStart = prefixLength
        val deltaEnd = s.length - suffixLength
        return s.substring(deltaStart, deltaEnd)
    }

    private fun endingContext(): String {
        val contextStart = expected!!.length - suffixLength
        val contextEnd = (contextStart + contextLength).coerceAtMost(expected.length)
        return expected.substring(contextStart, contextEnd)
    }

    private fun endingEllipsis(): String {
        return if (suffixLength > contextLength) ELLIPSIS else ""
    }

    companion object {
        private const val ELLIPSIS = "..."
        private const val DELTA_END = "]"
        private const val DELTA_START = "["
    }
}