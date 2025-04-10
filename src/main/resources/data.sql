


INSERT INTO customers (id, customer_id, last_name, first_name, email_address, street_address, postal_code, city, province,
                       username, password)
VALUES
    (1, '123e4567-e89b-12d3-a456-556642440000', 'Smith', 'John', 'john.smith@example.com', '123 Maple St', 'M1M 1M1', 'Toronto', 'Ontario', 'sjohn', 'pwd1'),
    (2, '223e4567-e89b-12d3-a456-556642440001', 'Johnson', 'Emily', 'emily.johnson@example.com', '456 Oak Ave', 'V6B 2W1', 'Vancouver', 'British Columbia', 'ejohnson', 'pwd2'),
    (3, '323e4567-e89b-12d3-a456-556642440002', 'Wong', 'Michael', 'michael.wong@example.com', '789 Elm St', 'H3H 2N2', 'Montreal', 'Quebec', 'mwong', 'pwd3'),
    (4, '423e4567-e89b-12d3-a456-556642440003', 'Patel', 'Sara', 'sara.patel@example.com', '321 Pine St', 'T2N 4T4', 'Calgary', 'Alberta', 'spatel', 'pwd4'),
    (5, '523e4567-e89b-12d3-a456-556642440004', 'Lee', 'David', 'david.lee@example.com', '987 Cedar Ave', 'K1K 7L7', 'Ottawa', 'Ontario', 'dlee', 'pwd5'),
    (6, '623e4567-e89b-12d3-a456-556642440005', 'Singh', 'Alisha', 'alisha.singh@example.com', '741 Birch St', 'L5A 1X2', 'Mississauga', 'Ontario', 'asingh', 'pwd6'),
    (7, '723e4567-e89b-12d3-a456-556642440006', 'Chen', 'Jason', 'jason.chen@example.com', '852 Elmwood Dr', 'B3A 2K6', 'Halifax', 'Nova Scotia', 'jchen', 'pwd7'),
    (8, '823e4567-e89b-12d3-a456-556642440007', 'Garcia', 'Sophia', 'sophia.garcia@example.com', '963 Spruce Rd', 'G1P 3T5', 'Quebec City', 'Quebec', 'sgarcia', 'pwd8'),
    (9, '923e4567-e89b-12d3-a456-556642440008', 'Martinez', 'Daniel', 'daniel.martinez@example.com', '654 Oak Ln', 'E1A 4R7', 'Fredericton', 'New Brunswick', 'dmartinez', 'pwd9'),
    (10, 'a23e4567-e89b-12d3-a456-556642440009', 'Kim', 'Jessica', 'jessica.kim@example.com', '852 Pinecrest Blvd', 'A1A 5W3', 'St. Johns', 'Newfoundland', 'jkim', 'pwd10');

-- Insert 10 phone numbers (one per customer)
INSERT INTO customer_phonenumbers(customer_id, type, number)
VALUES
    ('123e4567-e89b-12d3-a456-556642440000', 'MOBILE', '555-1000'),
    ('223e4567-e89b-12d3-a456-556642440001', 'HOME', '555-2000'),
    ('323e4567-e89b-12d3-a456-556642440002', 'MOBILE', '555-3000'),
    ('423e4567-e89b-12d3-a456-556642440003', 'FAX', '555-4000'),
    ('523e4567-e89b-12d3-a456-556642440004', 'HOME', '555-5000'),
    ('623e4567-e89b-12d3-a456-556642440005', 'MOBILE', '555-6000'),
    ('723e4567-e89b-12d3-a456-556642440006', 'FAX', '555-7000'),
    ('823e4567-e89b-12d3-a456-556642440007', 'MOBILE', '555-8000'),
    ('923e4567-e89b-12d3-a456-556642440008', 'FAX', '555-9000'),
    ('a23e4567-e89b-12d3-a456-556642440009', 'HOME', '555-0000');

-- Insert 10 catalogs
INSERT INTO catalogs (id, catalog_id, type, description)
VALUES
    (1, 'catalog-001', 'Smart Watch', 'Intelligent connected watches'),
    (2, 'catalog-002', 'Luxury Watch', 'Premium handcrafted watches'),
    (3, 'catalog-003', 'Diving Watch', 'Designed for underwater use'),
    (4, 'catalog-004', 'Vintage Watch', 'Classic watches with authenticity'),
    (5, 'catalog-005', 'Sports Watch', 'High durability for active users'),
    (6, 'catalog-006', 'Hybrid Watch', 'Combination of digital and mechanical'),
    (7, 'catalog-007', 'Chronograph', 'High-precision timekeeping features'),
    (8, 'catalog-008', 'Pocket Watch', 'Traditional pocket-style watches'),
    (9, 'catalog-009', 'Smart Fitness Watch', 'Tracks health metrics'),
    (10, 'catalog-010', 'Aviation Watch', 'Designed for pilots and frequent flyers');

-- Insert 10 watches (linked to catalogs)
INSERT INTO watches (id, watch_id, catalog_id, watch_status, usage_type, model, material, brand_name, brand_country, msrp, cost, total_options_cost)
VALUES
    (1, 'WCH-001', 'catalog-001', 'AVAILABLE', 'NEW', 'Apple Watch Ultra', 'Aluminum', 'Apple', 'USA', 1200.00, 1000.00, 200.00),
    (2, 'WCH-002', 'catalog-002', 'AVAILABLE', 'USED', 'Omega Speedmaster', 'Titanium', 'Omega', 'Switzerland', 8000.00, 7000.00, 500.00),
    (3, 'WCH-003', 'catalog-003', 'AVAILABLE', 'NEW', 'Rolex Sea-Dweller', 'Steel', 'Rolex', 'Switzerland', 12000.00, 10000.00, 800.00),
    (4, 'WCH-004', 'catalog-004', 'AVAILABLE', 'USED', 'Tag Heuer Monaco', 'Carbon', 'Tag Heuer', 'Switzerland', 6000.00, 5000.00, 400.00),
    (5, 'WCH-005', 'catalog-005', 'AVAILABLE', 'NEW', 'Casio G-Shock', 'Rubber', 'Casio', 'Japan', 400.00, 300.00, 100.00),
    (6, 'WCH-006', 'catalog-006', 'AVAILABLE', 'NEW', 'Fossil Hybrid', 'Stainless Steel', 'Fossil', 'USA', 300.00, 250.00, 50.00),
    (7, 'WCH-007', 'catalog-007', 'AVAILABLE', 'NEW', 'Breitling Navitimer', 'Steel', 'Breitling', 'Switzerland', 10000.00, 8500.00, 600.00),
    (8, 'WCH-008', 'catalog-008', 'AVAILABLE', 'USED', 'Hamilton Khaki', 'Brass', 'Hamilton', 'USA', 700.00, 500.00, 200.00),
    (9, 'WCH-009', 'catalog-009', 'AVAILABLE', 'NEW', 'Fitbit Charge 5', 'Plastic', 'Fitbit', 'USA', 150.00, 100.00, 50.00),
    (10, 'WCH-010', 'catalog-010', 'AVAILABLE', 'NEW', 'Garmin Aviator', 'Titanium', 'Garmin', 'USA', 1500.00, 1200.00, 300.00);

INSERT INTO watch_accessories (watch_id, accessory_name, accessory_cost)
VALUES
    ('WCH-001', 'Sapphire Crystal', 120.00),
    ('WCH-001', 'Titanium Sport Band', 80.00),  -- Total for WCH-001 = 200.00
    ('WCH-002', 'Leather Strap', 250.00),
    ('WCH-002', 'Titanium Bracelet', 250.00),  -- Total for WCH-002 = 500.00
    ('WCH-003', 'Chronograph Movement', 400.00),
    ('WCH-003', 'Anti-Reflective Coating', 400.00),  -- Total for WCH-003 = 800.00
    ('WCH-004', 'Rubber Strap', 200.00),
    ('WCH-004', 'Sapphire Glass', 200.00),  -- Total for WCH-004 = 400.00
    ('WCH-005', 'Shock Resistant Casing', 50.00),
    ('WCH-005', 'Protective Bezel', 50.00),  -- Total for WCH-005 = 100.00
    ('WCH-006', 'Hybrid Digital Display', 50.00),  -- Total for WCH-006 = 50.00
    ('WCH-007', 'Slide Rule Bezel', 300.00),
    ('WCH-007', 'Dual Time Function', 300.00),  -- Total for WCH-007 = 600.00

    ('WCH-008', 'Luminous Hands', 100.00),
    ('WCH-008', 'Scratch Resistant Coating', 100.00),  -- Total for WCH-008 = 200.00

    ('WCH-009', 'Silicone Sports Band', 25.00),
    ('WCH-009', 'Heart Rate Sensor Upgrade', 25.00),  -- Total for WCH-009 = 50.00

    ('WCH-010', 'Aviation Compass Dial', 200.00),
    ('WCH-010', 'Flight Computer Function', 100.00);  -- Total for WCH-010 = 300.00


INSERT INTO service_plans (plan_id, coverage_details, expiration_date)
VALUES
    ('SP-001', 'Full coverage for 1 year', '2026-03-16'),
    ('SP-002', 'Extended warranty (2 years)', '2027-05-10'),
    ('SP-003', 'Basic protection plan (6 months)', '2025-12-01'),
    ('SP-004', 'Accidental damage protection', '2026-09-25'),
    ('SP-005', 'Water resistance guarantee', '2027-07-14'),
    ('SP-006', 'Premium maintenance package', '2028-04-30'),
    ('SP-007', 'Battery replacement coverage', '2026-12-20'),
    ('SP-008', 'Software update warranty', '2027-11-05'),
    ('SP-009', 'Strap replacement coverage', '2026-08-01'),
    ('SP-010', 'Glass and screen protection', '2028-02-18');


INSERT INTO orders (order_id, customer_id, catalog_id, watch_id, plan_id, amount, currency, payment_currency, order_date, order_status)
VALUES
    ('ORD-001', '123e4567-e89b-12d3-a456-556642440000', 'catalog-001', 'WCH-001', 'SP-001', 1200.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-002', '223e4567-e89b-12d3-a456-556642440001', 'catalog-002', 'WCH-002', 'SP-002', 8000.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-003', '323e4567-e89b-12d3-a456-556642440002', 'catalog-003', 'WCH-003', 'SP-003', 12000.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-004', '423e4567-e89b-12d3-a456-556642440003', 'catalog-004', 'WCH-004', 'SP-004', 6000.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-005', '523e4567-e89b-12d3-a456-556642440004', 'catalog-005', 'WCH-005', 'SP-005', 400.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-006', '623e4567-e89b-12d3-a456-556642440005', 'catalog-006', 'WCH-006', 'SP-006', 300.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-007', '723e4567-e89b-12d3-a456-556642440006', 'catalog-007', 'WCH-007', 'SP-007', 10000.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED'),
    ('ORD-008', '823e4567-e89b-12d3-a456-556642440007', 'catalog-008', 'WCH-008', 'SP-008', 700.00, 'USD', 'CAD', NOW(), 'PURCHASE_COMPLETED');








INSERT INTO repairs (repair_id, order_id, description, repair_date, amount, currency, repair_status)
VALUES
    ('REP-001', 'ORD-001', 'Battery replacement.', NOW(), 250.00, 'USD', 'IN_PROGRESS'),
    ('REP-002', 'ORD-002', 'Strap replacement.', NOW(), 150.00, 'USD', 'PENDING'),
    ('REP-003', 'ORD-003', 'Glass replacement.', NOW(), 300.00, 'USD', 'COMPLETED'),
    ('REP-004', 'ORD-004', 'Water damage fix.', NOW(), 500.00, 'USD', 'IN_PROGRESS'),
    ('REP-005', 'ORD-005', 'Screen replacement.', NOW(), 100.00, 'USD', 'PENDING'),
    ('REP-006', 'ORD-006', 'Software update.', NOW(), 50.00, 'USD', 'COMPLETED'),
    ('REP-007', 'ORD-007', 'Crown replacement.', NOW(), 200.00, 'USD', 'IN_PROGRESS'),
    ('REP-008', 'ORD-008', 'Dial repainting.', NOW(), 180.00, 'USD', 'PENDING');
