// RoomList.jsx
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './List.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDoorOpen, faArrowLeft, faSearch, faPaintBrush } from '@fortawesome/free-solid-svg-icons';

export default function RoomList() {
  const [rooms, setRooms] = useState([]);
  const [filteredRooms, setFilteredRooms] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('/room/all')
      .then(res => {
        setRooms(res.data);
        setFilteredRooms(res.data);
      })
      .catch(err => setError(err.response?.data?.message || err.message));
  }, []);

  useEffect(() => {
    if (searchTerm) {
      const filtered = rooms.filter(room =>
        room.roomName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        room.code.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setFilteredRooms(filtered);
    } else {
      setFilteredRooms(rooms);
    }
  }, [searchTerm, rooms]);

  if (error) {
    return (
      <div className="app-container">
        <div className="particles" id="particles"></div>
        <div className="artistic-card">
          <h2 className="section-title">Error</h2>
          <p className="app-subtitle">{error}</p>
          <button className="create-button" onClick={() => navigate('/')}>
            Volver al inicio
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="app-container">
      <div className="particles" id="particles"></div>
      <div className="artistic-card">
        <button className="back-button" onClick={() => navigate('/')}>
          <FontAwesomeIcon icon={faArrowLeft} /> Volver
        </button>

        <h2 className="section-title">
          <FontAwesomeIcon icon={faDoorOpen} /> Salas Disponibles
        </h2>

        <div className="search-container">
          <div className="search-input">
            <FontAwesomeIcon icon={faSearch} className="search-icon" />
            <input
              type="text"
              placeholder="Buscar salas..."
              className="form-input"
              value={searchTerm}
              onChange={e => setSearchTerm(e.target.value)}
            />
          </div>
        </div>

        <div className="rooms-container">
          {filteredRooms.length === 0 ? (
            <div className="empty-state">
              <FontAwesomeIcon icon={faPaintBrush} size="3x" />
              <p>No hay salas disponibles. ¡Crea una nueva!</p>
              <button className="create-button" onClick={() => navigate('/create')}>
                Crear Sala
              </button>
            </div>
          ) : (
            <div className="rooms-grid">
              {filteredRooms.map(room => (
                <div
                  key={room.idRoom}
                  className={`room-card ${room.status === 'closed' ? 'closed' : ''}`}
                  onClick={() => {
                    if (room.status === 'active') {
                      navigate('/register', { state: { idRoom: room.idRoom, isOwner: false } });
                    }
                  }}
                  style={{ borderLeft: `5px solid ${room.status === 'active' ? '#66bb6a' : '#ff7043'}` }}
                >
                  <div className="room-info">
                    <h3>{room.roomName}</h3>
                    <p>Código: {room.code}</p>
                    <p>Estado: {room.status === 'active' ? 'Activa' : 'Cerrada'}</p>
                  </div>
                  <div className="room-action">
                    {room.status === 'active' ? (
                      <button className="join-button">
                        <FontAwesomeIcon icon={faDoorOpen} /> Unirse
                      </button>
                    ) : (
                      <span className="closed-label">Cerrada</span>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}