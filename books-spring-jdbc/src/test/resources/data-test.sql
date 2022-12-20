insert into AUTHORS (FULL_NAME) values ('Александр Сергеевич Пушкин');
insert into AUTHORS (FULL_NAME) values ('Лев Николаевич Толстой');
insert into AUTHORS (FULL_NAME) values ('Джоан Роулинг');

insert into GENRES (NAME) values ('Поэма');
insert into GENRES (NAME) values ('Роман');
insert into GENRES (NAME) values ('Фэнтези');

insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (1, 1, 'Руслан и Людмила');
insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (2, 2, 'Война и мир');
insert into BOOKS (AUTHOR_ID, GENRE_ID, TITLE) values (3, 3, 'Гарри Поттер');