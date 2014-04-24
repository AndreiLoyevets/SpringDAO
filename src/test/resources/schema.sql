CREATE TABLE users (
  id IDENTITY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(35) NOT NULL,
  firstName VARCHAR(35) NOT NULL,
  surname VARCHAR(35) NOT NULL,
  phone VARCHAR(12) NOT NULL,
  isActivated INT NULL
);