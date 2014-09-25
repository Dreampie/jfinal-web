package cn.dreampie.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ice on 14-9-17.
 */
@ClientEndpoint(
    encoders = {MessageEncoder.class},
    decoders = {MessageDecoder.class}
)
public class MessageClient {
  private static final Logger logger = LoggerFactory.getLogger(MessageClient.class);

  @OnOpen
  public void open(Session session) {
    try {
      session.getBasicRemote().sendObject(new Message("Welcome"));
    } catch (IOException e) {
      logger.error(e.toString());
    } catch (EncodeException e) {
      logger.error(e.toString());
    }
    logger.info("Connection opened.login time {}", new Date());
  }


  @OnMessage
  public void message(Session session, Message msg) {
//    if (msg instanceof MessageA) {
//      // We received a MessageA object...
//    } else if (msg instanceof MessageB) {
//      // We received a MessageB object...
//    }
    if (msg != null) {
      logger.info("read message {}", msg);
    }
  }


  @OnError
  public void error(Session session, Throwable t) {
    logger.error("Connection error.get error {},get error time {}", t.toString(), new Date());
  }

  @OnClose
  public void close(Session session,
                    CloseReason reason) {
    logger.info("Connection closed.close reson {},close time {}", reason.toString(), new Date());
  }

}
