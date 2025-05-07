package com.starrocks.udaf;

import com.starrocks.common.TDigestUtil;
import com.starrocks.udaf.TDigestUnion.State;
import com.tdunning.math.stats.AVLTreeDigest;
import com.tdunning.math.stats.TDigest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class TDigestUnionTest {

    private TDigestUnion uDAF;
    private State state;

    @BeforeEach
    void setUp() {
        uDAF = new TDigestUnion();
        state = uDAF.create(); // 初始化state
        state.digest.add(10);
    }

    @Test
    void testCreateAndDestroy() {
        assertNotNull(state);
        assertNotNull(state.digest, "digest should be initialized");

        uDAF.destroy(state);
        assertNull(state.digest, "digest should be null after destroy");
    }

    @Test
    void testUpdate() throws Exception {
        // 模拟Base64编码的输入
        TDigest other = TDigest.createAvlTreeDigest(100);
        other.add(300);

        String encoded = TDigestUtil.convertToVarchar(other);

        uDAF.update(state, encoded); // 更新到当前状态
        assertEquals(300, state.digest.getMax(), "merged digest should contain the added value");
    }

    @Test
    void testSerialize() throws Exception {
        state.digest.add(5);
        ByteBuffer buffer = ByteBuffer.allocate(TDigestUtil.byteSize(state.digest));
        uDAF.serialize(state, buffer); // 序列化

        buffer.flip();
        TDigest restored = AVLTreeDigest.fromBytes(buffer);
        assertEquals(2, restored.size(), "restored digest should match original");
    }

    @Test
    void testMerge() throws Exception {
        TDigest other = TDigest.createAvlTreeDigest(100);
        other.add(20);

        ByteBuffer buffer = ByteBuffer.allocate(other.byteSize());
        other.asBytes(buffer);
        buffer.flip();

        uDAF.merge(state, buffer); // 合并
        assertEquals(20, state.digest.getMax(), "merged digest should contain the added value");
    }

    @Test
    void testFinalize() throws Exception {
        state.digest.add(30);
        String result = uDAF.finalize(state); // 最终输出

        assertNotNull(result, "finalized result should not be null");

        // 解码验证
        byte[] decoded = Base64.getDecoder().decode(result);
        ByteBuffer buffer = ByteBuffer.wrap(decoded);
        TDigest restored = AVLTreeDigest.fromBytes(buffer);
        assertEquals(30, restored.getMax(), "restored digest should match original");
    }

    @Test
    void testQuantile() throws Exception {
        state.digest.add(10);
        state.digest.add(20);
        state.digest.add(30);

        Double q50 = uDAF.quantile(state, 0.5);
        System.out.println(q50);
        assertNotNull(q50, "quantile should not be null");
        assertTrue(q50 >= 10 && q50 <= 30, "quantile should be within data range");
    }
}
