INSERT INTO users (id, email, password, first_name, last_name, profile_img, username) VALUES (
    default,
    'testuser@gmail.com',
    'password',
    'Test',
    'User',
    'https://th.bing.com/th/id/OIP.61ajO7xnq1UZK2GVzHymEQAAAA?w=145&h=150&c=7&r=0&o=5&pid=1.7',
    'TestUserName'
);

INSERT INTO users (id, email, password, first_name, last_name, profile_img, username) VALUES (
    default,
    'ctang@gmail.com',
    'password',
    'Colby',
    'Tang',
    'https://th.bing.com/th/id/OIP.61ajO7xnq1UZK2GVzHymEQAAAA?w=145&h=150&c=7&r=0&o=5&pid=1.7',
    'ctang'
);

INSERT INTO posts (id, text, image_url, author_id, created_date) VALUES (
    10000,
    'The classic',
    'https://i.imgur.com/fhgzVEt.jpeg',
    1,
    now()
),
(
    10001,
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    '',
    1, now()
);

ALTER TABLE users ALTER COLUMN id RESTART WITH (select max(id)+1 from users);