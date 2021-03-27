package com.example.application.views.notifications;

import com.example.application.data.broadcaster.Broadcaster;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserNotificationService;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Admin Notifications")
public class NotificationSender extends VerticalLayout {

    public static final String DEFAULT_ROOM = "all";

    private UserNotificationService notificationService;
    private TextArea notificationTextArea = new TextArea("Notification Message:");
    private Button send = new Button("Send");
    private User adminUser;

    public NotificationSender(@Autowired UserNotificationService notificationService, @Autowired UserService userService) {
        this.notificationService = notificationService;
        setSizeFull();
        notificationTextArea.setHeight("30%");
        notificationTextArea.setWidth("20%");
        add(notificationTextArea);
        add(send);
        send.addClickListener(e -> sendNotificationMessage());
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        adminUser = user;
    }

    private void sendNotificationMessage() {
        String text = notificationTextArea.getValue();
        if (text.isEmpty()) {
            new Notification("No data to send", 3000).open();
            return;
        }
        notificationService.sendNotificationToAllUser(adminUser, text);
        Broadcaster.sendMessage(DEFAULT_ROOM, text);
        new Notification("Sent", 3000).open();
    }
}
