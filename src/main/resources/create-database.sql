create table card_product (
    id uuid primary key default gen_random_uuid(),
    card_name varchar(255),
    description varchar(255),
    card_image_link varchar(255)
);

create table card (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null,
    card_product_id uuid not null,
    plastic_name varchar(255),
    exp_date varchar(255),
    cvv int,
    contract_name varchar(255),
    pan varchar(255) unique,
    open_document varchar(255),
    close_document varchar(255),
    close_flag boolean default false
);

INSERT INTO card_product (card_name, description, card_image_link) VALUES
    (
       'Master Hasler Card',
       'Карта с повышенным кэшбэком на путешествия и доступом в бизнес-залы.',
       'https://i.ibb.co/Pbg6Vy0/photo.jpg'
    ),
    (
       'Java Back Card',
       'Для самых тестостероновых программистов',
       'https://i.ibb.co/Ld2gFqsQ/photo.jpg'
    ),
    (
       'Student Card',
       'Бесплатное обслуживание и специальные бонусы на посещение преподского лифта',
       'https://i.ibb.co/xqQ920Vx/photo.jpg'
    );
