-- Dodanie ról
insert into roles (name, description)
values
    ('ROLE_ADMIN', 'pełne uprawnienia'),
    ('ROLE_USER', 'podstawowe uprawnienia');

-- Dodanie użytkowników
insert into users (email, password)
values
    ('admin@example.com', '$2a$10$CwTycUXWue0Thq9StjUM0uJ8Upb0nV.VaA0m2d73kp5UsnI/x5Mfe'), -- "adminpass"
    ('user@example.com', '$2a$10$Dow1jRt7xUp4Cj8f7sM0FeyGjvS4I5Y9cFg5hFQKQ9vCh/Hs4RROe'),   -- "userpass"
    ('editor@example.com', '$2a$10$7Q9FzHkK7ZVz7/7pZkVruuS5C6IQtgF4Nw9ShFjRjU3ohc.8aC9Gi'), -- "editorpass"
    ('draff3@mtv.com','$2a$10$C3V3xjc7VvZr0PMyX2l1COfz77lFqgArM3RV9f6piIhlqRkgYVzvK'),      -- "Szyfrowane7X"
    ('pslateford4@t.co','$2a$10$HwvFb/2QoEhtqO/kLkcmNO3TT2lLrB/uL2tIYmX.kKFA9i19Q6Jve'),    -- "TajneHaslo8_"
    ('goralek_z_gor@o2.pl','$2a$10$2vWrxLQ2Mo2hLqP9BbJwNOnt2rRHD/qTgQhXr0AnQ8h8gZLRV2a1C'), -- "BezZmartwien1e"
    ('goralek_z_gor@tlen.pl','$2a$10$K20wZJ3sYQFJ7o5D29u2fOaTnY4u8i8rKqPlN3BIXC3k3nKj4n0zq'), -- "123"
    ('tomasz.gorski88@gmail.com','$2a$10$hV3vhE7f9F7g/3r2H9ZYeOZ5yZ3Z0ZsUQFXovXr4Vf9wJxRyb2tu6'), -- "123Sekretne$"
    ('i11youle1d@redcross.org','$2a$10$ZAwcQZ0Wb8fB8pF2eQ9AZeCv7SZX7Xszr8nQhxhqfKh0k3H4hw7mK'); -- "R@ndomH@ss!"

-- Przypisanie ról użytkownikom
insert into user_roles (user_id, role_id)
values
    (1, 1),  -- admin
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 2),
    (9, 2);
