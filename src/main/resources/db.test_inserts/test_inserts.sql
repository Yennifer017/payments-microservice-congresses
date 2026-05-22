
INSERT INTO wallet (id, user_id, currency) VALUES
    (1, 10, 1500.00),
    (2, 11, 850.00),
    (3, 12, 2300.00),
    (4, 13, 1200.00),
    (5, 14, 450.00),
    (6, 15, 980.00),
    (7, 16, 1750.00);

INSERT INTO payment (id, wallet_id, congress_id, description, amount, commission, created_at) VALUES
    -- Usuario 10: Christopher Robin
    (
        1,
        1,
        1,
        'Pago de inscripción al Congreso Internacional de Inteligencia Artificial 2026',
        450.00,
        22.50,
        '2026-04-22'
    ),
    (
        2,
        1,
        4,
        'Pago de inscripción al Congreso de Innovación Educativa',
        200.00,
        10.00,
        '2026-05-19'
    ),

    -- Usuario 11: Ellie Fredricksen
    (
        3,
        2,
        3,
        'Inscripción al Congreso Centroamericano de Ingeniería de Software',
        350.00,
        17.50,
        '2026-05-25'
    ),

    -- Usuario 12: Imelda Rivera
    (
        4,
        3,
        2,
        'Pago de asistencia al Congreso Nacional de Desarrollo de Software',
        600.00,
        30.00,
        '2026-04-27'
    ),
    (
        5,
        3,
        5,
        'Registro al Congreso de Ciberseguridad y Datos',
        500.00,
        25.00,
        '2026-05-18'
    ),

    -- Usuario 13: Alfredo Linguini
    (
        6,
        4,
        1,
        'Registro temprano para congreso de IA',
        450.00,
        18.00,
        '2026-05-02'
    ),
    (
        7,
        4,
        6,
        'Participación en Congreso Internacional de Tecnología',
        300.00,
        15.00,
        '2026-04-26'
    ),

    -- Usuario 14: Jesús Alzamirano
    (
        8,
        5,
        4,
        'Inscripción estudiantil al Congreso de Innovación Educativa',
        200.00,
        8.00,
        '2026-04-30'
    ),

    -- Usuario 15: William Cipher
    (
        9,
        6,
        3,
        'Participación en Congreso Centroamericano de Ingeniería de Software',
        350.00,
        17.50,
        '2026-05-15'
    ),
    (
        10,
        6,
        5,
        'Pago de inscripción a Congreso de Ciberseguridad y Datos',
        500.00,
        25.00,
        '2026-04-12'
    ),

    -- Usuario 16: Hipo Abadejo
    (
        11,
        7,
        1,
        'Inscripción premium a Congreso Internacional de Inteligencia Artificial 2026',
        450.00,
        27.00,
        '2026-04-26'
    ),
    (
        12,
        7,
        2,
        'Asistencia al Congreso Nacional de Desarrollo de Software',
        600.00,
        36.00,
        '2026-03-29'
    ),
    (
        13,
        7,
        6,
        'Registro al Congreso Internacional de Tecnología',
        300.00,
        15.00,
        '2026-05-21'
    );