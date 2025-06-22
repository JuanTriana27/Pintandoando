// RegisterUser.jsx
import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Register.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserPlus, faArrowLeft } from '@fortawesome/free-solid-svg-icons';

export default function RegisterUser() {
    const { state } = useLocation();
    const navigate = useNavigate();
    const { idRoom, isOwner } = state || {};
    const [playerName, setPlayerName] = useState('');
    const [error, setError] = useState(null);

    const handleSubmit = async e => {
        e.preventDefault();
        setError(null);
        try {
            const payload = {
                playerName,
                score: 0,
                isOwner,
                idRoom
            };
            const res = await axios.post('/player/save-new', payload);
            const idPlayer = res.data.data.idPlayer;
            navigate('/draw', { state: { idPlayer, idRoom } });
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        }
    };

    if (!idRoom || isOwner === undefined) {
        return (
            <div className="app-container">
                <div className="particles" id="particles"></div>
                <div className="artistic-card">
                    <h2 className="section-title">Error en la sala</h2>
                    <p className="app-subtitle">La sala especificada no existe o no está disponible.</p>
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
                <button className="back-button" onClick={() => navigate('/rooms')}>
                    <FontAwesomeIcon icon={faArrowLeft} /> Volver
                </button>
                
                <h2 className="section-title">
                    <FontAwesomeIcon icon={faUserPlus} /> Registro de Usuario
                </h2>
                
                <div className="room-info-badge">
                    <div>Sala: <strong>{idRoom}</strong></div>
                    <div>Rol: <strong>{isOwner ? 'Dueño' : 'Participante'}</strong></div>
                </div>
                
                <form onSubmit={handleSubmit} className="form-container">
                    <div className="form-group">
                        <label>Nombre de jugador:</label>
                        <input
                            className="form-input"
                            value={playerName}
                            onChange={e => setPlayerName(e.target.value)}
                            required
                            placeholder="Tu nombre artístico"
                        />
                    </div>
                    
                    <button type="submit" className="create-button">
                        Unirme a la Sala
                    </button>
                </form>
                
                {error && <div className="error-message">{error}</div>}
            </div>
        </div>
    );
}