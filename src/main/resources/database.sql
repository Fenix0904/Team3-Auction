-- Insert data

INSERT INTO roles VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles VALUES (2, 'ROLE_USER');

INSERT INTO user VALUES (1,
                         'Svyatoslav',
                         -- Це пароль, я заюзав new BCryptPasswordEncoder().encode("тут свій пароль");
                         '$2a$10$9BX/sqXVeSOD.vex/Sj6E.XTIfXOmjjOuDeqDf2lbgJEN99mRtQze',
                         0,
                         'Fenix0904');

INSERT INTO user VALUES (2,
                         'Svyatoslav',
                         -- Це пароль, я заюзав new BCryptPasswordEncoder().encode("тут свій пароль");
                         '$2a$10$9BX/sqXVeSOD.vex/Sj6E.XTIfXOmjjOuDeqDf2lbgJEN99mRtQze',
                         1,
                         'TestUser');

INSERT INTO category VALUES (1, 'TEH');

INSERT INTO status VALUES (1, 'PLANNED', 0);
INSERT INTO status VALUES (2, 'OPEN', 1);
INSERT INTO status VALUES (3, 'CLOSED', 2);


INSERT INTO auction VALUES (1, '2020-12-12', '2020-12-12', 1, 1, 1);
INSERT INTO auction VALUES (2, '2020-12-12', '2020-12-12', 1, 1, 3);
INSERT INTO auction VALUES (6, '2017-12-10 16:46:00', '2017-12-10 16:47:00', 1, 1, 2);
INSERT INTO auction VALUES (4, '2017-12-10 16:47:00', '2017-12-10 16:48:00', 1, 1, 3);
INSERT INTO auction VALUES (5, '2017-12-10 16:46:00', '2017-12-10 16:49:00', 1, 1, 3);
INSERT INTO auction VALUES (7, '2017-12-10 17:12:00', '2017-12-10 17:15:00', 1, 1, 2);
INSERT INTO auction VALUES (8, '2017-12-10 17:13:00', '2017-12-10 17:14:00', 1, 1, 1);
INSERT INTO auction VALUES (9, '2017-12-10 17:12:00', '2017-12-10 17:13:00', 1, 1, 3);

INSERT INTO lot VALUES (1, 10, 'This is description', 200, 1, 'This is Title', 1, 200);
INSERT INTO lot VALUES (2, 100, 'This is description', 200, 1, 'This is Title', 1);
INSERT INTO lot VALUES (3, 1000, 'This is description', 200, 1, 'This is Title', 2);
INSERT INTO lot VALUES (4, 230, 'This is description', 200, 1, 'This is Title', 1);
INSERT INTO lot VALUES (5, 4234, 'This is description', 200, 1, 'This is Title', 2);
INSERT INTO lot VALUES (6, 234, 'This is description', 200, 1, 'This is Title', 1);

