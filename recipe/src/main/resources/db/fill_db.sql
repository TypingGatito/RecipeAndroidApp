-- Вставка тестовых пользователей
INSERT INTO users (login, password, email, is_active)
VALUES
    ('john_doe', 'password123', 'john.doe@example.com', true),
    ('jane_smith', 'securepassword', 'jane.smith@example.com', true),
    ('admin_user', 'admin', 'admin@example.com', true);

-- Вставка тестовых ролей пользователей
INSERT INTO user_role (user_id, role)
VALUES
    (1, 'USER'),
    (2, 'USER'),
    (3, 'ADMIN');

-- Вставка тестовых секций
INSERT INTO sections (name)
VALUES
    ('Breakfast'),
    ('Lunch'),
    ('Dinner');

-- Вставка тестовых рецептов
INSERT INTO recipes (section_id, user_id, name, calories_on_hund_g, time_to_cook, dose_num, short_description, created_at)
VALUES
    (1, 1, 'Pancakes', 250, '00:20:00', 2, 'Fluffy pancakes with syrup', NOW()),
    (2, 2, 'Spaghetti Bolognese', 350, '00:45:00', 4, 'Classic spaghetti with rich meat sauce', NOW()),
    (3, 3, 'Grilled Chicken', 400, '00:30:00', 3, 'Juicy grilled chicken with spices', NOW());

-- Вставка тестовых шагов для рецептов
INSERT INTO steps (recipe_id, num, text)
VALUES
    (1, 1, 'Mix the flour, milk, and eggs to make the batter.'),
    (1, 2, 'Heat the pan and cook the pancakes until golden brown.'),
    (2, 1, 'Boil the spaghetti in salted water until al dente.'),
    (2, 2, 'Cook the minced meat with onions, garlic, and tomato sauce.'),
    (3, 1, 'Marinate the chicken with your favorite spices.'),
    (3, 2, 'Grill the chicken until fully cooked and juicy.');

-- Вставка тестовых комментариев для шагов
INSERT INTO commentaries (user_id, step_id, order_num, text)
VALUES
    (1, 1, 1, 'Make sure the batter is smooth before cooking.'),
    (2, 2, 1, 'The sauce should be thick, adjust seasoning if needed.'),
    (3, 1, 1, 'Add a little extra spice for more flavor.');

-- Вставка тестовых рейтингов для рецептов
INSERT INTO rating (recipe_id, user_id, rating)
VALUES
    (1, 1, 5.0),
    (2, 2, 4.5),
    (3, 3, 4.8);

-- Вставка тестовых ингредиентов для рецептов
INSERT INTO ingredients (recipe_id, name, amount, unit)
VALUES
    (1, 'Flour', 200, 'GRAM'),
    (1, 'Eggs', 2, 'PIECE'),
    (2, 'Spaghetti', 500, 'GRAM'),
    (2, 'Minced Beef', 300, 'GRAM'),
    (3, 'Chicken Breast', 2, 'PIECE'),
    (3, 'Spices', 10, 'GRAM');

-- Вставка тестовых избранных рецептов
INSERT INTO favourite_recipes (user_id, recipe_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);
