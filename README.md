# 🚀 Java Big Data Pipeline & Dashboard

A **real-time big data processing pipeline** built using **Java, Spring Boot, Apache Kafka, Apache Spark, and MongoDB**.  
It ingests, processes, and visualizes high-volume event data in real time with a modern, interactive dashboard.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.11-brightgreen)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.5.0-000000)
![Spark](https://img.shields.io/badge/Apache%20Spark-3.5.0-orange)
![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

---

## ⚙️ Technologies Used
| Component | Technology | Purpose |
|------------|-------------|----------|
| **Backend** | Java 21, Spring Boot 3 | REST API, Business Logic |
| **Messaging** | Apache Kafka 3.5 | Event Streaming |
| **Processing** | Apache Spark 3.5 | Real-Time Analytics |
| **Database** | MongoDB 6.0 | NoSQL Storage |
| **Dashboard** | Thymeleaf, Chart.js | Visualization |
| **Containerization** | Docker, Docker Compose | Environment Setup |
| **Build Tool** | Maven | Dependency Management |

---

## 🧩 System Architecture
[Data Sources] → [Spring Boot API] → [Kafka] → [Spark Streaming] → [MongoDB] → [Dashboard]

yaml
Copy code

---

## 💻 Run Locally

### 1️⃣ Prerequisites
- Java 21  
- Maven 3.6+  
- Docker & Docker Compose  

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/java-bigdata-pipeline.git
cd java-bigdata-pipeline
3️⃣ Start Infrastructure
bash
Copy code
# Start Kafka, Zookeeper, MongoDB, and Kafka UI
docker-compose up -d
4️⃣ Run the Application
bash
Copy code
mvn clean spring-boot:run
5️⃣ Open the Dashboard
Visit: http://localhost:8080

📚 API Endpoints
http
Copy code
POST /api/v1/events/ingest      # Send events
GET  /api/v1/events/all         # Get all events
GET  /api/v1/events/count       # Event count
GET  /api/v1/events/source/{src}# Events by source
GET  /api/v1/events/type/{type} # Events by type
GET  /api/v1/events/health      # Health check
📈 Dashboard Features
Send and view events in real time

Filter by source and type

Auto-refresh every 10 seconds

Clear system data with one click

Interactive charts powered by Chart.js

🎯 Use Cases
🛒 E-commerce: User analytics, recommendations, sales tracking

📱 Mobile Apps: User engagement, performance monitoring

🔍 IoT Devices: Sensor data collection and real-time alerts

🏦 Finance: Fraud detection and risk monitoring

🤝 Contributing
Fork the repository

Create a new branch (git checkout -b feature/new-feature)

Commit changes (git commit -m 'Add feature')

Push and open a Pull Request

📄 License
Licensed under the MIT License.

👨‍💻 Author
Bibek Kumar Tamang
GitHub: biv3k224
LinkedIn: [Your LinkedIn](https://www.linkedin.com/in/btamang/)

⭐ If you like this project, give it a star!
