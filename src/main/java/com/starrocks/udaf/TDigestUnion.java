package com.starrocks.udaf;

import com.starrocks.common.TDigestUtil;
import com.tdunning.math.stats.AVLTreeDigest;
import com.tdunning.math.stats.TDigest;

import java.nio.ByteBuffer;
import java.io.IOException;

public class TDigestUnion {
    // 状态类（关键修改点：必须实现serializeLength）
    public static class State {
        TDigest digest;

        public int serializeLength() throws IOException {
            return TDigestUtil.byteSize(digest);
        }
    }

    // 创建状态
    public State create() {
        State state = new State();
        state.digest = TDigestUtil.createDigest();
        return state;
    }

    // 清理资源
    public void destroy(State state) {
        state.digest = null;
    }

    // 单行更新（对应Spark的update）
    public void update(State state, String value) {
        TDigest restored = TDigestUtil.convertFromVarchar(value);
        if (restored != null) {
            state.digest.add(restored);
        }
    }

    // 序列化（严格遵循示例格式）
    public void serialize(State state, ByteBuffer buff) throws IOException {
        TDigestUtil.asBytes(state.digest,buff);
    }

    // 跨节点合并（关键修改点：正确处理二进制合并）
    public void merge(State state, ByteBuffer buffer) throws IOException {
        TDigest other = AVLTreeDigest.fromBytes(buffer);
        state.digest.add(other);
    }

    // 最终结果计算
    public String finalize(State state) {
        return TDigestUtil.convertToVarchar(state.digest);
    }

    // 附加：支持动态分位数查询
    public Double quantile(State state, Double q) {
        return state.digest.quantile(q);
    }
}
