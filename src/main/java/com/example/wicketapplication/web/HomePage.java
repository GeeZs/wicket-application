package com.example.wicketapplication.web;

import com.example.wicketapplication.model.User;
import com.example.wicketapplication.service.UserService;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

@WicketHomePage
public class HomePage extends WebPage {

    @SpringBean
    private UserService userService;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        add(new FeedbackPanel("feedback"));
        add(new LoginForm("loginForm"));
    }

    class LoginForm extends Form<LoginForm> {
        private TextField usernameField;
        private PasswordTextField passwordField;
        private Label loginStatus;

        LoginForm(String id) {
            super(id);

            usernameField = new TextField<>("username", Model.of(""));
            passwordField = new PasswordTextField("password", Model.of(""));
            loginStatus = new Label("loginStatus", Model.of(""));

            add(usernameField);
            add(passwordField);
            add(loginStatus);
        }

        public final void onSubmit() {
            String username = (String)usernameField.getDefaultModelObject();
            String password = (String)passwordField.getDefaultModelObject();



            User user = userService.getUserByUsername(username);
            if (user != null){
                if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                    loginStatus.setDefaultModelObject("Congratulations!");
                    setResponsePage(UserListPage.class);
                }else {
                    loginStatus.setDefaultModelObject("Wrong username or password!");
                }
            }

        }
    }
}