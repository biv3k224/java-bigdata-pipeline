package com.bigdata.ingestion.spark;


import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
public class SimpleSparkProcessor {

	public static void main(String[] args) {
		//Initializing the spark
		SparkSession spark = SparkSession.builder().
				appName("BigDataPipelineProcessor")
				.master("local[*]")
				.config("spark.sql.adaptive.enabled", "true")
				.getOrCreate();
		
		spark.sparkContext().setLogLevel("WARN");
		
		System.out.println("=== Spark Processor Started ===");
		System.out.println("Spark Version: " + spark.version());
		
		try {
			Dataset<Row> df = spark.read().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
					.option("subscribe", "raw-events").option("startingOffsets", "earliest")
					.load();
			
			System.out.println("=== Kafka Data Schema ===");
			df.printSchema();
			
			 System.out.println("=== Sample Raw Data (showing 5 records) ===");
	            Dataset<Row> sampleData = df.selectExpr("CAST(key AS STRING) as key", "CAST(value AS STRING) as value");
	            sampleData.show(5, false);

	            // Count total messages
	            long totalCount = df.count();
	            System.out.println("=== Total messages in topic: " + totalCount + " ===");

	            // Try to parse JSON and show event sources
	            if (totalCount > 0) {
	                System.out.println("=== Event Analysis ===");
	                
	                // Create temporary view for SQL queries
	                sampleData.createOrReplaceTempView("raw_events");
	                
	                // Count events by source (if JSON parsing works)
	                Dataset<Row> sourceCounts = spark.sql(
	                    "SELECT " +
	                    "  get_json_object(value, '$.source') as source, " +
	                    "  COUNT(*) as event_count " +
	                    "FROM raw_events " +
	                    "WHERE get_json_object(value, '$.source') IS NOT NULL " +
	                    "GROUP BY source " +
	                    "ORDER BY event_count DESC"
	                );
	                
	                System.out.println("=== Events by Source ===");
	                sourceCounts.show();
	                
	                // Count events by type
	                Dataset<Row> typeCounts = spark.sql(
	                    "SELECT " +
	                    "  get_json_object(value, '$.eventType') as event_type, " +
	                    "  COUNT(*) as event_count " +
	                    "FROM raw_events " +
	                    "WHERE get_json_object(value, '$.eventType') IS NOT NULL " +
	                    "GROUP BY event_type " +
	                    "ORDER BY event_count DESC"
	                );
	                
	                System.out.println("=== Events by Type ===");
	                typeCounts.show();
	            }

	        } catch (Exception e) {
	            System.err.println("Error processing data: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            spark.stop();
	            System.out.println("=== Spark Processor Finished ===");
	        }
	    }
	
		
	}
