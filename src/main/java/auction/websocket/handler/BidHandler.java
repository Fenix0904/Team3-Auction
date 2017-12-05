package auction.websocket.handler;

import auction.utils.LotException;
import auction.domain.Bid;
import auction.domain.Lot;
import auction.service.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static auction.utils.LotException.ErrorCode.ANOTHER_CURRENT_PRICE;

@Component
public class BidHandler extends TextWebSocketHandler {

    @Autowired
    private BidService bidService;

    private Map<WebSocketSession, Integer> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Bid bid = mapper.readValue(message.getPayload(), Bid.class);
        Lot lot = null;
        try {
            lot = bidService.makeBid(bid);
        } catch (LotException e) {
            if (e.getErrorCode() == ANOTHER_CURRENT_PRICE) {
                session.sendMessage(new BinaryMessage("Lot price has been changed!".getBytes()));
            }
        }
        sessions.put(session, lot.getId());
        System.out.println(message.getPayload());
        for (Map.Entry<WebSocketSession, Integer> entry : sessions.entrySet()) {
            if (lot.getId() == entry.getValue()) {
                entry.getKey().sendMessage(new TextMessage(String.valueOf(lot.getId())));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Removing session " + session.getId());
        sessions.remove(session);
    }
}
