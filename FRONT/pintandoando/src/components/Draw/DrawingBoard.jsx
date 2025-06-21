import { useLocation } from 'react-router-dom'

export default function DrawingBoard() {
    const { state } = useLocation()
    const { idPlayer, idRoom } = state || {}

    if (!idPlayer) {
        return <p>Error: jugador no registrado.</p>
    }

    return (
        <div style={{ padding: '2rem' }}>
            <h2>Tablero de Dibujo</h2>
            <p>Jugador #{idPlayer} en Sala #{idRoom}</p>
            {/* Aquí meterás tu lógica/canvas de dibujo */}
            <canvas
                id="drawing-canvas"
                width={600}
                height={400}
                style={{ border: '1px solid #ccc', background: '#fff', cursor: 'crosshair' }}
                ref={canvas => {
                    if (!canvas) return
                    let drawing = false
                    let ctx = canvas.getContext('2d')

                    const startDraw = e => {
                        drawing = true
                        const rect = canvas.getBoundingClientRect()
                        ctx.beginPath()
                        ctx.moveTo(e.clientX - rect.left, e.clientY - rect.top)
                    }

                    const draw = e => {
                        if (!drawing) return
                        const rect = canvas.getBoundingClientRect()
                        ctx.lineTo(e.clientX - rect.left, e.clientY - rect.top)
                        ctx.stroke()
                    }

                    const stopDraw = () => {
                        drawing = false
                        ctx.closePath()
                    }

                    // Remove previous listeners to avoid stacking
                    canvas.onmousedown = startDraw
                    canvas.onmousemove = draw
                    canvas.onmouseup = stopDraw
                    canvas.onmouseleave = stopDraw
                }}
            />
        </div>
    )
}