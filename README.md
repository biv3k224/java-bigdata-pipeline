markdown
# 🚀 Java Big Data Pipeline & Dashboard

A production-ready real-time data processing pipeline built with Java, Spring Boot, Apache Kafka, Apache Spark, and MongoDB. This system ingests, processes, and visualizes high-volume event data with a modern interactive dashboard.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.11-brightgreen)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.5.0-000000)
![Spark](https://img.shields.io/badge/Apache%20Spark-3.5.0-orange)
![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green)

## 📊 Live Demo
**Local Deployment Only** - Run the complete system with one command:
```bash
docker-compose up -d && mvn spring-boot:run
Then visit: http://localhost:8080

🎯 Features
🔄 Real-time Data Pipeline
Event Ingestion: REST API for high-volume data ingestion

Message Streaming: Apache Kafka for reliable event streaming

Data Processing: Apache Spark for real-time analytics and batch processing

Data Storage: MongoDB for flexible NoSQL data persistence

Real-time Dashboard: Modern web interface with live updates

📈 Analytics & Monitoring
Real-time event processing and aggregation

Event analytics by source, type, and time windows

System health monitoring and metrics

Interactive charts and visualizations

Live event filtering and search

🛠️ Technical Highlights
Microservices architecture with Spring Boot

Containerized with Docker & Docker Compose

Producer-Consumer pattern with Kafka

Stream processing with Spark Structured Streaming

Responsive dashboard with Chart.js

RESTful API design

🏗️ System Architecture
text
[Data Sources] → [Spring Boot API] → [Kafka] → [Spark Streaming] → [MongoDB] → [Dashboard]
     ↑                ↑                 ↑           ↑               ↑            ↑
 Web Apps      Event Ingestion      Message Bus   Processing      Storage    Visualization
 Mobile Apps     (REST API)        (Streaming)   (Analytics)    (NoSQL)     (Real-time)
🚀 Quick Start
Prerequisites
Java 21

Maven 3.6+

Docker & Docker Compose

1. Clone and Setup
bash
git clone https://github.com/yourusername/java-bigdata-pipeline.git
cd java-bigdata-pipeline
2. Start Infrastructure
bash
# Start Kafka, MongoDB, Zookeeper, and Kafka-UI
docker-compose up -d
3. Run the Application
bash
# Build and start Spring Boot application
mvn clean spring-boot:run
4. Access the Dashboard
Open your browser to: http://localhost:8080

📚 API Documentation
Event Ingestion
http
POST /api/v1/events/ingest
Content-Type: application/json

{
  "source": "website",
  "eventType": "PAGE_VIEW",
  "payload": {
    "userId": "user123",
    "page": "/home",
    "duration": 45
  }
}
Health Check
http
GET /api/v1/events/health
Event Queries
http
GET /api/v1/events/all          # All events
GET /api/v1/events/count        # Event count
GET /api/v1/events/source/{src} # Events by source
GET /api/v1/events/type/{type}  # Events by type
🎮 Using the Dashboard
Send Test Events
Use the "Send Test Event" form in the dashboard

Select source (Website, Mobile App, Sensor, API Server)

Choose event type (Page View, User Login, Purchase, etc.)

Add custom JSON payload

Click Send Event and watch real-time updates

Monitor System
View real-time charts of events by source and type

See recent events with filtering capabilities

Check system health status

Monitor event processing in real-time

Interactive Features
Live filtering by source and event type

Auto-refresh dashboard every 10 seconds

Sample data loading for testing

Event export functionality

System reset for clean testing

🔧 Project Structure
text
java-bigdata-pipeline/
├── src/main/java/com/bigdata/ingestion/
│   ├── controller/          # REST Controllers
│   ├── service/            # Business Logic
│   ├── model/              # Data Models
│   ├── config/             # Configuration
│   ├── consumer/           # Kafka Consumers
│   └── spark/              # Spark Processing Jobs
├── src/main/resources/
│   ├── templates/          # Thymeleaf templates
│   └── static/            # CSS, JS, assets
├── docker-compose.yml      # Infrastructure setup
└── README.md
🛠️ Technology Stack
Component	Technology	Purpose
Backend	Spring Boot 3, Java 21	REST API, Business Logic
Messaging	Apache Kafka 3.5	Event Streaming, Message Broker
Processing	Apache Spark 3.5	Real-time Analytics, Batch Processing
Database	MongoDB 6.0	NoSQL Data Storage
Dashboard	Thymeleaf, Chart.js	Real-time Visualization
Container	Docker, Docker Compose	Environment Management
Build Tool	Maven	Dependency Management
💡 Use Cases
This pipeline is designed for real-world scenarios like:

🛒 E-commerce Analytics
Track user behavior, purchases, and conversion funnels

Real-time product recommendations

Inventory and sales monitoring

📱 Mobile App Analytics
User engagement tracking

Performance monitoring

Crash analytics and error tracking

🔍 IoT Data Processing
Sensor data collection and analysis

Real-time alerting and monitoring

Predictive maintenance

🏦 Financial Services
Transaction monitoring

Fraud detection patterns

Real-time risk analysis

🎓 Learning Outcomes
Building this project demonstrates:

Microservices Architecture with Spring Boot

Event-Driven Design with Kafka

Big Data Processing with Spark

NoSQL Database design with MongoDB

Real-time Dashboard development

Containerization with Docker

REST API design and implementation

🔄 Development
Running Spark Jobs
bash
# Run Spark streaming processor
mvn exec:java -Dexec.mainClass="com.bigdata.ingestion.spark.SparkStreamingProcessor"
Database Management
bash
# Access MongoDB
docker exec -it bigdata-mongodb mongosh bigdata_pipeline

# Check Kafka topics
docker exec -it bigdata-kafka kafka-topics --list --bootstrap-server localhost:9092
Reset System
Use the "Clear All Events" button in the dashboard or:

bash
docker exec -it bigdata-mongodb mongosh bigdata_pipeline --eval "db.raw_events.deleteMany({})"
🤝 Contributing
Fork the repository

Create a feature branch (git checkout -b feature/amazing-feature)

Commit your changes (git commit -m 'Add amazing feature')

Push to the branch (git push origin feature/amazing-feature)

Open a Pull Request

📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Author
Your Name

GitHub: @yourusername

LinkedIn: Your LinkedIn

⭐ Show Your Support
If you find this project helpful, please give it a star! ⭐
