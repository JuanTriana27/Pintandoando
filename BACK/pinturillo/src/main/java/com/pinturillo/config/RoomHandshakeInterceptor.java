package com.pinturillo.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class RoomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

            // Obtener roomId y playerId de los parámetros de la solicitud
            String roomId = servletRequest.getServletRequest().getParameter("roomId");
            String playerId = servletRequest.getServletRequest().getParameter("playerId");

            if (roomId != null && playerId != null) {
                attributes.put("roomId", Integer.parseInt(roomId));
                attributes.put("playerId", Integer.parseInt(playerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // No se necesita implementación
    }
}