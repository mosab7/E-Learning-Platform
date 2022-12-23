CREATE TABLE user (
  userId BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(128) NOT NULL UNIQUE,
  password VARCHAR(256) NOT NULL,
  name VARCHAR(128) NOT NULL,
  surname VARCHAR(128) NOT NULL,
  email VARCHAR(128) NOT NULL UNIQUE,
  registration_date DATE NOT NULL,
  detail VARCHAR(1024),
  imgUrl VARCHAR(1024) NOT NULL
);

CREATE TABLE auth_user_group (
  auth_user_group_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(128) NOT NULL,
  auth_group VARCHAR(128) NOT NULL,
  CONSTRAINT user_auth_user_group_fk FOREIGN KEY(username) REFERENCES user(username),
  UNIQUE (username, auth_group)
);

CREATE TABLE tutor (
    tutorId BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    surname VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    description VARCHAR(256) NOT NULL,
    detail VARCHAR(1024),
    imgUrl VARCHAR(1024) NOT NULL
);

CREATE TABLE course (
  courseId BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(128) NOT NULL UNIQUE,
  description VARCHAR(256) NOT NULL,
  detail VARCHAR(1024) NOT NULL ,
  difficulty VARCHAR(128) NOT NULL,
  tutorId BIGINT NOT NULL,
  url VARCHAR(1024) NOT NULL ,
  imgUrl VARCHAR(1024) NOT NULL ,
  CONSTRAINT course_fk FOREIGN KEY(tutorId) REFERENCES tutor(tutorId)
);

CREATE TABLE enrollment (
    enrollmentId BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    courseId BIGINT NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT enrollment_user_fk FOREIGN KEY(userId) REFERENCES user(userId),
    CONSTRAINT enrollment_course_fk FOREIGN KEY(courseId) REFERENCES course(courseId)
);