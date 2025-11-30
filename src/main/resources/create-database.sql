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
    card_name varchar(255),
    open_document varchar(255),
    close_document varchar(255),
    close_flag boolean default false,
    image_link varchar(255)
);
