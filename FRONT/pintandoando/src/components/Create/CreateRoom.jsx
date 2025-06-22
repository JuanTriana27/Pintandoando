// CreateRoom.jsx
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Create.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlusCircle, faArrowLeft} from '@fortawesome/free-solid-svg-icons';

export default function CreateRoom() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    code: '',
    roomName: '',
    maxPlayers: '8',
    status: 'active'
  });
  const [error, setError] = useState(null);
  const [selectedTheme, setSelectedTheme] = useState('#7e57c2');

  const themes = [
    { color: '#7e57c2', name: 'Púrpura' },
    { color: '#26c6da', name: 'Turquesa' },
    { color: '#66bb6a', name: 'Verde' },
    { color: '#ff7043', name: 'Naranja' },
    { color: '#ffca28', name: 'Amarillo' },
    { color: '#78909c', name: 'Gris' }
  ];

  const handleChange = e => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setError(null);

    try {
      const res = await axios.post('/room/save-new', {
        code: form.code,
        roomName: form.roomName,
        maxPlayers: parseInt(form.maxPlayers, 10),
        status: form.status,
        theme: selectedTheme
      });
      const idRoom = res.data.data.idRoom;
      navigate('/register', { state: { idRoom, isOwner: true } });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
    }
  };

  return (
    <div className="app-container">
      <div className="particles" id="particles"></div>
      <div className="artistic-card">
        <button className="back-button" onClick={() => navigate('/')}>
          <FontAwesomeIcon icon={faArrowLeft} /> Volver
        </button>

        <h2 className="section-title">
          <FontAwesomeIcon icon={faPlusCircle} /> Crear Nueva Sala
        </h2>

        <form onSubmit={handleSubmit} className="form-container">
          <div className="form-group">
            <label>Nombre de la Sala</label>
            <input
              className="form-input"
              name="roomName"
              value={form.roomName}
              onChange={handleChange}
              required
              placeholder="Ej: Dibujos de verano"
            />
          </div>

          <div className="form-group">
            <label>Código de Acceso</label>
            <input
              className="form-input"
              name="code"
              value={form.code}
              onChange={handleChange}
              required
              placeholder="Ej: ARTE2023"
            />
          </div>

          <div className="form-group">
            <label>Máximo de Jugadores</label>
            <select
              className="form-input"
              name="maxPlayers"
              value={form.maxPlayers}
              onChange={handleChange}
            >
              {[2, 4, 6, 8, 10, 12].map(num => (
                <option key={num} value={num}>{num} jugadores</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Tema de la Sala</label>
            <div className="theme-options">
              {themes.map(theme => (
                <div
                  key={theme.color}
                  className={`theme-option ${selectedTheme === theme.color ? 'selected' : ''}`}
                  onClick={() => setSelectedTheme(theme.color)}
                  style={{ backgroundColor: theme.color }}
                  title={theme.name}
                />
              ))}
            </div>
          </div>

          <button type="submit" className="create-button">
            Crear Sala
          </button>
        </form>

        {error && <div className="error-message">{error}</div>}
      </div>
    </div>
  );
}