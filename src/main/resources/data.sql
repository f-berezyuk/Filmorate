-- MERGE INTO films (name, description, releaseDate, duration)
--     KEY (NAME)
--     VALUES ('Крёстный отец',
--             'Стареющий патриарх преступной династии передаёт контроль над своей тайной империей своему неохотному сыну.',
--             '1972-03-24', 175),
--            ('Побег из Шоушенка',
--             'Два заключённых дружат на протяжении многих лет, находя утешение и в конечном итоге искупление через акты общей признательности.',
--             '1994-10-14', 142),
--            ('Начало',
--             'Вор, крадущий корпоративные секреты с помощью технологии совместного сна, получает противоположное задание - засадить идею в ум генерального директора.',
--             '2010-07-16', 148);

MERGE INTO mpa (mpa)
    key (mpa)
    VALUES ('G'),     -- General audiences (все возрастные группы)
           ('PG'),    -- Parental Guidance suggested (наличие родительского контроля)
           ('PG-13'), -- Parents strongly cautioned (предостережение для родителей)
           ('R'),     -- Restricted (ограничение по возрасту)
           ('NC-17');
-- No one 17 and under admitted (допуск только с 18 лет)

-- MERGE INTO mpa_films (mpa, film)
--     key (film)
--     VALUES (4, 1),
--            (4, 2),
--            (3, 3);

MERGE INTO users (name, login, email, birthday)
    key (EMAIL)
    VALUES ('John Doe', 'johnd', 'john@example.com', '1980-04-15'),
           ('Jane Smith', 'janes', 'jane@example.com', '1990-12-06');

MERGE INTO friendships (user_from, user_to, status)
    key (USER_FROM)
    VALUES (1, 2, true);

MERGE INTO genres (genre)
    key (GENRE)
    VALUES ('Комедия'),            -- Экшн
           ('Драма'),              -- Приключения
           ('Мультфильм'),               -- Комедия
           ('Триллер'),           -- Криминал
           ('Документальный'),        -- Драма
           ('Боевик');
-- Вестерн

-- MERGE INTO genre_films (genre, film)
--     key (genre, film)
--     VALUES (1, 1),
--            (2, 1),
--            (1, 2),
--            (3, 3),
--            (4, 3);