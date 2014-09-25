package cn.dreampie.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by ice on 14-9-25.
 */
public class WebsocketClient {
  private static final Logger logger = LoggerFactory.getLogger(WebsocketClient.class);
  private Session session;
  private String clientid;

  public WebsocketClient(String url, String clentid) {
    connect(url, clentid);
  }

  private void connect(String url, String clentid) {

    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    if (!url.endsWith("/")) {
      url += "/" + clentid;
    } else {
      url += clentid;
    }
    logger.info("Connecting to " + url);
    try {
      clientid = clentid;
      session = container.connectToServer(MessageClient.class, URI.create(url));
    } catch (DeploymentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void send(String receiverid, String message) {
    if (receiverid == null || receiverid.isEmpty()) {
      logger.info("Send: {}", "not receiver");
      return;
    }
    try {
      session.getBasicRemote().sendObject(new Message(clientid, receiverid, message));
      logger.info("Send: {}", message);

    } catch (IOException e) {
      logger.error(e.toString());
    } catch (EncodeException e) {
      logger.error(e.toString());
    }
  }

  public void sendAll(String message) {
    try {
      session.getBasicRemote().sendObject(new Message(clientid, "all", message));
      logger.info("Send: {}", message);

    } catch (IOException e) {
      logger.error(e.toString());
    } catch (EncodeException e) {
      logger.error(e.toString());
    }
  }

  public static void main(String args[]) {
    WebsocketClient client = new WebsocketClient("ws://localhost:9090/im", "1");

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = "";
    String[] inputs = null;
    try {
      do {
        input = br.readLine();
        if (!input.equals("exit")) {
          if (input.contains(":")) {
            inputs = input.split(":");
            client.send(inputs[0], inputs[1]);
          }
        }
      } while (!input.equals("exit"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
