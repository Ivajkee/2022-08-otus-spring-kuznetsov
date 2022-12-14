insert into AUTHORS (NAME) values ('Александр Сергеевич Пушкин');
insert into AUTHORS (NAME) values ('Лев Николаевич Толстой');
insert into AUTHORS (NAME) values ('Джоан Роулинг');
insert into AUTHORS (NAME) values ('Агата Кристи');
insert into AUTHORS (NAME) values ('Стивен Кинг');

insert into GENRES (NAME) values ('Поэма');
insert into GENRES (NAME) values ('Роман');
insert into GENRES (NAME) values ('Фэнтези');
insert into GENRES (NAME) values ('Детектив');
insert into GENRES (NAME) values ('Ужасы');

insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (1, 1, 'Руслан и Людмила');
insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (2, 2, 'Война и мир');
insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (3, 3, 'Гарри Поттер');
insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (4, 4, 'Убийство в Восточном экспрессе');
insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (5, 5, 'Оно');