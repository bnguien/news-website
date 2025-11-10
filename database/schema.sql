CREATE DATABASE news_website_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE news_website_db;

CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('reader','reporter','admin') DEFAULT 'reader'
);

CREATE TABLE categories (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT
);

CREATE TABLE articles (
  article_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  summary VARCHAR(300),
  content TEXT NOT NULL,
  image VARCHAR(255),
  author_id INT,
  category_id INT,
  view_count INT DEFAULT 0,
  published_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  status ENUM('draft','published','archived') DEFAULT 'draft',
  FOREIGN KEY (author_id) REFERENCES users(user_id),
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE comments (
  comment_id INT AUTO_INCREMENT PRIMARY KEY,
  article_id INT,
  user_id INT,
  content TEXT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (article_id) REFERENCES articles(article_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
