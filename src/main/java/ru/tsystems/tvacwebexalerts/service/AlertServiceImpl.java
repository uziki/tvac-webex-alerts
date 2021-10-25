package ru.tsystems.tvacwebexalerts.service;

import com.ciscospark.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Iterator;

@Service
public class AlertServiceImpl implements AlertService {
    private static final Log LOGGER = LogFactory.getLog(AlertServiceImpl.class);

    @Value("${webex.bot.access.token}")
    private String accessToken;

    @Value("${webex.room.title}")
    private String roomTitle;

    @Value("${webex.api.url}")
    private String webexApiURL;

    //https://developer.webex.com/docs/sdks/java
    @Override
    public ResponseEntity<String> alert(String alertMessage) {
        LOGGER.info(String.format("Message received: %s", alertMessage));

        Spark spark = Spark.builder()
                           .baseUrl(URI.create(webexApiURL))
                           .accessToken(accessToken)
                           .build();

        Room room = new Room();
        Iterator<Room> roomIterator = spark.rooms().iterate();
        while (roomIterator.hasNext()) {
            Room currentRoom = roomIterator.next();
            if (roomTitle.equals(currentRoom.getTitle())) {
                room = currentRoom;
            }
        }
        if (room.getTitle() == null) {
            LOGGER.error(String.format("Failed to send message: Webex bot is not in room %s", roomTitle));
            return new ResponseEntity<>("Failed to send alert to webex: Webex bot is not in the right room", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Message message = new Message();
        message.setRoomId(room.getId());
        message.setText(alertMessage);
        spark.messages().post(message);
        LOGGER.info(String.format("Message sent: %s", alertMessage));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

