package com.bigdata.ingestion.spark;

public class SparkRunner {
    public static void main(String[] args) {
        // Java 21 module system compatibility for Spark
        System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        System.setProperty("org.apache.spark.unsafe.Platform.enable", "true");
        System.setProperty("spark.driver.allowMultipleContexts", "true");
        
        // Java module system exports for Spark compatibility
        System.setProperty("--add-exports", "java.base/sun.nio.ch=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.lang=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.io=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.net=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.nio=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.util=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.util.concurrent=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/java.util.concurrent.atomic=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/sun.security.action=ALL-UNNAMED");
        System.setProperty("--add-opens", "java.base/sun.util.calendar=ALL-UNNAMED");
        
        SimpleSparkProcessor.main(args);
    }
}