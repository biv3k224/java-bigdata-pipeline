# 🚀 Java Big Data Pipeline & Dashboard

A **real-time data processing pipeline** built with **Java, Spring Boot, Apache Kafka, Apache Spark, and MongoDB**.  
It ingests, processes, and visualizes high-volume event data through a modern, interactive dashboard.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.11-brightgreen)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.5.0-000000)
![Spark](https://img.shields.io/badge/Apache%20Spark-3.5.0-orange)
![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green)

---

## 📊 Live Demo
**Local Only:**  
```bash
docker-compose up -d && mvn spring-boot:run
# Visit: http://localhost:8080
🎯 Features
Real-Time Data Pipeline: REST API ingestion → Kafka → Spark Streaming → MongoDB → Dashboard

Event Processing: Real-time analytics, filtering, and aggregation

Dashboard: Live charts, event filters, and system monitoring

Microservices: Built with Spring Boot and containerized with Docker

🛠️ Tech Stack
Component	Technology	Purpose
Backend	Java 21, Spring Boot 3	REST API, Services
Messaging	Apache Kafka 3.5	Event Streaming
Processing	Apache Spark 3.5	Real-Time Analytics
Database	MongoDB 6.0	NoSQL Storage
Dashboard	Thymeleaf, Chart.js	Visualization
Container	Docker, Docker Compose	Deployment
Build Tool	Maven	Dependency Management

🏗️ Architecture
css
Copy code
[Data Sources] → [Spring Boot API] → [Kafka] → [Spark Streaming] → [MongoDB] → [Dashboard]
🚀 Quick Start
1️⃣ Prerequisites
Java 21

Maven 3.6+

Docker & Docker Compose

2️⃣ Setup
bash
Copy code
git clone https://github.com/yourusername/java-bigdata-pipeline.git
cd java-bigdata-pipeline
docker-compose up -d
mvn spring-boot:run
Then open http://localhost:8080

📚 API Endpoints
http
Copy code
POST /api/v1/events/ingest      # Send events
GET  /api/v1/events/all         # Get all events
GET  /api/v1/events/count       # Count events
GET  /api/v1/events/source/{src}# Events by source
GET  /api/v1/events/type/{type} # Events by type
GET  /api/v1/events/health      # Health check
📈 Dashboard Usage
Send Test Events via dashboard form

Monitor Events by source/type with live updates

View Charts auto-refreshing every 10s

Clear System Data with a single click

💡 Real-World Use Cases
🛒 E-commerce Analytics: User tracking, conversions, recommendations

📱 Mobile Analytics: Engagement and crash tracking

🔍 IoT Monitoring: Sensor data analysis and alerts

🏦 Finance: Transaction and fraud detection

🎓 Learning Outcomes
Event-driven architecture with Kafka

Stream processing with Spark

Microservices with Spring Boot

NoSQL data handling with MongoDB

Real-time dashboard integration

Dockerized deployment

🤝 Contributing
Fork the repo

Create a feature branch (git checkout -b feature/new-feature)

Commit changes and open a Pull Request

📄 License
This project is licensed under the MIT License.

👨‍💻 Author
Your Name
GitHub: @yourusername
LinkedIn: Your LinkedIn

⭐ If you find this project helpful, please give it a star!

yaml
Copy code
