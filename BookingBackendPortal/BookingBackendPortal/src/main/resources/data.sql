-- Insert default users
INSERT INTO users (username, password, enabled, created_at, updated_at) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', true, NOW(), NOW()),
('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', true, NOW(), NOW());

-- Insert roles
INSERT INTO user_roles (user_id, role) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_USER');

-- Insert sample resources
INSERT INTO resources (name, type, description, capacity, active, created_at, updated_at) VALUES
('Conference Room A', 'ROOM', 'Large conference room with projector', 20, true, NOW(), NOW()),
('Meeting Room B', 'ROOM', 'Small meeting room for 4 people', 4, true, NOW(), NOW()),
('Company Car', 'VEHICLE', 'Toyota Camry for business trips', 5, true, NOW(), NOW()),
('Projector', 'EQUIPMENT', 'HD Projector for presentations', 1, true, NOW(), NOW());