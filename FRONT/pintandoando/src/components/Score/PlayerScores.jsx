import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Score.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faTrophy,
    faCrown,
    faMedal,
    faArrowLeft,
    faUser,
    faChartLine
} from '@fortawesome/free-solid-svg-icons';

export default function PlayerScores() {
    const navigate = useNavigate();
    const [scores, setScores] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [timeRange, setTimeRange] = useState('all'); // 'all', 'month', 'week'

    useEffect(() => {
        const fetchScores = async () => {
            try {
                setLoading(true);
                const res = await axios.get('/player/all');

                // Procesar datos: ordenar por puntaje y tomar top 10
                const sortedPlayers = res.data
                    .filter(player => player.score > 0)
                    .sort((a, b) => b.score - a.score)
                    .slice(0, 10);

                setScores(sortedPlayers);
                setLoading(false);
            } catch (err) {
                setError(err.response?.data?.message || 'Error al cargar puntajes');
                setLoading(false);
            }
        };

        fetchScores();
    }, [timeRange]);

    const getRankColor = (rank) => {
        switch (rank) {
            case 1: return '#ffd700'; // Oro
            case 2: return '#c0c0c0'; // Plata
            case 3: return '#cd7f32'; // Bronce
            default: return '#7e57c2';
        }
    };

    const getRankIcon = (rank) => {
        switch (rank) {
            case 1: return <FontAwesomeIcon icon={faCrown} />;
            case 2: return <FontAwesomeIcon icon={faMedal} />;
            case 3: return <FontAwesomeIcon icon={faMedal} />;
            default: return <span className="rank-number">{rank}</span>;
        }
    };

    return (
        <div className="app-container">
            <div className="particles" id="particles"></div>
            <div className="artistic-card">
                <button className="back-button" onClick={() => navigate('/')}>
                    <FontAwesomeIcon icon={faArrowLeft} /> Volver
                </button>

                <div className="scores-header">
                    <FontAwesomeIcon icon={faTrophy} className="trophy-icon" />
                    <h2 className="section-title">Top 10 Jugadores</h2>
                    <FontAwesomeIcon icon={faTrophy} className="trophy-icon" />
                </div>

                <div className="time-range-selector">
                    <button
                        className={`time-button ${timeRange === 'all' ? 'active' : ''}`}
                        onClick={() => setTimeRange('all')}
                    >
                        Todos los tiempos
                    </button>
                    <button
                        className={`time-button ${timeRange === 'month' ? 'active' : ''}`}
                        onClick={() => setTimeRange('month')}
                    >
                        Este mes
                    </button>
                    <button
                        className={`time-button ${timeRange === 'week' ? 'active' : ''}`}
                        onClick={() => setTimeRange('week')}
                    >
                        Esta semana
                    </button>
                </div>

                {loading ? (
                    <div className="loading-scores">
                        <div className="loading-spinner"></div>
                        <p>Cargando puntajes...</p>
                    </div>
                ) : error ? (
                    <div className="error-message">
                        <p>{error}</p>
                        <button className="retry-button" onClick={() => window.location.reload()}>
                            Reintentar
                        </button>
                    </div>
                ) : scores.length === 0 ? (
                    <div className="empty-scores">
                        <FontAwesomeIcon icon={faUser} size="3x" />
                        <p>¡Aún no hay puntajes registrados!</p>
                        <p>Sé el primero en jugar y subir en el ranking.</p>
                        <button className="create-button" onClick={() => navigate('/create')}>
                            Crear Sala
                        </button>
                    </div>
                ) : (
                    <div className="scores-table">
                        <div className="table-header">
                            <div className="header-rank">Posición</div>
                            <div className="header-player">Jugador</div>
                            <div className="header-score">Puntaje</div>
                        </div>

                        {scores.map((player, index) => (
                            <div
                                key={player.idPlayer}
                                className={`player-row ${index < 3 ? 'top-player' : ''}`}
                            >
                                <div
                                    className="player-rank"
                                    style={{ background: getRankColor(index + 1) }}
                                >
                                    {getRankIcon(index + 1)}
                                </div>
                                <div className="player-info">
                                    <div className="player-name">{player.playerName}</div>
                                    <div className="player-stats">
                                        <span className="player-rooms">{player.roomsPlayed || 1} salas</span>
                                        <span className="player-avg">{Math.round(player.score / (player.roomsPlayed || 1))} pts/sala</span>
                                    </div>
                                </div>
                                <div className="player-score">
                                    <FontAwesomeIcon icon={faChartLine} />
                                    <span>{player.score} pts</span>
                                </div>
                            </div>
                        ))}
                    </div>
                )}

                <div className="score-info">
                    <p>Los puntajes se actualizan automáticamente después de cada partida.</p>
                </div>
            </div>
        </div>
    );
}