DROP TABLE IF EXISTS repairs;

DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS service_plans;

DROP TABLE IF EXISTS watch_accessories;
DROP TABLE IF EXISTS watches;

DROP TABLE IF EXISTS catalogs;

DROP TABLE IF EXISTS customer_phonenumbers;
DROP TABLE IF EXISTS customers;


CREATE TABLE IF NOT EXISTS customers (

                                         id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         customer_id VARCHAR(50) UNIQUE NOT NULL,
    last_name VARCHAR(50),
    first_name VARCHAR(50),
    email_address VARCHAR(50),
    street_address VARCHAR(50),
    postal_code VARCHAR(50),
    city VARCHAR(50),
    province VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(50)
    );


CREATE TABLE IF NOT EXISTS customer_phonenumbers (
                                                     customer_id VARCHAR(50) NOT NULL,
    type VARCHAR(50),
    number VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS catalogs(

                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       catalog_id VARCHAR(50) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL

);

CREATE TABLE IF NOT EXISTS watches (
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       watch_id VARCHAR(50) UNIQUE NOT NULL,
    catalog_id VARCHAR(50) NOT NULL,
    watch_status VARCHAR(20) NOT NULL,
    usage_type VARCHAR(20) NOT NULL,
    model VARCHAR(100) NOT NULL,
    material VARCHAR(100) NOT NULL,
    brand_name VARCHAR(100) NOT NULL,
    brand_country VARCHAR(100) NOT NULL,
    msrp DECIMAL(10,2) NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    total_options_cost DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (catalog_id) REFERENCES catalogs(catalog_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS watch_accessories (
                                                 watch_id VARCHAR(50) NOT NULL,
    accessory_name VARCHAR(100) NOT NULL,
    accessory_cost DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (watch_id) REFERENCES watches(watch_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS service_plans (
     id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plan_id VARCHAR(50) UNIQUE NOT NULL,
    coverage_details VARCHAR(255) NOT NULL,
    expiration_date DATE NOT NULL -- Store actual expiration date (YYYY-MM-DD)
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      order_id VARCHAR(50) UNIQUE NOT NULL,
    customer_id VARCHAR(50) NOT NULL,
    catalog_id VARCHAR(50) NOT NULL,
    watch_id VARCHAR(50) NOT NULL,
    plan_id VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    payment_currency VARCHAR(10) NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    order_status VARCHAR(20) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (catalog_id) REFERENCES catalogs(catalog_id) ON DELETE CASCADE,
    FOREIGN KEY (watch_id) REFERENCES watches(watch_id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES service_plans(plan_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS repairs (
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       repair_id VARCHAR(50) UNIQUE NOT NULL,
    order_id VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    repair_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    repair_status VARCHAR(20) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
    );