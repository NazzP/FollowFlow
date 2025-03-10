-- Drop existing tables if they exist
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS user_followings;
DROP TABLE IF EXISTS users;

-- Create Users table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create User Followings table (Self-referencing Many-to-Many)
CREATE TABLE user_followings (
                                 user_id INT NOT NULL,
                                 following_id INT NOT NULL,
                                 PRIMARY KEY (user_id, following_id),
                                 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                 FOREIGN KEY (following_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Posts table
CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       body TEXT NOT NULL,
                       user_id INT NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Likes table (Many-to-Many between Users and Posts)
CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       user_id INT NOT NULL,
                       post_id INT NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);
