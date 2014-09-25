package cn.dreampie.web.websocket;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by ice on 14-9-17.
 */
public class MessageServerConfigurator extends ServerEndpointConfig.Configurator {

  @Override
  public void modifyHandshake(ServerEndpointConfig conf,
                              HandshakeRequest req,
                              HandshakeResponse resp) {

    conf.getUserProperties().put("handshakereq", req);
  }

}