INSERT INTO films (name, description, released, duration_min)
VALUES ('Крёстный отец',
        'Стареющий патриарх преступной династии передаёт контроль над своей тайной империей своему неохотному сыну.',
        '1972-03-24', 175),
       ('Побег из Шоушенка',
        'Два заключённых дружат на протяжении многих лет, находя утешение и в конечном итоге искупление через акты общей признательности.',
        '1994-10-14', 142),
       ('Начало',
        'Вор, крадущий корпоративные секреты с помощью технологии совместного сна, получает противоположное задание - засадить идею в ум генерального директора.',
        '2010-07-16', 148);

INSERT INTO mpa (mpa) VALUES ('G'),
                            ('PG'),
                            ('PG-13'),
                            ('R');

INSERT INTO mpa_films (mpa, film) VALUES (4, 1),
                                        (4, 2),
                                        (3, 3);

INSERT INTO users (name, login, email, birthdate)
VALUES ('John Doe', 'johnd', 'john@example.com', '1980-04-15'),
       ('Jane Smith', 'janes', 'jane@example.com', '1990-12-06');

INSERT INTO friendships (user_from, user_to, status)
VALUES (1, 2, true);

INSERT INTO likes (user_id, film_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);

INSERT INTO genres (genre)
VALUES ('Drama'),
       ('Crime'),
       ('Fantasy'),
       ('Sci-Fi');

INSERT INTO genre_films (genre, film)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (3, 3),
       (4, 3);