import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function CreateRoom() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    code: '',
    roomName: '',
    maxPlayers: '',
    status: 'active'
  });
  const [error, setError] = useState(null);

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
        status: form.status
      });
      // al crear, vamos al registro de usuario como dueño
      const idRoom = res.data.data.idRoom;
      navigate('/register', { state: { idRoom, isOwner: true } });
    } catch (err) {
      setError(err.response?.data?.message || err.message);
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Crear Sala</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Código:</label><br/>
          <input
            name="code"
            value={form.code}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Nombre de Sala:</label><br/>
          <input
            name="roomName"
            value={form.roomName}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Máx. Jugadores:</label><br/>
          <input
            name="maxPlayers"
            type="number"
            min="1"
            value={form.maxPlayers}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Status:</label><br/>
          <select name="status" value={form.status} onChange={handleChange}>
            <option value="active">active</option>
            <option value="closed">closed</option>
          </select>
        </div>
        <button type="submit" style={{ marginTop: '1rem' }}>
          Crear
        </button>
      </form>
      {error && <p style={{ color: 'red' }}>Error: {error}</p>}
    </div>
  );
}