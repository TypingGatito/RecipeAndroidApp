CREATE DATABASE IF NOT EXIST recipes;

CREATE TYPE UNIT AS ENUM ('GRAM', 'LITER', 'PIECE');

CREATE TYPE ROLE AS ENUM ('USER', 'ADMIN');

CREATE TABLE IF NOT EXISTS users (
id SERIAL PRIMARY KEY,
login VARCHAR(255),
password VARCHAR(255),
email VARCHAR(255),
is_active BOOLEAN
);

CREATE TABLE IF NOT EXISTS user_role (
user_id INT,
role ROLE,

FOREIGN KEY (user_id) REFERENCES users(id),
PRIMARY KEY (user_id, role)
);


CREATE TABLE IF NOT EXISTS sections (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS recipes (
    id SERIAL PRIMARY KEY,
    section_id BIGINT,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    calories_on_hund_g INT,
    time_to_cook INTERVAL,
    dose_num INT,
    short_description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (section_id) REFERENCES sections(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS step (
    id SERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL,
    num INT NOT NULL,
    text TEXT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)
);

CREATE TABLE IF NOT EXISTS commentaries (
    user_id BIGINT,
    step_id BIGINT,
    order_num INT,
    text TEXT,
    PRIMARY KEY(user_id, step_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (step_id) REFERENCES steps(id)
);

CREATE TABLE IF NOT EXISTS rating (
    recipe_id BIGINT,
    user_id BIGINT,
    rating REAL NOT NULL,
    PRIMARY KEY(recipe_id, user_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS ingredients (
    id SERIAL PRIMARY KEY,
    recipe_id BIGINT,
    name VARCHAR(255),
    amount INT,
    unit UNIT,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)
);

CREATE TABLE IF NOT EXISTS favourite_recipes (
    user_id BIGINT,
    recipe_id BIGINT,
    PRIMARY KEY(user_id, recipe_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

