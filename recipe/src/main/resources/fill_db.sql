-- Вставка пользователей
INSERT INTO users (login, password, email, is_active) VALUES
('user1', 'password1', 'user1@example.com', TRUE),
('user2', 'password2', 'user2@example.com', TRUE),
('admin', 'adminpass', 'admin@example.com', TRUE),
('user3', 'password3', 'user3@example.com', TRUE);

-- Вставка ролей пользователей
INSERT INTO user_role (user_id, role) VALUES
(1, 'USER'),  -- user1
(2, 'USER'),  -- user2
(3, 'ADMIN'), -- admin
(4, 'USER');  -- user3

-- Вставка секций
INSERT INTO sections (name) VALUES
('Супы'),
('Основные блюда'),
('Десерты');

-- Вставка рецептов
INSERT INTO recipes (section_id, user_id, name, calories_on_hund_g, time_to_cook, dose_num, short_description) VALUES
(1, 1, 'Куриный бульон', 50, '1 hour', 4, 'Вкусный и насыщенный куриный бульон.'),
(2, 2, 'Спагетти с мясом', 300, '30 minutes', 2, 'Итальянские спагетти с мясным соусом.'),
(3, 3, 'Торт Наполеон', 400, '2 hours', 8, 'Классический торт с кремом.'),
(2, 4, 'Овощное рагу', 150, '45 minutes', 4, 'Полезное и вкусное овощное рагу.');

-- Вставка ингредиентов для первых рецептов
INSERT INTO ingredients (recipe_id, name, amount, unit) VALUES
(1, 'Курица', 1, 'PIECE'),
(1, 'Вода', 2, 'LITER'),
(2, 'Спагетти', 200, 'GRAM'),
(2, 'Фарш', 300, 'GRAM'),
(3, 'Мука', 500, 'GRAM'),
(3, 'Сахар', 200, 'GRAM'),
(4, 'Картофель', 300, 'GRAM'),
(4, 'Морковь', 100, 'GRAM');

-- Вставка шагов для первого рецепта
INSERT INTO step (recipe_id, num, text) VALUES
(1, 1, 'Положить курицу в кастрюлю.'),
(1, 2, 'Добавить воду и довести до кипения.'),
(1, 3, 'Убавить огонь и варить 30 минут.');

-- Вставка шагов для второго рецепта
INSERT INTO step (recipe_id, num, text) VALUES
(2, 1, 'Отварить спагетти.'),
(2, 2, 'Обжарить фарш на сковороде.'),
(2, 3, 'Смешать спагетти с мясом.');

-- Вставка комментариев к шагам
INSERT INTO commentaries (user_id, step_id, order_num, text) VALUES
(1, 1, 1, 'Отличный рецепт!'),
(2, 2, 2, 'Мясо получается очень вкусным.');

-- Вставка рейтинга для рецептов
INSERT INTO rating (recipe_id, user_id, rating) VALUES
(1, 1, 4.5),
(2, 2, 5.0),
(3, 3, 4.0),
(4, 4, 4.8);

-- Вставка избранных рецептов
INSERT INTO favourite_recipes (user_id, recipe_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);
