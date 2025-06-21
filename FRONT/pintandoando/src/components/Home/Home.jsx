import { useNavigate } from 'react-router-dom';

export default function Home() {
    const navigate = useNavigate();

    return (
        <div style={{ padding: '2rem' }}>
            <h1>Pinturillo</h1>
            <button onClick={() => navigate('/create')}>
                Crear sala
            </button>
            <button onClick={() => navigate('/rooms')} style={{ marginLeft: '1rem' }}>
                Ver salas
            </button>
        </div>
    );
}