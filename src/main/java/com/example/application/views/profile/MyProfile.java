package com.example.application.views.profile;

import com.example.application.data.entity.User;
import com.example.application.data.service.UserService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Route(value = "myProfile", layout = MainView.class)
@PageTitle("Profile Information")
@CssImport("./styles/views/myProfile/my-profile.css")
@RouteAlias(value = "", layout = MainView.class)
public class MyProfile extends Div {

    private Image profileImage = new Image("images/user.svg", "Avatar");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email address");
    private DatePicker dateOfBirth = new DatePicker("Birthday");
    private PhoneNumberField phone = new PhoneNumberField("Phone number");
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<User> binder = new Binder(User.class);

    private UserService userService;

    public MyProfile(UserService userService) {

        profileImage.setWidth("100px");
        profileImage.setHeight("100px");

        this.userService = userService;
        setId("jeder-kann-kochen-web-starter-view");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            showImage(event.getFileName(), buffer.getInputStream());
        });
        upload.setMaxFileSize(200 * 1024);
        add(createTitle());
        add(profileImage);
        add(upload);
        add(createFormLayout());
        add(createButtonLayout());

        bindProfile();
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> bindProfile());
        save.addClickListener(e -> {
            userService.update(binder.getBean());
            Notification.show("Saved");
        });
    }

    private void showImage(String fileName, InputStream stream) {
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            profileImage.getElement().setAttribute("src", new StreamResource(
                    fileName, () -> new ByteArrayInputStream(bytes)));
            binder.getBean().setProfileImage(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void bindProfile() {
        binder.setBean(userService.login("test@google.com", "123456"));
        if (binder.getBean().getProfileImage() != null) {
            profileImage.getElement().setAttribute("src", new StreamResource(
                    "profileImage", () -> new ByteArrayInputStream(binder.getBean().getProfileImage())));
        }

    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(firstName, lastName, dateOfBirth, phone, email);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setPattern("\\+\\d*");
            countryCode.setPreventInvalidInput(true);
            countryCode.setItems("+49", "+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225","+49","+02");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setPattern("\\d*");
            number.setPreventInvalidInput(true);
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}
