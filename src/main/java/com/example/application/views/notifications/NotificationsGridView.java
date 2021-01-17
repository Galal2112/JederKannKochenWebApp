package com.example.application.views.notifications;

import com.example.application.data.broadcaster.Broadcaster;
import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.data.entity.UserNotification;
import com.example.application.data.service.UserNotificationService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UIDetachedException;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;


@PageTitle("My notifications")
public class NotificationsGridView extends Div implements AfterNavigationObserver {

    private UserNotificationService notificationService;

    private Grid<UserNotification> grid = new Grid<>(UserNotification.class);

    private Registration registration;

    public NotificationsGridView(@Autowired UserNotificationService notificationService) {
        setId("notifications-view");
        addClassName("notifications-view");
        this.notificationService = notificationService;
        setSizeFull();
        configureGrid();
        add(grid);

        registration = Broadcaster.addMessageListener(this::onMessage);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addComponentColumn((notification) -> {
            NotificationView notificationView = new NotificationView();
            notificationView.bind(notification);
            return notificationView;
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            Notification notification = new Notification(event.getValue().getText(), 3000);
            notification.open();
        });
        grid.removeColumnByKey("sender");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("createdAt");
        grid.removeColumnByKey("text");
        grid.removeColumnByKey("receiverId");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        refreshList();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        registration.remove();
    }

    private void refreshList() {//TODO , HIER !

        List<UserNotification> dbNotifications = notificationService.getUserNotifications(1);
        grid.setItems(dbNotifications);
        grid.getDataProvider().refreshAll();
    }

    private static class NotificationView extends VerticalLayout {
        Label nameLabel = new Label("");
        Label messageLabel = new Label("");
        Label dateLabel = new Label("");
        static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MMM yyyy HH:mm");

        NotificationView() {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(new Image("images/user.svg", "Avatar"));
            horizontalLayout.add(nameLabel);
            add(horizontalLayout);
            add(messageLabel);
            add(dateLabel);
        }

        void bind(UserNotification notification) {
            User sender = notification.getSender();
            if (sender.getRole() == Role.ADMIN) {
                nameLabel.setText("System Admin");
            } else {
                nameLabel.setText(sender.getFirstName() + " " + sender.getLastName());
            }
            messageLabel.setText(notification.getText());
            dateLabel.setText(simpleDateFormat.format(notification.getCreatedAt()));
        }
    }

    private void onMessage(Broadcaster.MessageEvent event) {
        try {
            if (!getUI().isPresent())
                return;

            getUI().get().access(() -> {
                new Notification(event.getMessage(), 3000).open();
                refreshList();
            });
        } catch (UIDetachedException e) {
            registration.remove();
        }
    }
}
