-- Insert Users
INSERT INTO users (id, username, password) VALUES (1, 'alice', 'password123');
INSERT INTO users (id, username, password) VALUES (2, 'bob', 'securePass');
INSERT INTO users (id, username, password) VALUES (3, 'charlie', 'charliePass');

-- Insert User Followings
INSERT INTO user_followings (user_id, following_id) VALUES (1, 2); -- Alice follows Bob
INSERT INTO user_followings (user_id, following_id) VALUES (2, 3); -- Bob follows Charlie
INSERT INTO user_followings (user_id, following_id) VALUES (3, 1); -- Charlie follows Alice

-- Insert Posts
INSERT INTO posts (id, title, body, user_id) VALUES (1, 'My First Post', 'Hello, this is my first post!', 1);
INSERT INTO posts (id, title, body, user_id) VALUES (2, 'Learning Spring Boot', 'Spring Boot makes development easy!', 2);
INSERT INTO posts (id, title, body, user_id) VALUES (3, 'Java Rocks!', 'Java is a powerful programming language.', 3);

-- Insert Likes
INSERT INTO likes (id, user_id, post_id) VALUES (1, 2, 1); -- Bob likes Alice's post
INSERT INTO likes (id, user_id, post_id) VALUES (2, 3, 1); -- Charlie likes Alice's post
INSERT INTO likes (id, user_id, post_id) VALUES (3, 1, 2); -- Alice likes Bob's post
INSERT INTO likes (id, user_id, post_id) VALUES (4, 3, 2); -- Charlie likes Bob's post
INSERT INTO likes (id, user_id, post_id) VALUES (5, 1, 3); -- Alice likes Charlie's post
