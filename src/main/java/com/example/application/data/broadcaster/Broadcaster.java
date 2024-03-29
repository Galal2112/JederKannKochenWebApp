package com.example.application.data.broadcaster;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Broadcaster {

    private static Broadcaster instance = new Broadcaster();

    private ComponentEventBus router = new ComponentEventBus(new Div());
    private ConcurrentHashMap<String, CopyOnWriteArrayList<MessageEvent>> lastMessages = new ConcurrentHashMap<>();

    public static class MessageEvent extends ComponentEvent<Div> {
        String message;
        private String room;
        private Date date;

        public MessageEvent(String room, String message, Date date) {
            super(new Div(), false);
            this.message = message;
            this.room = room;
            this.date = date;
        }

        public String getMessage() {
            return message;
        }

        public String getRoom() {
            return room;
        }

        public Date getDate() {
            return date;
        }

    }

    public static void sendMessage(String room, String message) {
        LoggerFactory.getLogger(Broadcaster.class).info("Sending message '" + message + "' in chat for " + room);
        MessageEvent event = new MessageEvent(room, message, new Date());
        instance.router.fireEvent(event);
        CopyOnWriteArrayList<MessageEvent> last = instance.lastMessages.computeIfAbsent(room,
                r -> new CopyOnWriteArrayList<>());
        last.add(event);
        if (last.size() > 15) {
            last.remove(0);
        }
    }

    public static Registration addMessageListener(ComponentEventListener<MessageEvent> messageListener) {
        return instance.router.addListener(MessageEvent.class, messageListener);
    }

    public static List<MessageEvent> getLastMessages(String room) {
        return instance.lastMessages.getOrDefault(room, new CopyOnWriteArrayList<>());
    }

}
