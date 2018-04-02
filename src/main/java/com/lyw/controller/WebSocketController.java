package com.lyw.controller;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 插件配置的地址是 ws://ip:port/robot/ws_api/cqp
 * @ProjectName maintain-robot
 * @Package com.lyw.module
 * @Author yangjh5
 * @CreateDate 2017/12/2
 */
@Slf4j
@ServerEndpoint(value = "/robot/ws_api/{user}")
public class WebSocketController {

    public static final String CQP_USER = "cqp";

    public static Map<String, Session> sessionMap = new HashMap<>();

    @OnOpen
    public void open(Session session, @PathParam(value = "user")String user) {
        Session session1 = sessionMap.get(user);
        if (null != session1) {
            try {
                session1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sessionMap.put(user, session);
        log.info("*** WebSocket opened from sessionId " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("*** WebSocket Received from sessionId " + session.getId() + ": " + message);
    }

    @OnClose
    public void end(Session session) {
        log.info("*** WebSocket closed from sessionId " + session.getId());
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            if (entry.getValue().getId().equals(session.getId())) {
                sessionMap.remove(entry.getKey());
            }
        }
    }

}
