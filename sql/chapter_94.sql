create database chapter_94;

use chapter_94;

CREATE TABLE item (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL UNIQUE,
                      type VARCHAR(20) NOT NULL,
                      price INT,
                      cpu VARCHAR(30),
                      capacity VARCHAR(30)
);

INSERT INTO item(name, type, price, cpu, capacity)
VALUES
    ('Apple iPhone 12 Pro Max', 'Smartphone', 1490000, 'A14 Bionic', '512GB'),
    ('Samsung Galaxy S21 Ultra', 'Smartphone', 1690000, 'Exynos 2100', '256GB'),
    ('Google Pixel 6 Pro', 'Smartphone', 1290000, 'Google Tensor', '128GB'),
    ('Dell XPS 15', 'Laptop', 2290000, 'Intel Core i9', '1TB SSD'),
    ('Sony Alpha 7 III', 'Mirrorless Camera', 2590000, 'BIONZ X', 'No internal storage'),
    ('Microsoft Xbox Series X', 'Gaming Console', 499000, 'Custom AMD Zen 2', '1TB SSD');
    
SELECT * FROM item;

