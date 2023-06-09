INSERT INTO statuses("id", "name", "description")
VALUES
    (1, 'active', 'resource enabled for all corresponding functionality'),
    (2, 'inactive', 'resource locked for some/all functionality'),
    (3, 'suspended', 'resource temporarily blocked for some/all operations'),
    (4, 'banned', 'resource permanently blocked from operation'),
    (5, 'pending', 'resource awaiting further action, temporarily blocked from some operations'),
    (6, 'complete', 'resource action successfully completed'),
    (7, 'unverified', 'resource requires verification that is yet to be done'),
    (8, 'failed', 'transaction unsuccessful'),
    (9, 'rejected', 'transaction rejected/cancelled'),
    (10, 'cancelled', 'transaction cancelled by payer');
alter sequence IF EXISTS statuses_id_seq restart with 11;

INSERT INTO roles("id", "name", "description")
VALUES
    (1, 'system-admin', 'Sytem administrator'),
    (2, 'admin', 'client adminstrator/manager'),
    (3, 'customer', 'Car reservation customer'),
    (4, 'api_client', 'Car reservation API clients');
alter sequence IF EXISTS roles_id_seq restart with 5;

INSERT INTO contact_types("id", "name", "description", "regex_value")
VALUES
    (1, 'mobile_number', 'user mobile number', '^[0-9]{12}$'),
    (2, 'email', 'user email address' , '^(?<a>\\w*)(?<b>[\\.-]\\w*){0,3}@(?<c>\\w+)(?<d>\\.\\w{2,}){1,3}$');
alter sequence IF EXISTS contact_types_id_seq restart with 3;

INSERT INTO civil_identity_types("id", "name", "description")
VALUES
    (1, 'passport', 'government issued passport'),
    (2, 'national_id', 'government issued national identity card'),
    (3, 'driving_license', 'government issued driving license');
alter sequence IF EXISTS civil_identity_types_id_seq restart with 4;

INSERT INTO transmission_types("id", "name", "description")
VALUES
    (1, 'automatic', 'the vehicle handles changing of gears itself'),
    (2, 'manual', 'the driver changes the gears');
alter sequence IF EXISTS transmission_types_id_seq restart with 3;

INSERT INTO fuel_types("id", "name", "description")
VALUES
    (1, 'petrol', 'the vehicle uses petrol fuel'),
    (2, 'diesel', 'the vehicle uses diesel');
alter sequence IF EXISTS fuel_types_id_seq restart with 3;

INSERT INTO payment_channels("id", "name", "description")
VALUES
    (1, 'cash', 'cash payment'),
    (2, 'mobile-money', 'mobile money payments'),
    (3, 'card', 'debit or credit cards');
alter sequence IF EXISTS payment_channels_id_seq restart with 4;

INSERT INTO transaction_types("id", "name", "description")
VALUES
    (1, 'reservation_payment', 'payment for car reservation'),
    (2, 'damage_payment', 'payment for damages incurred on the vehicle');
alter sequence IF EXISTS transaction_types_id_seq restart with 3;