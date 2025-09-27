-- Create patient table
CREATE TABLE
    patient (
        id BIGSERIAL PRIMARY KEY,
        nick VARCHAR(40),
        name VARCHAR(40),
        surname VARCHAR(40),
        email VARCHAR(40),
        telephone VARCHAR(20),
        year_of_birth INT,
        join_date TIMESTAMP,
        information VARCHAR(1023),
        approval BOOLEAN
    );

-- Create therapy table
CREATE TABLE
    therapy (
        id BIGSERIAL PRIMARY KEY,
        kind_of_therapy VARCHAR(1023),
        description TEXT,
        price DECIMAL(10, 2)
    );

-- Create calender table
CREATE TABLE
    calender (
        id BIGSERIAL PRIMARY KEY,
        dayOf VARCHAR(20),
        time VARCHAR(10),
        free BOOLEAN
    );

-- Create appointment table
CREATE TABLE
    appointment (
        id BIGSERIAL PRIMARY KEY,
        patient_id BIGINT,
        therapy_id BIGINT,
        calender_id BIGINT,
        FOREIGN KEY (patient_id) REFERENCES patient (id),
        FOREIGN KEY (therapy_id) REFERENCES therapy (id),
        FOREIGN KEY (calender_id) REFERENCES calender (id)
    );

-- Create users table
CREATE TABLE
    users (
        id BIGSERIAL PRIMARY KEY,
        email VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL
    );

-- Table: roles
CREATE TABLE
    roles (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(255) UNIQUE NOT NULL,
        description VARCHAR(255)
    );

-- Table: user_roles (junction table)
CREATE TABLE
    user_roles (
        user_id BIGINT,
        role_id BIGINT,
        CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
        CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id),
        CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id)
    );

-- Indexy dla user_roles
CREATE INDEX fk_user_roles_user_id_index_c ON user_roles (user_id);

CREATE INDEX fk_user_roles_role_id_index_c ON user_roles (role_id);

-- Create change_password table
CREATE TABLE
    change_password (
        id BIGSERIAL PRIMARY KEY,
        patient_id BIGINT,
        old_password VARCHAR(255),
        new_password VARCHAR(255),
        confirm_new_password VARCHAR(255),
        token VARCHAR(255),
        token_expiry_date TIMESTAMP,
        FOREIGN KEY (patient_id) REFERENCES patient (id)
    );

-- Create counter table
CREATE TABLE
    counter (
        id BIGSERIAL PRIMARY KEY,
        session_id VARCHAR(255),
        ip VARCHAR(20),
        refresh BIGINT,
        entry BIGINT,
        date DATE,
        url VARCHAR(255)
    );