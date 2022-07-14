package com.vigli.deepdive.example.cleancodechapter15

import junit.framework.Assert

class ComparisonCompactorVer1(
    private val contextLength: Int,
    private val expected: String?,
    private val actual: String?
) {

    private var prefixIndex = 0
    private var suffixLength = 0

    private lateinit var compactExpected: String
    private lateinit var compactActual: String

    private fun canBeCompacted(): Boolean = expected != null &&
            actual != null &&
            !areStringsEqual()

    fun compact(message: String?): String {
        if (canBeCompacted()) {
            formatCompactedComparison()
            return Assert.format(message, compactExpected, compactActual)
        }

        return Assert.format(message, expected, actual)
    }

    private fun formatCompactedComparison() {
        findCommonPrefixAndSuffix()

        compactExpected = compactString(expected!!)
        compactActual = compactString(actual!!)
    }

    private fun findCommonPrefixAndSuffix() {
        findCommonPrefix()

        suffixLength = 0
        while (!suffixOverlapsPrefix(suffixLength)) {
           if (charFromEnd(expected!!, suffixLength) !=
               charFromEnd(actual!!, suffixLength)) break
            suffixLength++
        }
    }

    private fun charFromEnd(s: String, i: Int): Char {
        return s[s.length - i - 1]
    }

    private fun suffixOverlapsPrefix(suffixLength: Int): Boolean {
        return actual!!.length - suffixLength <= prefixIndex ||
                expected!!.length - suffixLength <=  prefixIndex
    }

    private fun compactString(source: String): String {
        return computeCommonPrefix() +
                DELTA_START +
                source.substring(prefixIndex, source.length - suffixLength) +
                DELTA_END +
                computeCommonSuffix()
    }

    private fun findCommonPrefix() {
        prefixIndex = 0
        val end = Math.min(expected!!.length, actual!!.length)
        while (prefixIndex < end) {
            if (expected[prefixIndex] != actual[prefixIndex]) {
                break
            }
            prefixIndex++
        }
    }

    private fun computeCommonPrefix(): String {
        return (if (prefixIndex > contextLength) ELLIPSIS else "") + expected!!.substring(
            0.coerceAtLeast(prefixIndex - contextLength), prefixIndex
        )
    }

    private fun computeCommonSuffix(): String {
        val end = Math.min(expected!!.length - suffixLength + contextLength, expected.length)
        return expected.substring(
            expected.length - suffixLength,
            end
        ) + if (expected.length - suffixLength < expected.length - contextLength) ELLIPSIS else ""
    }

    private fun areStringsEqual(): Boolean {
        return expected == actual
    }

    companion object {
        private const val ELLIPSIS = "..."
        private const val DELTA_END = "]"
        private const val DELTA_START = "["
    }
}