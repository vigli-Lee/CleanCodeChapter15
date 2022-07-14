package com.vigli.deepdive.example.cleancodechapter15

import junit.framework.TestCase

class ComparisonCompactorTest : TestCase() {

    fun testResultOk() {
        val success = ComparisonCompactorVer2(0, "b", "b")
            .formatCompactedComparison(null)
        assertTrue("expected:<b> but was:<b>" == success)
    }

    fun testMessage() {
        val failure = ComparisonCompactorVer2(0, "b", "c")
            .formatCompactedComparison("a")
        assertTrue("a expected:<[b]> but was:<[c]>" == failure)
    }

    fun testStartSame() {
        val failure = ComparisonCompactorVer2(1, "ba", "bc")
            .formatCompactedComparison(null)
        assertEquals("expected:<b[a]> but was:<b[c]>", failure)
    }

    fun testEndSame() {
        val failure = ComparisonCompactorVer2(1, "ab", "cb")
            .formatCompactedComparison(null)
        assertEquals("expected:<[a]b> but was:<[c]b>", failure)
    }

    fun testSame() {
        val failure = ComparisonCompactorVer2(1, "ab", "ab")
            .formatCompactedComparison(null)
        assertEquals("expected:<ab> but was:<ab>", failure)
    }

    fun testNoContextStartAndEndSame() {
        val failure = ComparisonCompactorVer2(0, "abc", "adc")
            .formatCompactedComparison(null)
        assertEquals("expected:<...[b]...> but was:<...[d]...>", failure)
    }

    fun testStartAndEndContext() {
        val failure = ComparisonCompactorVer2(1, "abc", "adc")
            .formatCompactedComparison(null)
        assertEquals("expected:<a[b]c> but was:<a[d]c>", failure)
    }

    fun testStartAndEndContextWithEllipses() {
        val failure = ComparisonCompactorVer2(1, "abcde", "abfde")
            .formatCompactedComparison(null)
        assertEquals("expected:<...b[c]d...> but was:<...b[f]d...>", failure)
    }

    fun testComparisonErrorStartSameComplete() {
        val failure = ComparisonCompactorVer2(2, "ab", "abc")
            .formatCompactedComparison(null)
        assertEquals("expected:<ab[]> but was:<ab[c]>", failure)
    }

    fun testComparisonErrorEndSameComplete() {
        val failure = ComparisonCompactorVer2(0, "bc", "abc")
            .formatCompactedComparison(null)
        assertEquals("expected:<[]...> but was:<[a]...>", failure)
    }

    fun testComparisonErrorEndSameCompleteContext() {
        val failure = ComparisonCompactorVer2(2, "bc", "abc")
            .formatCompactedComparison(null)
        assertEquals("expected:<[]bc> but was:<[a]bc>", failure)
    }

    fun testComparisonErrorOverlappingMatches() {
        val failure = ComparisonCompactorVer2(0, "abc", "abbc")
            .formatCompactedComparison(null)
        assertEquals("expected:<...[]...> but was:<...[b]...>", failure)
    }

    fun testComparisonErrorOverlappingMatchesContext() {
        val failure = ComparisonCompactorVer2(2, "abc", "abbc")
            .formatCompactedComparison(null)
        assertEquals("expected:<ab[]c> but was:<ab[b]c>", failure)
    }

    fun testComparisonErrorOverlappingMatches2() {
        val failure = ComparisonCompactorVer2(0, "abcdde", "abcde")
            .formatCompactedComparison(null)
        assertEquals("expected:<...[d]...> but was:<...[]...>", failure)
    }

    fun testComparisonErrorOverlappingMatches2Context() {
        val failure = ComparisonCompactorVer2(2, "abcdde", "abcde")
            .formatCompactedComparison(null)
        assertEquals("expected:<...cd[d]e> but was:<...cd[]e>", failure)
    }

    fun testComparisonErrorWithActualNull() {
        val failure = ComparisonCompactorVer2(0, "a", null)
            .formatCompactedComparison(null)
        assertEquals("expected:<a> but was:<null>", failure)
    }

    fun testComparisonErrorWithActualNullContext() {
        val failure = ComparisonCompactorVer2(2, "a", null)
            .formatCompactedComparison(null)
        assertEquals("expected:<a> but was:<null>", failure)
    }

    fun testComparisonErrorWithExpectedNull() {
        val failure = ComparisonCompactorVer2(0, null, "a")
            .formatCompactedComparison(null)
        assertEquals("expected:<null> but was:<a>", failure)
    }

    fun testComparisonErrorWithExpectedNullContext() {
        val failure = ComparisonCompactorVer2(2, null, "a")
            .formatCompactedComparison(null)
        assertEquals("expected:<null> but was:<a>", failure)
    }

    fun testBug609972() {
        val failure = ComparisonCompactorVer2(10, "S&P500", "0")
            .formatCompactedComparison(null)
        assertEquals("expected:<[S&P50]0> but was:<[]0>", failure)
    }
}