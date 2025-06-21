import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function Lobby() {
    const [playerName, setPlayerName] = useState('');
    const [roomCode, setRoomCode] = useState('');
    const [rooms, setRooms] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchRooms();
        const interval = setInterval(fetchRooms, 5000);
        return () => clearInterval(interval);
    }, []);

    const fetchRooms = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/rooms');
            setRooms(response.data);
        } catch (err) {
            setError('Error al cargar salas');
        }
    };

    const createRoom = async () => {
        if (!playerName.trim()) {
            setError('Ingresa tu nombre');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/rooms', {
                playerName,
                roomName: `${playerName}'s Room`
            });

            navigate('/game', {
                state: {
                    idPlayer: response.data.playerId,
                    idRoom: response.data.roomId,
                    playerName
                }
            });
        } catch (err) {
            setError('Error al crear sala');
        }
    };

    const joinRoom = async () => {
        if (!playerName.trim() || !roomCode.trim()) {
            setError('Completa ambos campos');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/rooms/join', {
                playerName,
                roomCode
            });

            navigate('/game', {
                state: {
                    idPlayer: response.data.playerId,
                    idRoom: response.data.roomId,
                    playerName
                }
            });
        } catch (err) {
            setError(err.response?.data || 'Error al unirse a la sala');
        }
    };

    return (
        <div className="lobby-container">
            <h1>Pinturillo</h1>

            <div className="create-section">
                <h2>Crear Sala</h2>
                <input
                    type="text"
                    placeholder="Tu nombre"
                    value={playerName}
                    onChange={(e) => setPlayerName(e.target.value)}
                />
                <button onClick={createRoom}>Crear Nueva Sala</button>
            </div>

            <div className="join-section">
                <h2>Unirse a Sala</h2>
                <input
                    type="text"
                    placeholder="Código de sala"
                    value={roomCode}
                    onChange={(e) => setRoomCode(e.target.value.toUpperCase())}
                />
                <button onClick={joinRoom}>Unirse</button>
            </div>

            {error && <p className="error">{error}</p>}

            <div className="room-list">
                <h2>Salas Disponibles</h2>
                {rooms.length === 0 ? (
                    <p>No hay salas disponibles</p>
                ) : (
                    <ul>
                        {rooms.map(room => (
                            <li key={room.id}>
                                <span>{room.name}</span>
                                <span>{room.playerCount}/{room.maxPlayers}</span>
                                <button onClick={() => {
                                    setRoomCode(room.code);
                                    document.querySelector('.join-section input').focus();
                                }}>
                                    Unirse
                                </button>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}