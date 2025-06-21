import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function RoomList() {
  const [rooms, setRooms] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('/room/all')
      .then(res => setRooms(res.data))
      .catch(err => setError(err.response?.data?.message || err.message));
  }, []);

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Salas Disponibles</h2>
      {rooms.length === 0 && <p>No hay salas aún.</p>}
      <ul>
        {rooms.map(room => (
          <li key={room.idRoom} style={{ margin: '0.5rem 0' }}>
            <strong>{room.roomName}</strong> (Código: {room.code}) —{' '}
            {room.status === 'active' 
              ? (
                <button
                  onClick={() =>
                    navigate('/register', { state: { idRoom: room.idRoom, isOwner: false } })
                  }
                >
                  Ingresar
                </button>
              )
              : <em>cerrada</em>
            }
          </li>
        ))}
      </ul>
    </div>
  );
}