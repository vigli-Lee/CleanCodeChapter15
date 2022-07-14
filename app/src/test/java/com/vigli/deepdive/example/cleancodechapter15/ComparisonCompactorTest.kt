package com.vigli.deepdive.example.cleancodechapter15

import junit.framework.TestCase

class ComparisonCompactorTest : TestCase() {

    fun testResultOk() {
        val success = ComparisonCompactor(0, "b", "b").compact(null)
        assertTrue("expected:<b> but was:<b>" == success)
    }

    fun testMessage() {
        val failure = ComparisonCompactor(0, "b", "c").compact("a")
        assertTrue("a expected:<[b]> but was:<[c]>" == failure)
    }

    fun testStartSame() {
        val failure = ComparisonCompactor(1, "ba", "bc").compact(null)
        assertEquals("expected:<b[a]> but was:<b[c]>", failure)
    }

    fun testEndSame() {
        val failure = ComparisonCompactor(1, "ab", "cb").compact(null)
        assertEquals("expected:<[a]b> but was:<[c]b>", failure)
    }

    fun testSame() {
        val failure = ComparisonCompactor(1, "ab", "ab").compact(null)
        assertEquals("expected:<ab> but was:<ab>", failure)
    }

    fun testNoContextStartAndEndSame() {
        val failure = ComparisonCompactor(0, "abc", "adc").compact(null)
        assertEquals("expected:<...[b]...> but was:<...[d]...>", failure)
    }

    fun testStartAndEndContext() {
        val failure = ComparisonCompactor(1, "abc", "adc").compact(null)
        assertEquals("expected:<a[b]c> but was:<a[d]c>", failure)
    }

    fun testStartAndEndContextWithEllipses() {
        val failure = ComparisonCompactor(1, "abcde", "abfde").compact(null)
        assertEquals("expected:<...b[c]d...> but was:<...b[f]d...>", failure)
    }

    fun testComparisonErrorStartSameComplete() {
        val failure = ComparisonCompactor(2, "ab", "abc").compact(null)
        assertEquals("expected:<ab[]> but was:<ab[c]>", failure)
    }

    fun testComparisonErrorEndSameComplete() {
        val failure = ComparisonCompactor(0, "bc", "abc").compact(null)
        assertEquals("expected:<[]...> but was:<[a]...>", failure)
    }

    fun testComparisonErrorEndSameCompleteContext() {
        val failure = ComparisonCompactor(2, "bc", "abc").compact(null)
        assertEquals("expected:<[]bc> but was:<[a]bc>", failure)
    }

    fun testComparisonErrorOverlappingMatches() {
        val failure = ComparisonCompactor(0, "abc", "abbc").compact(null)
        assertEquals("expected:<...[]...> but was:<...[b]...>", failure)
    }

    fun testComparisonErrorOverlappingMatchesContext() {
        val failure = ComparisonCompactor(2, "abc", "abbc").compact(null)
        assertEquals("expected:<ab[]c> but was:<ab[b]c>", failure)
    }

    fun testComparisonErrorOverlappingMatches2() {
        val failure = ComparisonCompactor(0, "abcdde", "abcde").compact(null)
        assertEquals("expected:<...[d]...> but was:<...[]...>", failure)
    }

    fun testComparisonErrorOverlappingMatches2Context() {
        val failure = ComparisonCompactor(2, "abcdde", "abcde").compact(null)
        assertEquals("expected:<...cd[d]e> but was:<...cd[]e>", failure)
    }

    fun testComparisonErrorWithActualNull() {
        val failure = ComparisonCompactor(0, "a", null).compact(null)
        assertEquals("expected:<a> but was:<null>", failure)
    }

    fun testComparisonErrorWithActualNullContext() {
        val failure = ComparisonCompactor(2, "a", null).compact(null)
        assertEquals("expected:<a> but was:<null>", failure)
    }

    fun testComparisonErrorWithExpectedNull() {
        val failure = ComparisonCompactor(0, null, "a").compact(null)
        assertEquals("expected:<null> but was:<a>", failure)
    }

    fun testComparisonErrorWithExpectedNullContext() {
        val failure = ComparisonCompactor(2, null, "a").compact(null)
        assertEquals("expected:<null> but was:<a>", failure)
    }

    fun testBug609972() {
        val failure = ComparisonCompactor(10, "S&P500", "0").compact(null)
        assertEquals("expected:<[S&P50]0> but was:<[]0>", failure)
    }
}