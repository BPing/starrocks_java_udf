package com.starrocks.udf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TDigestHashTest {

    @Test
    void testEvaluateWithValidValue() {
        TDigestHash tDigestHash = new TDigestHash();
        String result = tDigestHash.evaluate(10.5);
        assertNotNull(result, "Result should not be null for a valid input value.");
    }

    @Test
    void testEvaluateWithNullValue() {
        TDigestHash tDigestHash = new TDigestHash();
        String result = tDigestHash.evaluate(null);
        assertNull(result, "Result should be null when input is null.");
    }

    @Test
    void testEvaluateWithExtremeValue() {
        TDigestHash tDigestHash = new TDigestHash();
        String result = tDigestHash.evaluate(Double.MAX_VALUE);
        assertNotNull(result, "Result should not be null for an extreme input value.");
    }
}
