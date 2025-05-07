package org.example;

import com.starrocks.common.TDigestUtil;
import com.tdunning.math.stats.AVLTreeDigest;
import com.tdunning.math.stats.TDigest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        TDigest digest=TDigestUtil.convertFromVarchar("AAAAAgAAAAAAAAAAQO1MABsxi45AWQAAAAAAAAAAAFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEA8PDtC+FLDQgOWvEFj4PZBBO0zQLNTZUCXEtdAUZa8QBu6Mz+1UwQ2e9uuNSNS5zSywbA0hsbcNYLMijS4wWY2MaaINUbxbjWa2Pg1wItRNjBUTTuUrTpAs0/qQsYVNUK+ppo8zOmSOLkFPj6kdJtCDDCCQx4OekKa+ZVC+FIXQwnZCUOrmYZDu58ZQ7aMn0PjVipD6dlLQ8KYqEQzGT5EZBIERAf6AkQL1s5EZ+q9RE0Zx0M/9xNDECkuRFXdGUMLwsVEVyeiQ7aio0TMl5lE+b+6RP0HxUT2+DFE/2VVRRj2aETUfSxFHXMzRUF+DEV/NVdFcj0FRbV10EXI5I9FHD//Repf/DsKfEk6qKgKOv6atQEBAQIDBQkPGixIRGt9iAKqAtYFmwmeEKIY0yOpOtZIu1qo1wGWjwKqjgOF0wTPwgb55gbuxAnKzQuylBDSoR6+oR6NlCf7xFD42k2tkmCs+TGqyHPW5Raj8Uzati/bohu01g3S5gmBoAib/wjA6AH6/APeygLRRvq9AYtxgBWmSaQEiSmYFZgPzgLkB+UDygKiAYsBahU+JBIRBwgCBQICAQ==");
        System.out.println(digest.getMax());
        System.out.println(digest.getMin());
        System.out.println(digest.quantile(0.1));
        System.out.println(digest.quantile(0.25));
        System.out.println(digest.quantile(0.5));
        System.out.println(digest.quantile(0.75));
        System.out.println(digest.quantile(0.95));
    }
}