INSERT IGNORE INTO usuarios (id, nombres, apellidos, email, telefono, direccion, activo, fecha_registro) VALUES
(1, 'Carlos Andrés', 'Ramírez Gómez', 'carlos.ramirez@correo.com', '3104567890', 'Calle 45 # 12-33, Bogotá', true, '2026-01-15'),
(2, 'Laura Ximena', 'Torres Vargas', 'laura.torres@correo.com', '3125678901', 'Carrera 70 # 30-18, Medellín', true, '2026-02-03'),
(3, 'Juan Sebastián', 'Muñoz Rivera', 'juan.munoz@correo.com', '3016789012', 'Avenida 6N # 25-40, Cali', true, '2026-02-20'),
(4, 'Diana Marcela', 'Castillo Peña', 'diana.castillo@correo.com', '3157890123', 'Calle 72 # 41-15, Barranquilla', false, '2026-03-11'),
(5, 'Andrés Felipe', 'Ortiz Salazar', 'andres.ortiz@correo.com', '3208901234', 'Carrera 15 # 8-52, Bucaramanga', true, '2026-04-05');

INSERT IGNORE INTO carros (id, placa, marca, modelo, anio, color, precio_dia, disponible) VALUES
(1, 'HKL452', 'Chevrolet', 'Onix', 2022, 'Blanco', 145000.00, true),
(2, 'PQR781', 'Renault', 'Logan', 2021, 'Gris', 130000.00, true),
(3, 'TUV309', 'Mazda', 'CX-30', 2023, 'Rojo', 235000.00, false),
(4, 'BNM614', 'Toyota', 'Hilux', 2020, 'Negro', 310000.00, true),
(5, 'ZXC128', 'Nissan', 'Versa', 2019, 'Azul', 118000.00, true);

INSERT IGNORE INTO choferes (id, nombres, apellidos, documento, numero_licencia, categoria_licencia, telefono, fecha_vencimiento_licencia, activo) VALUES
(1, 'Miguel Ángel', 'Cárdenas Rojas', '79458123', 'LIC-2024-8845', 'C1', '3112233445', '2028-06-30', true),
(2, 'Yeison Alberto', 'Palacios Mena', '1017453298', 'LIC-2023-5512', 'C2', '3013344556', '2027-11-15', true),
(3, 'Rosa Elena', 'Quintero Ávila', '52874109', 'LIC-2025-2210', 'C1', '3204455667', '2029-03-22', true),
(4, 'Wilson Javier', 'Bermúdez Cano', '80125476', 'LIC-2022-7734', 'C3', '3155566778', '2027-08-09', false),
(5, 'Néstor Iván', 'Guerrero Lozano', '1090234871', 'LIC-2024-1198', 'C3', '3186677889', '2028-12-01', true);
