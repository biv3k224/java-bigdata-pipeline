package com.bigdata.ingestion.spark;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Standalone Spark application - run this separately from Spring Boot
 */
public class StandaloneSparkProcessor {
    
    public static void main(String[] args) {
        // These must be passed as JVM arguments, not system properties
        System.out.println("=== Standalone Spark Processor ===");
        
        SparkSession spark = SparkSession.builder()
                .appName("BigDataPipelineProcessor")
                .master("local[*]")
                .config("spark.sql.adaptive.enabled", "true")
                .getOrCreate();

        spark.sparkContext().setLogLevel("WARN");

        System.out.println("Spark Version: " + spark.version());

        try {
            Dataset<Row> df = spark
                    .read()
                    .format("kafka")
                    .option("kafka.bootstrap.servers", "localhost:9092")
                    .option("subscribe", "raw-events")
                    .option("startingOffsets", "earliest")
                    .load();

            System.out.println("=== Kafka Data Schema ===");
            df.printSchema();

            System.out.println("=== Sample Data ===");
            Dataset<Row> sampleData = df.selectExpr("CAST(key AS STRING) as key", "CAST(value AS STRING) as value");
            sampleData.show(5, false);

            long totalCount = df.count();
            System.out.println("Total messages: " + totalCount);

            if (totalCount > 0) {
                sampleData.createOrReplaceTempView("raw_events");
                
                Dataset<Row> sourceCounts = spark.sql(
                    "SELECT get_json_object(value, '$.source') as source, COUNT(*) as count " +
                    "FROM raw_events WHERE get_json_object(value, '$.source') IS NOT NULL " +
                    "GROUP BY source ORDER BY count DESC"
                );
                
                System.out.println("=== Events by Source ===");
                sourceCounts.show();
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            spark.stop();
            System.out.println("=== Spark Finished ===");
        }
    }
}