INSERT INTO authors (`name`)
VALUES
('Dan Brown'),
('Dan Brow');

INSERT INTO genres (`type`)
VALUES
('Detective'),
('Roman');

INSERT INTO books (`author_id`,`genre_id`,`title`)
VALUES
(1, 1, 'The Da Vinci Code'),
(2, 2, 'Angels and Demons');
