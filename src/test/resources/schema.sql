CREATE TABLE users (
  id INT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(35) NOT NULL,
  firstName VARCHAR(35) NOT NULL,
  surname VARCHAR(35) NOT NULL,
  phone VARCHAR(12) NOT NULL,
  isActivated INT NULL
);

CREATE TABLE departments (
  id IDENTITY PRIMARY KEY,
  location VARCHAR(255) NOT NULL,
  manager_id INT NULL,
  CONSTRAINT manag_id FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);