INSERT INTO AUTHORS (FULL_NAME) VALUES ('Александр Сергеевич Пушкин');
INSERT INTO AUTHORS (FULL_NAME) VALUES ('Лев Николаевич Толстой');
INSERT INTO AUTHORS (FULL_NAME) VALUES ('Джоан Роулинг');
INSERT INTO AUTHORS (FULL_NAME) VALUES ('Агата Кристи');
INSERT INTO AUTHORS (FULL_NAME) VALUES ('Стивен Кинг');

INSERT INTO GENRES (NAME) VALUES ('Поэма');
INSERT INTO GENRES (NAME) VALUES ('Роман');
INSERT INTO GENRES (NAME) VALUES ('Фэнтези');
INSERT INTO GENRES (NAME) VALUES ('Детектив');
INSERT INTO GENRES (NAME) VALUES ('Ужасы');

INSERT INTO BOOKS (AUTHOR_ID, GENRE_ID, TITLE) VALUES (1, 1, 'Руслан и Людмила');
INSERT INTO BOOKS (AUTHOR_ID, GENRE_ID, TITLE) VALUES (2, 2, 'Война и мир');
INSERT INTO BOOKS (AUTHOR_ID, GENRE_ID, TITLE) VALUES (3, 3, 'Гарри Поттер');
INSERT INTO BOOKS (AUTHOR_ID, GENRE_ID, TITLE) VALUES (4, 4, 'Убийство в Восточном экспрессе');
INSERT INTO BOOKS (AUTHOR_ID, GENRE_ID, TITLE) VALUES (5, 5, 'Оно');