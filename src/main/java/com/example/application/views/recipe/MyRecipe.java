package com.example.application.views.recipe;


import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.User;
import com.example.application.data.service.RezeptService;
import com.vaadin.flow.component.crud.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@PageTitle("My recipes")
public class MyRecipe extends Div {

    Crud<Rezept> crud = new Crud<>(Rezept.class, createRezeptEditor());

    public MyRecipe(RezeptService rezeptService) {

        RezeptDataProvider dataProvider = new RezeptDataProvider(rezeptService);

        crud.setDataProvider(dataProvider);
        crud.addSaveListener(e -> dataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> dataProvider.delete(e.getItem()));

        crud.getGrid().removeColumnByKey("id");
        crud.getGrid().removeColumnByKey("creator");
        crud.getGrid().removeColumnByKey("zutaten");
        crud.getGrid().removeColumnByKey("videos");

        crud.addThemeVariants(CrudVariant.NO_BORDER);
        add(crud);
    }

    private CrudEditor<Rezept> createRezeptEditor() {

        TextField rezeptName = new TextField("Name");
        TextArea inhalt = new TextArea("Inhalt");
        FormLayout form = new FormLayout(rezeptName, inhalt);

        Binder<Rezept> binder = new Binder<>(Rezept.class);
        binder.bind(rezeptName, Rezept::getRezeptName, Rezept::setRezeptName);
        binder.bind(inhalt, Rezept::getBeschreibung, Rezept::setBeschreibung);

        return new BinderCrudEditor<>(binder, form);
    }

    public static class RezeptDataProvider extends AbstractBackEndDataProvider<Rezept, CrudFilter> {

        final RezeptService service;
        private final List<Rezept> DATABASE;
        private Consumer<Long> sizeChangeListener;

        public RezeptDataProvider(RezeptService service) {
            this.service = service;
            User user = VaadinSession.getCurrent().getAttribute(User.class);
           this.DATABASE= service.getUserRezepte(user.getId());
        }

        @Override
        protected Stream<Rezept> fetchFromBackEnd(Query<Rezept, CrudFilter> query) {
            int offset = query.getOffset();
            int limit = query.getLimit();
            Stream<Rezept> stream = DATABASE.stream();
            if (query.getFilter().isPresent()) {
                stream = stream
                        .filter(predicate(query.getFilter().get()))
                        .sorted(comparator(query.getFilter().get()));
            }

            return stream.skip(offset).limit(limit);
        }

        @Override
        protected int sizeInBackEnd(Query<Rezept, CrudFilter> query) {
            long count = fetchFromBackEnd(query).count();

            if (sizeChangeListener != null) {
                sizeChangeListener.accept(count);
            }

            return (int) count;
        }

        void setSizeChangeListener(Consumer<Long> listener) {
            sizeChangeListener = listener;
        }

        private static Predicate<Rezept> predicate(CrudFilter filter) {
            return filter.getConstraints().entrySet().stream()
                    .map(constraint -> (Predicate<Rezept>) rezept -> {
                        try {
                            Object value = valueOf(constraint.getKey(), rezept);
                            return value != null && value.toString().toLowerCase()
                                    .contains(constraint.getValue().toLowerCase());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    })
                    .reduce(Predicate::and)
                    .orElse(e -> true);
        }

        private static Comparator<Rezept> comparator(CrudFilter filter) {
            // For RDBMS just generate an ORDER BY clause
            return filter.getSortOrders().entrySet().stream()
                    .map(sortClause -> {
                        try {
                            Comparator<Rezept> comparator
                                    = Comparator.comparing(rezept ->
                                    (Comparable) valueOf(sortClause.getKey(), rezept));

                            if (sortClause.getValue() == SortDirection.DESCENDING) {
                                comparator = comparator.reversed();
                            }

                            return comparator;
                        } catch (Exception ex) {
                            return (Comparator<Rezept>) (o1, o2) -> 0;
                        }
                    })
                    .reduce(Comparator::thenComparing)
                    .orElse((o1, o2) -> 0);
        }

        private static Object valueOf(String fieldName, Rezept rezept) {
            try {
                Field field = Rezept.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(rezept);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        void persist(Rezept item) {
            Optional<Rezept> existingItem = Optional.empty();
            if (item.getId() != null) {
                existingItem = find(item.getId());
            }
            if (existingItem.isPresent()) {
                int position = DATABASE.indexOf(existingItem.get());
                DATABASE.remove(existingItem.get());
                DATABASE.add(position, item);
                service.updateRezept(existingItem.get().getId(), item.getRezeptName(), item.getBeschreibung());
            } else {
                User user = VaadinSession.getCurrent().getAttribute(User.class);
               DATABASE.add(service.createRezept(user, item.getRezeptName(), item.getBeschreibung()));
            }
        }

        Optional<Rezept> find(Integer id) {
            return DATABASE
                    .stream()
                    .filter(entity -> entity.getId().equals(id))
                    .findFirst();
        }

        void delete(Rezept item) {
            DATABASE.removeIf(entity -> entity.getId().equals(item.getId()));
            service.delete(item.getId());
        }
    }
}
