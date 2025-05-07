package com.starrocks.udf;

import com.starrocks.common.TDigestUtil;
import com.tdunning.math.stats.TDigest;

import java.nio.ByteBuffer;
import java.util.Base64;

/**
 * TDigestHash 计算给定数值的 TDigest 的基础结构
 */
public class TDigestHash {
    public final String evaluate(Double val) {
        if (val == null) return null;
        TDigest digest = TDigestUtil.createDigest();
        digest.add(val);
        return TDigestUtil.convertToVarchar(digest);
    }

}
