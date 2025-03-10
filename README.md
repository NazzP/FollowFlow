# Simple Social Media Application API

This project is a RESTful API for a simple social media application built using **Spring Boot**, **Hibernate**, and **PostgreSQL**. The application allows users to create and view posts, follow other users, and like posts. Each post contains a title, body, and author.

## Features

- **User Registration**: Allows users to create an account.
- **Create Post**: Users can create posts with a title and body.
- **View Posts**: Users can view all posts or posts by a specific user.
- **Follow Users**: Users can follow and unfollow other users.
- **Like Posts**: Users can like posts created by others.
- **Persistence**: Uses Hibernate to persist user and post data in a PostgreSQL database.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Hibernate**
- **PostgreSQL**
- **Maven**
- **JUnit**: For unit testing.
- **Testcontainers**: For integration testing.

## Getting Started

### Prerequisites
Make sure you have the following installed:

- JDK 17 or higher
- Maven
- PostgreSQL (for local development)
- An IDE (e.g., IntelliJ IDEA, Eclipse)


### Coverage ( mvn clean verify with jacoco plugin ): 
![зображення](https://github.com/user-attachments/assets/04901011-ca2f-4fbc-a91a-6b038edddb12)

### mvn clean install ran successfully without issues: 
![зображення](https://github.com/user-attachments/assets/69ebcad3-738c-4981-a41f-53ca3bda2ce3)


### ChatGPT Usage Feedback

- **Was it easy to complete the task using AI?**: 
  Yes, the AI provided clear guidance on how to set up the project architecture, including necessary configurations for Spring Boot, Hibernate, and PostgreSQL. It also helped generate boilerplate code for the services and controllers, which significantly reduced development time.

- **How long did the task take you to complete?**: 
  The entire task took approximately 2-3 hours to complete. This includes initial setup, coding, and testing. If you include learning and debugging time, it could be closer to 4 days.

- **Was the code ready to run after generation? What did you have to change to make it usable?**: 
  Most of the generated code was functional and followed best practices. However, I had to configure the database connection settings in the `application.properties` file. Additionally, I adjusted some methods for personal coding style preferences and added validation logic for user input.

- **Which challenges did you face during the completion of the task?**: 
  One significant challenge was ensuring that the relationships between entities (like `User` and `Post`) were correctly established and mapped. I also encountered issues while testing the interactions between entities, especially with cascading operations in Hibernate.

- **Which specific prompts did you learn as a good practice to complete the task?**: 
  I learned that using specific prompts like "Generate a CRUD API with Spring Boot" and "How to configure Hibernate with PostgreSQL" yields better results. Additionally, asking for explanations of concepts (like lazy vs. eager loading in Hibernate) helped me understand the underlying workings better while implementing them in the project.
