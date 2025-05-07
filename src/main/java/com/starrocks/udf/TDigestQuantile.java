package com.starrocks.udf;

import com.starrocks.common.TDigestUtil;
import com.tdunning.math.stats.TDigest;

/**
 * TDigestQuantile 计算给定数值的 TDigest 的分位数
 * 该类用于处理通过TDigest结构存储的大量数据的分位数计算
 */
public class TDigestQuantile {
    /**
     * 计算TDigest结构的分位数
     *
     * @param val 一个字符串，表示序列化的TDigest结构
     * @param q   分位数，一个介于0到1之间的双精度浮点数
     * @return 分位数对应的值，如果输入为空则返回null
     */
    public final Double evaluate(String val, Double q) {
        if (val == null) return null;
        TDigest digest = TDigestUtil.convertFromVarchar(val);
        return digest.quantile(q);
    }
}
