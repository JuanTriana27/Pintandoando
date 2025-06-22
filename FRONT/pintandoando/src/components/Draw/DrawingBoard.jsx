import React, { useState, useRef, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faPencilAlt,
    faEraser,
    faTrash,
    faPalette,
    faUsers,
    faArrowLeft,
    faDownload,
    faFillDrip,
    faSlidersH,
    faSync,
    faTrophy,
    faCrown
} from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import './Draw.css';

export default function DrawingBoard() {
    const { state } = useLocation();
    const navigate = useNavigate();
    const { idPlayer, idRoom } = state || {};
    const canvasRef = useRef(null);
    const [drawing, setDrawing] = useState(false);
    const [tool, setTool] = useState('pencil');
    const [color, setColor] = useState('#ff7043');
    const [brushSize, setBrushSize] = useState(5);
    const [players, setPlayers] = useState([]);
    const [showPlayers, setShowPlayers] = useState(true);
    const [showTools, setShowTools] = useState(true);
    const [loadingPlayers, setLoadingPlayers] = useState(true);
    const [error, setError] = useState(null);

    // Configuración inicial del canvas
    useEffect(() => {
        if (!canvasRef.current) return;

        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');

        // Configuración del contexto de dibujo
        ctx.lineCap = 'round';
        ctx.lineJoin = 'round';
        ctx.strokeStyle = color;
        ctx.lineWidth = brushSize;

        // Dibuja un fondo blanco
        ctx.fillStyle = '#ffffff';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }, []);

    // Obtener jugadores de la sala
    const fetchPlayers = async () => {
        if (!idRoom) return;

        try {
            setLoadingPlayers(true);
            setError(null);

            // Obtener todos los jugadores y filtrar por sala
            const allPlayersRes = await axios.get('/player/all');

            // Filtrar jugadores en esta sala
            const playersInRoom = allPlayersRes.data.filter(
                player => player.idRoom === idRoom
            );

            // Marcamos al jugador actual
            const playersWithCurrent = playersInRoom.map(player => ({
                ...player,
                isCurrent: player.idPlayer === idPlayer
            }));

            // Ordenamos por puntaje
            const sortedPlayers = playersWithCurrent.sort((a, b) => b.score - a.score);

            setPlayers(sortedPlayers);
        } catch (err) {
            console.error('Error al cargar jugadores:', err);
            setError('Error al cargar los jugadores. Intenta recargar la página.');
        } finally {
            setLoadingPlayers(false);
        }
    };

    // Configurar intervalo de actualización
    useEffect(() => {
        // Cargar jugadores inmediatamente al montar
        fetchPlayers();

        // Configurar actualización periódica cada 10 segundos
        const interval = setInterval(fetchPlayers, 10000);

        // Limpiar intervalo al desmontar
        return () => clearInterval(interval);
    }, [idRoom]);

    // ======= FUNCIONES DE DIBUJO =======

    // Iniciar dibujo
    const startDrawing = (e) => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        const rect = canvas.getBoundingClientRect();

        ctx.beginPath();
        ctx.moveTo(
            e.clientX - rect.left,
            e.clientY - rect.top
        );

        setDrawing(true);

        if (tool === 'fill') {
            ctx.fillStyle = color;
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            setDrawing(false);
        }
    };

    // Dibujar
    const draw = (e) => {
        if (!drawing) return;

        const canvas = canvasRef.current;
        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        const rect = canvas.getBoundingClientRect();

        ctx.strokeStyle = tool === 'eraser' ? '#ffffff' : color;
        ctx.lineWidth = brushSize;

        ctx.lineTo(
            e.clientX - rect.left,
            e.clientY - rect.top
        );

        ctx.stroke();
    };

    // Detener dibujo
    const stopDrawing = () => {
        if (!drawing) return;

        const canvas = canvasRef.current;
        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        ctx.closePath();
        setDrawing(false);
    };

    // Limpiar lienzo
    const clearCanvas = () => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        ctx.fillStyle = '#ffffff';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    };

    // Descargar dibujo
    const downloadDrawing = () => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const link = document.createElement('a');
        link.download = `pintando-ando-${Date.now()}.png`;
        link.href = canvas.toDataURL('image/png');
        link.click();
    };

    // Cambiar tamaño del pincel
    const handleBrushSizeChange = (e) => {
        setBrushSize(parseInt(e.target.value));
    };

    // Cambiar color
    const handleColorChange = (newColor) => {
        setColor(newColor);
    };

    // Función para forzar actualización manual
    const refreshPlayers = () => {
        fetchPlayers();
    };

    if (!idPlayer) {
        return (
            <div className="app-container">
                <div className="particles" id="particles"></div>
                <div className="artistic-card">
                    <h2 className="section-title">Error en el tablero</h2>
                    <p className="app-subtitle">No se ha identificado al jugador correctamente.</p>
                    <button className="create-button" onClick={() => navigate('/')}>
                        Volver al inicio
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="drawing-container">
            {/* Fondo artístico */}
            <div className="drawing-particles" id="drawing-particles"></div>

            {/* Barra superior */}
            <div className="drawing-header">
                <button className="back-button" onClick={() => navigate('/')}>
                    <FontAwesomeIcon icon={faArrowLeft} /> Salir
                </button>

                <div className="room-info">
                    <h2>Sala de Dibujo #{idRoom}</h2>
                    <p>Jugando como: <span className="player-you">Tú (ID: {idPlayer})</span></p>
                </div>

                <div className="header-actions">
                    <button
                        className={`toggle-button ${showPlayers ? 'active' : ''}`}
                        onClick={() => setShowPlayers(!showPlayers)}
                    >
                        <FontAwesomeIcon icon={faUsers} />
                        <span>Jugadores ({players.length})</span>
                    </button>

                    <button
                        className="refresh-button"
                        onClick={refreshPlayers}
                        disabled={loadingPlayers}
                    >
                        <FontAwesomeIcon icon={faSync} spin={loadingPlayers} />
                        <span>Actualizar</span>
                    </button>

                    <button
                        className={`toggle-button ${showTools ? 'active' : ''}`}
                        onClick={() => setShowTools(!showTools)}
                    >
                        <FontAwesomeIcon icon={faSlidersH} />
                        <span>Herramientas</span>
                    </button>
                </div>
            </div>

            <div className="drawing-content">
                {/* Panel de jugadores */}
                {showPlayers && (
                    <div className="players-panel">
                        <div className="panel-header">
                            <h3>
                                <FontAwesomeIcon icon={faUsers} /> Jugadores en Sala
                            </h3>
                            <span className="player-count">{players.length} jugadores</span>
                        </div>

                        {error ? (
                            <div className="error-message">
                                <p>{error}</p>
                                <button className="refresh-button" onClick={refreshPlayers}>
                                    <FontAwesomeIcon icon={faSync} /> Reintentar
                                </button>
                            </div>
                        ) : loadingPlayers ? (
                            <div className="loading-players">
                                <FontAwesomeIcon icon={faSync} spin />
                                <p>Cargando jugadores...</p>
                            </div>
                        ) : players.length === 0 ? (
                            <div className="empty-players">
                                <p>No hay jugadores en esta sala</p>
                                <button className="refresh-button" onClick={refreshPlayers}>
                                    <FontAwesomeIcon icon={faSync} /> Intentar de nuevo
                                </button>
                            </div>
                        ) : (
                            <div className="players-list">
                                {players.map(player => (
                                    <div
                                        key={player.idPlayer}
                                        className={`player-item ${player.isCurrent ? 'you' : ''}`}
                                    >
                                        <div className="player-avatar">
                                            {player.playerName.charAt(0)}
                                        </div>
                                        <div className="player-info">
                                            <div className="player-name">
                                                {player.playerName}
                                                {player.isCurrent && <span> (Tú)</span>}
                                                {player.isOwner && <span className="owner-badge"><FontAwesomeIcon icon={faCrown} /> Dueño</span>}
                                            </div>
                                            <div className="player-status">
                                                <span className="player-score">
                                                    <FontAwesomeIcon icon={faTrophy} /> {player.score} pts
                                                </span>
                                                <span className="player-joined">
                                                    Unido: {new Date(player.createdAt).toLocaleTimeString()}
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                )}

                {/* Área de dibujo principal */}
                <div className="drawing-area">
                    <div className="canvas-container">
                        <canvas
                            ref={canvasRef}
                            width={800}
                            height={500}
                            onMouseDown={startDrawing}
                            onMouseMove={draw}
                            onMouseUp={stopDrawing}
                            onMouseLeave={stopDrawing}
                            onTouchStart={(e) => startDrawing(e.touches[0])}
                            onTouchMove={(e) => draw(e.touches[0])}
                            onTouchEnd={stopDrawing}
                        />
                    </div>
                </div>

                {/* Panel de herramientas */}
                {showTools && (
                    <div className="tools-panel">
                        <h3>
                            <FontAwesomeIcon icon={faPencilAlt} /> Herramientas
                        </h3>

                        <div className="tool-group">
                            <button
                                className={`tool-button ${tool === 'pencil' ? 'active' : ''}`}
                                onClick={() => setTool('pencil')}
                            >
                                <FontAwesomeIcon icon={faPencilAlt} />
                                <span>Lápiz</span>
                            </button>

                            <button
                                className={`tool-button ${tool === 'eraser' ? 'active' : ''}`}
                                onClick={() => setTool('eraser')}
                            >
                                <FontAwesomeIcon icon={faEraser} />
                                <span>Borrador</span>
                            </button>

                            <button
                                className={`tool-button ${tool === 'fill' ? 'active' : ''}`}
                                onClick={() => setTool('fill')}
                            >
                                <FontAwesomeIcon icon={faFillDrip} />
                                <span>Relleno</span>
                            </button>
                        </div>

                        <div className="tool-group">
                            <div className="tool-label">
                                <FontAwesomeIcon icon={faPalette} /> Color
                            </div>
                            <div className="color-palette">
                                {['#ff7043', '#7e57c2', '#26c6da', '#66bb6a', '#ffca28', '#78909c', '#000000', '#ffffff'].map(c => (
                                    <div
                                        key={c}
                                        className={`color-option ${color === c ? 'selected' : ''}`}
                                        style={{ backgroundColor: c }}
                                        onClick={() => handleColorChange(c)}
                                    />
                                ))}
                            </div>
                        </div>

                        <div className="tool-group">
                            <div className="tool-label">
                                <FontAwesomeIcon icon={faSlidersH} /> Tamaño: {brushSize}px
                            </div>
                            <input
                                type="range"
                                min="1"
                                max="30"
                                value={brushSize}
                                onChange={handleBrushSizeChange}
                                className="brush-slider"
                            />
                        </div>

                        <div className="tool-group actions">
                            <button className="action-button clear" onClick={clearCanvas}>
                                <FontAwesomeIcon icon={faTrash} /> Limpiar
                            </button>
                            <button className="action-button save" onClick={downloadDrawing}>
                                <FontAwesomeIcon icon={faDownload} /> Guardar
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}