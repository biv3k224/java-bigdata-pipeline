package com.bigdata.ingestion.spark;

import org.apache.spark.sql.SparkSession;

import java.util.concurrent.TimeoutException;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

public class SparkStreamingProcessor {
    
    public static void main(String[] args) throws StreamingQueryException, TimeoutException {
        System.out.println("=== Starting Spark Streaming Processor ===");
        
        SparkSession spark = SparkSession.builder()
                .appName("BigDataStreamingProcessor")
                .master("local[*]")
                .config("spark.sql.adaptive.enabled", "true")
                .getOrCreate();

        spark.sparkContext().setLogLevel("WARN");

        // Read streaming data from Kafka
        Dataset<Row> stream = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "raw-events")
                .option("startingOffsets", "latest")
                .load();

        // Process the stream
        Dataset<Row> processed = stream
                .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING) as json_value")
                .selectExpr("key", 
                    "get_json_object(json_value, '$.eventId') as event_id",
                    "get_json_object(json_value, '$.source') as source",
                    "get_json_object(json_value, '$.eventType') as event_type",
                    "get_json_object(json_value, '$.timestamp') as timestamp"
                );

        // Real-time aggregations - events per minute by source
        Dataset<Row> windowedCounts = processed
                .groupBy(
                    processed.col("source"),
                    org.apache.spark.sql.functions.window(
                        org.apache.spark.sql.functions.current_timestamp(), 
                        "1 minute"
                    )
                )
                .count()
                .withColumnRenamed("count", "events_per_minute");

        // Output to console
        StreamingQuery query = windowedCounts
                .writeStream()
                .outputMode("update")
                .format("console")
                .option("truncate", "false")
                .start();

        System.out.println("=== Spark Streaming Started - Processing events in real-time ===");
        System.out.println("Send events via Postman to see real-time analytics!");

        query.awaitTermination();
    }
}