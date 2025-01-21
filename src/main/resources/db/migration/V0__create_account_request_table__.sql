CREATE TABLE public.account_requests (
    request_id                      uuid            not null,
    name                            varchar (255)   not null,
    date_of_birth                   date            not null,
    street_name                     varchar (255)   not null,
    house_number                    varchar (255)   not null,
    post_code                       varchar (10)    not null,
    city                            varchar (255)   not null,
    id_document                     varchar (255)   null,
    account_type                    varchar (15)    null,
    starting_balance                numeric         null,
    monthly_salary                  numeric         null,
    interested_in_other_products    boolean  DEFAULT false  null,
    email                           varchar (255)   null,
    account_status                  varchar (15)    null,

    CONSTRAINT account_pkey PRIMARY KEY (request_id)
);
