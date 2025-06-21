import { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import axios from 'axios'

export default function RegisterUser() {
    const { state } = useLocation()
    const navigate = useNavigate()
    const { idRoom, isOwner } = state || {}
    const [playerName, setPlayerName] = useState('')
    const [error, setError] = useState(null)

    const handleSubmit = async e => {
        e.preventDefault()
        setError(null)
        try {
            const payload = {
                playerName,
                score: 0,
                isOwner,
                idRoom
            }
            const res = await axios.post('/player/save-new', payload)
            const idPlayer = res.data.data.idPlayer
            // una vez creado el player, vas al dibujo
            navigate('/draw', { state: { idPlayer, idRoom } })
        } catch (err) {
            setError(err.response?.data?.message || err.message)
        }
    }

    if (!idRoom || isOwner === undefined) {
        return <p>Error: sala no especificada.</p>
    }

    return (
        <div style={{ padding: '2rem' }}>
            <h2>Registro de Usuario</h2>
            <p>Sala: {idRoom} — Eres {isOwner ? 'dueño' : 'participante'}</p>
            <form onSubmit={handleSubmit}>
                <label>Nombre de jugador:</label><br />
                <input
                    value={playerName}
                    onChange={e => setPlayerName(e.target.value)}
                    required
                /><br />
                <button type="submit" style={{ marginTop: '1rem' }}>
                    Registrarme
                </button>
            </form>
            {error && <p style={{ color: 'red' }}>Error: {error}</p>}
        </div>
    )
}