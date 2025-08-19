INSERT INTO users (id, username, email, password, document, document_type, role, status)
VALUES ('c32118b1-f73e-4498-bdc1-1356b8a5cbad',
        'admin',
        'wesleyprado.dev@gmail.com',
        '$2a$10$0Od6n0QKAJKYf.SMVbz1geTJSwnguG//KcStaU8JKihl17jwBZAJG',
        '48142198061',
        'CPF',
        'ADMIN',
        'ACTIVE') ON CONFLICT (username) DO NOTHING;