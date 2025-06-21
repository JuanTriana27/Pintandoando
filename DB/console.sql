-- Table Rooms
CREATE TABLE rooms(
    id_room SERIAL PRIMARY KEY,
    code VARCHAR(6) UNIQUE NOT NULL, -- Code for join in the room
    room_name VARCHAR(50) NOT NULL,
    max_players INTEGER DEFAULT 8,
    status VARCHAR(20) DEFAULT 'WAITING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tables Players
CREATE TABLE players(
    id_player SERIAL PRIMARY KEY,
    player_name VARCHAR(40) NOT NULL,
    id_room INTEGER REFERENCES rooms (id_room) ON DELETE CASCADE,
    score INTEGER DEFAULT 0,
    is_owner BOOLEAN DEFAULT FALSE, -- Room Creator
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Words
CREATE TABLE words (
    id_word SERIAL PRIMARY KEY,
    word VARCHAR(20) UNIQUE NOT NULL
);

-- Tables Rounds
CREATE TABLE rounds (
    id_round SERIAL PRIMARY KEY,
    id_room INTEGER REFERENCES rooms (id_room) ON DELETE CASCADE,
    id_word INTEGER REFERENCES words (id_word),
    id_player INTEGER REFERENCES players (id_player) ON DELETE SET NULL,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP
);

-- Table Drawing
CREATE TABLE drawings (
    id_drawing SERIAL PRIMARY KEY,
    id_round INTEGER REFERENCES rounds (id_round) ON DELETE CASCADE,
    data TEXT NOT NULL, -- JSON WITH DRAWS
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Guesses
CREATE TABLE guesses(
    id_guesses SERIAL PRIMARY KEY,
    id_round INTEGER REFERENCES rounds (id_round) ON DELETE CASCADE,
    id_player INTEGER REFERENCES players (id_player) ON DELETE CASCADE,
    guess VARCHAR(255) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar palabras para el juego
INSERT INTO words (word) VALUES
('perro'), ('gato'), ('elefante'), ('casa'), ('árbol'),
('sol'), ('montaña'), ('coche'), ('bicicleta'), ('avión'),
('barco'), ('flor'), ('libro'), ('computadora'), ('teléfono');

-- Crear salas de juego
INSERT INTO rooms (code, room_name, max_players, status) VALUES
('ABC123', 'Sala Divertida', 6, 'WAITING'),
('XYZ789', 'Artistas Unidos', 8, 'PLAYING'),
('DEF456', 'Adivina Rápido', 4, 'WAITING'),
('GHI789', 'Dibujo Fácil', 8, 'FINISHED');

-- Insertar jugadores en las salas
-- Sala 1 (ABC123)
INSERT INTO players (player_name, id_room, score, is_owner) VALUES
('María', 1, 0, TRUE),
('Carlos', 1, 0, FALSE),
('Ana', 1, 0, FALSE);

-- Sala 2 (XYZ789)
INSERT INTO players (player_name, id_room, score, is_owner) VALUES
('Pedro', 2, 120, TRUE),
('Lucía', 2, 80, FALSE),
('Jorge', 2, 95, FALSE),
('Sofía', 2, 60, FALSE);

-- Sala 3 (DEF456)
INSERT INTO players (player_name, id_room, score, is_owner) VALUES
('Miguel', 3, 0, TRUE),
('Elena', 3, 0, FALSE);

-- Sala 4 (GHI789)
INSERT INTO players (player_name, id_room, score, is_owner) VALUES
('Roberto', 4, 200, TRUE),
('Carmen', 4, 150, FALSE),
('David', 4, 180, FALSE);

-- Crear rondas de juego (ejemplo para sala en progreso)
INSERT INTO rounds (id_room, id_word, id_player, start_time, end_time) VALUES
(2, 7, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2 minutes');

-- Insertar dibujos (ejemplo de formato JSON)
INSERT INTO drawings (id_round, data) VALUES
(1, '{"strokes": [{"points": [{"x":100,"y":100},{"x":200,"y":200}], "color":"#000", "width":5}]}');

-- Insertar adivinanzas
INSERT INTO guesses (id_round, id_player, guess, is_correct) VALUES
(1, 6, 'montaña', TRUE),
(1, 7, 'colina', FALSE),
(1, 8, 'cerro', FALSE);