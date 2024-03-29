INSERT INTO AUTHORS (FULL_NAME) VALUES ('Александр Сергеевич Пушкин');
INSERT INTO AUTHORS (FULL_NAME) VALUES ('Лев Николаевич Толстой');
INSERT INTO AUTHORS (FULL_NAME) VALUES ('Джоан Роулинг');

INSERT INTO GENRES (NAME) VALUES ('Поэма');
INSERT INTO GENRES (NAME) VALUES ('Роман');
INSERT INTO GENRES (NAME) VALUES ('Фэнтези');

INSERT INTO BOOKS (TITLE) VALUES ('Руслан и Людмила');
INSERT INTO BOOKS (TITLE) VALUES ('Война и мир');
INSERT INTO BOOKS (TITLE) VALUES ('Гарри Поттер');

INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (1, 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (2, 2);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (3, 3);

INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID) VALUES (1, 1);
INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID) VALUES (2, 2);
INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID) VALUES (3, 3);

INSERT INTO COMMENTS (BOOK_ID, TEXT) VALUES (1, 'comment 1');
INSERT INTO COMMENTS (BOOK_ID, TEXT) VALUES (1, 'comment 2');
INSERT INTO COMMENTS (BOOK_ID, TEXT) VALUES (2, 'comment 3');