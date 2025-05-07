package com.starrocks.common;

import com.tdunning.math.stats.AVLTreeDigest;
import com.tdunning.math.stats.TDigest;

import java.nio.ByteBuffer;
import java.util.Base64;

public class TDigestUtil {

    public static TDigest createDigest() {
        // 压缩参数100
        return new AVLTreeDigest(100);
    }

    public static void asBytes(TDigest digest, ByteBuffer buf) {
        digest.asSmallBytes(buf);
    }

    public static int byteSize(TDigest digest) {
        return digest.smallByteSize();
    }

    public static String convertToVarchar(TDigest digest) {
        if (digest == null) return null;
        try {
            byte[] bytes = new byte[byteSize(digest)];
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            asBytes(digest,buffer);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static TDigest convertFromVarchar(String value) {
        if (value != null) {
            // 解码回来
            byte[] decoded = Base64.getDecoder().decode(value);
            ByteBuffer bufferFromDecoded = ByteBuffer.wrap(decoded);
            TDigest restored = AVLTreeDigest.fromBytes(bufferFromDecoded);
            return restored;
        }
        return null;
    }
}
