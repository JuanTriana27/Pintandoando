// Home.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faPlusCircle, 
  faDoorOpen,
  faTrophy,
} from '@fortawesome/free-solid-svg-icons';
import './Home.css';

export default function Home() {
  const navigate = useNavigate();

  useEffect(() => {
    // Crear partículas para el fondo
    const particlesContainer = document.getElementById('particles');
    const particleCount = 30;
    
    for (let i = 0; i < particleCount; i++) {
      const particle = document.createElement('div');
      particle.classList.add('particle');
      
      // Tamaño y posición aleatorios
      const size = Math.random() * 20 + 5;
      const posX = Math.random() * 100;
      const posY = Math.random() * 100;
      const delay = Math.random() * 15;
      
      particle.style.width = `${size}px`;
      particle.style.height = `${size}px`;
      particle.style.left = `${posX}%`;
      particle.style.top = `${posY}%`;
      particle.style.animationDelay = `${delay}s`;
      
      particlesContainer.appendChild(particle);
    }
    
    // Limpieza al desmontar
    return () => {
      particlesContainer.innerHTML = '';
    };
  }, []);

  return (
    <div className="app-container">
      {/* Fondo de partículas */}
      <div className="particles" id="particles"></div>
      
      <div className="artistic-card">
        <h1 className="app-title">🎨 Pintando Ando</h1>
        <p className="app-subtitle">
          La plataforma creativa para dibujar, colaborar y compartir tus creaciones artísticas
        </p>
        
        <div className="creative-nav">
          <div className="nav-item" onClick={() => navigate('/create')}>
            <div className="nav-icon">
              <FontAwesomeIcon icon={faPlusCircle} />
            </div>
            <div className="nav-title">Crear Sala</div>
            <div className="nav-description">Empieza un nuevo proyecto</div>
          </div>
          
          <div className="nav-item" onClick={() => navigate('/rooms')}>
            <div className="nav-icon">
              <FontAwesomeIcon icon={faDoorOpen} />
            </div>
            <div className="nav-title">Ver Salas</div>
            <div className="nav-description">Explora creaciones</div>
          </div>

          <div className="nav-item" onClick={() => navigate('/scores')}>
            <div className="nav-icon">
              <FontAwesomeIcon icon={faTrophy} />
            </div>
            <div className="nav-title">Ver Puntajes</div>
            <div className="nav-description">Consulta los puntajes de los usuarios</div>
          </div>
        </div>

        
        <div className="app-footer">
          <p>© {new Date().getFullYear()} Pintando Ando - Todos los derechos reservados</p>
        </div>
      </div>
    </div>
  );
}