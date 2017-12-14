-- Insert data

INSERT INTO user VALUES (1,
                         -- Це пароль, я заюзав new BCryptPasswordEncoder().encode("тут свій пароль");
                         '$2a$10$9BX/sqXVeSOD.vex/Sj6E.XTIfXOmjjOuDeqDf2lbgJEN99mRtQze',
                         0,
                         'Fenix0904');

INSERT INTO user VALUES (2,
                         -- Це пароль, я заюзав new BCryptPasswordEncoder().encode("тут свій пароль");
                         '$2a$10$9BX/sqXVeSOD.vex/Sj6E.XTIfXOmjjOuDeqDf2lbgJEN99mRtQze',
                         1,
                         'TestUser');

INSERT INTO category VALUES (1, 'TEH');
INSERT INTO category VALUES (2, 'T');
INSERT INTO category VALUES (3, 'G');
INSERT INTO category VALUES (4, 'V');
INSERT INTO category VALUES (5, 'A');


