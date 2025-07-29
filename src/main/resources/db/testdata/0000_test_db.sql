-- Create patient table
CREATE TABLE patient (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         nick VARCHAR(40),
                         name VARCHAR(40),
                         surname VARCHAR(40),
                         email VARCHAR(40),
                         telephone VARCHAR(20),
                         year_of_brith INT,
                         join_date TIMESTAMP,
                         information VARCHAR(1023),
                         approval BOOLEAN
);

-- Create therapy table
CREATE TABLE therapy (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         kind_of_therapy VARCHAR(255),
                         description TEXT,
                         price DECIMAL(10,2)
);

-- Create calender table
CREATE TABLE calender (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          patient_id INT,
                          dayOf VARCHAR(20),
                          time VARCHAR(10),
                          free BOOLEAN,
                          FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Create meeting table
CREATE TABLE meeting (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         patient_id INT,
                         therapy_id INT,
                         calender_id INT,
                         FOREIGN KEY (patient_id) REFERENCES patient(id),
                         FOREIGN KEY (therapy_id) REFERENCES therapy(id),
                         FOREIGN KEY (calender_id) REFERENCES calender(id)
);

-- Create users table
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       email VARCHAR(255),
                       password VARCHAR(255)
);

-- Create user_role table
CREATE TABLE user_role (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255),
                           description VARCHAR(255)
);

-- Create user_roles table (junction table for users and user_role)
CREATE TABLE user_roles (
                            user_id INT,
                            role_id INT,
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES user_role(id),
                            CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id)
);

-- Create indexes for user_roles table
CREATE INDEX fk_user_roles_user_id_index_c ON user_roles (user_id);
CREATE INDEX fk_user_roles_role_id_index_c ON user_roles (role_id);


-- Create change_password table
CREATE TABLE change_password (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 patient_id INT,
                                 old_password VARCHAR(255),
                                 new_password VARCHAR(255),
                                 confirm_new_password VARCHAR(255),
                                 token VARCHAR(255),
                                 token_expiry_date TIMESTAMP,
                                 FOREIGN KEY (patient_id) REFERENCES patient(id)
);


-- Create counter table
CREATE TABLE counter (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         session_id VARCHAR(255),
                         ip VARCHAR(20),
                         refresh INT,
                         entry INT,
                         date DATE
);
