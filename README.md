## Distinct microservices / monolith functionalities:

| Microservice              | Main Function                           | Communication                                |
|--------------------------|------------------------------------------|----------------------------------------------|
| **Product Service**       | Product CRUD                             | REST + Kafka (produce `product-events`)      |
| **Category Service**      | Category CRUD                            | REST                                          |
| **Pricing Service**       | Price management + history               | REST + Kafka (`price-changes`)               |
| **Inventory Service**     | Stock management                         | REST + Kafka (consume `product-events`)      |
| **Audit Service**         | Centralized event logging                | Kafka (subscribes to multiple topics)        |
| **Auth Service**          | JWT authentication              | REST                                          |
| **Frontend (Angular)**    | User Interface                           | HTTP (via Gateway)                           |
