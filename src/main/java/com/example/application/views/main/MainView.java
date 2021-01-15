package com.example.application.views.main;

import com.example.application.views.about.AboutView;
import com.example.application.views.cardlist.CardListView;
import com.example.application.views.dashboard.DashboardView;
import com.example.application.views.login.LoginView;
import com.example.application.views.notifications.NotificationSender;
import com.example.application.views.notifications.NotificationsGridView;
import com.example.application.views.profile.MyProfile;
import com.example.application.views.recipe.MyRecipe;
import com.example.application.views.recipe.NewRecipe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.webcomponent.WebComponent;
import com.vaadin.flow.router.RouterLink;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport(value = "./styles/views/main/main-view.css", themeFor = "vaadin-app-layout")
@CssImport("./styles/views/main/main-view.css")
@JsModule("./styles/shared-styles.js")
public class MainView extends AppLayout {

    public static class Exporter extends WebComponentExporter<NotificationSender> {

        public Exporter() {
            super("notifications-list");
        }

        @Override
        public void configureInstance(WebComponent<NotificationSender> webComponent, NotificationSender component) {

        }

    }

    private final Tabs menu;

    public MainView() {
        HorizontalLayout header = createHeader();
        menu = createMenuTabs();
        addToNavbar(createTopBar(header, menu));
    }

    private VerticalLayout createTopBar(HorizontalLayout header, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("dark");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(header, menu);
        return layout;
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setId("header");
        Image logo = new Image("images/logo.png", "Jeder Kann Kochen logo");
        logo.setId("logo");
        header.add(logo);
        Image avatar = new Image("images/user.svg", "Avatar");
        avatar.setId("avatar");
        header.add(new H1("Jeder Kann Kochen"));
        header.add(avatar);
        return header;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        return new Tab[]{createTab("Dashboard", DashboardView.class),
                createTab("Profile", MyProfile.class),
                createTab("Home", CardListView.class),
                createTab("Create New Recipe", NewRecipe.class),
                createTab("Recipes CRUD", MyRecipe.class),
                createTab("Notifications", NotificationsGridView.class),
                createTab("Login", LoginView.class), createTab("About", AboutView.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }
}
