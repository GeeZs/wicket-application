package com.example.wicketapplication.web;

import com.example.wicketapplication.model.User;
import com.example.wicketapplication.service.UserService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class UserListPage extends WebPage {

    @SpringBean
    private UserService userService;

    private List<User> users = userService.getAllUsers();
    private List<User> searchedUsers = new ArrayList<>();

    public UserListPage(final PageParameters parameters){
        add(new FeedbackPanel("feedback"));
        add(new UserForm("userForm"));

        final TextField username = new TextField<>("username", Model.of(""));

        Form form = new Form<Void>("search"){
            @Override
            protected void onSubmit() {

                User user = userService.getUserByUsername((String) username.getDefaultModelObject());
                if (user != null) {
                    info("First name: " + user.getFirstName() + ","
                            + "Second name: " + user.getSecondName() + ","
                            + "Username: " + user.getUsername()
                    );
                }else {
                    info("no such users");
                }
            }
        };

        add(form);
        form.add(username);

        displayUsers(users);


    }

    private void displayUsers(List<User> userList) {

        add(new ListView<User>("users", userList) {
            @Override
            protected void populateItem(ListItem<User> item) {
                item.add(new Label("id", new PropertyModel<>(item.getModel(), "id")));
                item.add(new Label("firstName", new PropertyModel<>(item.getModel(), "firstName")));
                item.add(new Label("secondName", new PropertyModel<>(item.getModel(), "secondName")));
                item.add(new Label("username", new PropertyModel<>(item.getModel(), "username")));
                item.add(new EditForm("edit", item));
                item.add(new Form<Void>("delete"){
                    @Override
                    protected void onSubmit() {
                        userService.deleteUser(userList.get(Integer.parseInt(item.getId())).getId());
                        setResponsePage(UserListPage.class);
                    }
                });
            }
        });
    }

    class EditForm extends Form<EditForm>{
        private TextField firstNameField = new TextField<>("firstName", Model.of(""));
        private TextField secondNameField = new TextField<>("secondName", Model.of(""));
        ListItem<User> list;

        EditForm(String id, ListItem<User> item) {
            super(id);

            list = item;
            add(firstNameField);
            add(secondNameField);
        }
        protected void onSubmit() {

            int id = users.get(Integer.parseInt(list.getId())).getId();

            String firstName = (String) firstNameField.getDefaultModelObject();
            String secondName = (String) secondNameField.getDefaultModelObject();
            String username = users.get(Integer.parseInt(list.getId())).getUsername();
            String password = users.get(Integer.parseInt(list.getId())).getPassword();
            User user = new User(id, firstName, secondName, username, password);

            userService.updateUser(user);
            setResponsePage(UserListPage.class);
        }
    }

    class UserForm extends Form<UserForm> {
        private int id = users.get(users.size()-1).getId() + 1;
        private TextField firstNameField;
        private TextField secondNameField;
        private TextField usernameField;
        private PasswordTextField passwordField;


        UserForm(String id) {
            super(id);

            firstNameField = new TextField<>("firstName", Model.of(""));
            secondNameField = new TextField<>("secondName", Model.of(""));
            usernameField = new TextField<>("username", Model.of(""));
            passwordField = new PasswordTextField("password", Model.of(""));

            add(firstNameField);
            add(secondNameField);
            add(usernameField);
            add(passwordField);
        }

        @Override
        public final void onSubmit() {
            String fName = (String) firstNameField.getDefaultModelObject();
            String sName = (String) secondNameField.getDefaultModelObject();
            String uName = (String) usernameField.getDefaultModelObject();
            String pass = (String) passwordField.getDefaultModelObject();

            User user = new User(id, fName, sName, uName, pass);
            userService.addUser(user);

            setResponsePage(UserListPage.class);
        }
    }
}
