/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doca_java.Controller.Chat;


import jakarta.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class WebSocketSessionManager {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
    }

    public static void sendMessageToAll(String message) throws IOException {
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        }
    }
}
