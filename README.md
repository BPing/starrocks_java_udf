# starrocs java udf


```sql
CREATE
GLOBAL FUNCTION t_digest_hash(double)
RETURNS string
PROPERTIES (
    "symbol" = "com.starrocks.udf.TDigestHash",
    "type" = "StarrocksJar",
    "file" = "https://xxxx/udf-1.0-SNAPSHOT-jar-with-dependencies.jar"
);

CREATE
GLOBAL FUNCTION t_digest_quantile(string,double)
RETURNS double
PROPERTIES (
    "symbol" = "com.starrocks.udf.TDigestQuantile",
    "type" = "StarrocksJar",
    "file" = "https://xxxx/udf-1.0-SNAPSHOT-jar-with-dependencies.jar"
);

CREATE
GLOBAL AGGREGATE FUNCTION t_digest_union(string)
RETURNS string
PROPERTIES
(
    "symbol" = "com.starrocks.udaf.TDigestUnion",
    "type" = "StarrocksJar",
    "file" = "https://xxxx/udf-1.0-SNAPSHOT-jar-with-dependencies.jar"
);
```