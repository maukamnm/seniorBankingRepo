package com.mycompany;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Service;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private TextField usernameField;
	private TextField passwordField;
	private TextField depositField;
	private TextField dispenseField;

	int userId;
	private Label errorLabel;
	private String username;
	private String password;
	private Button loginSubmit;
	private Button logout;
	private Button depositOption;
	private Button dispenseOption;
	private Button depositAction;
	private Button dispenseAction;

	public String transactionAmount;

	private WebMarkupContainer loginCont;
	private WebMarkupContainer homeCont;
	private WebMarkupContainer depositCont;
	private WebMarkupContainer dispenseCont;

	private static final Logger log = LoggerFactory.getLogger (HomePage.class);

	public HomePage(final PageParameters parameters) {
		super(parameters);
		addLoginComponents();
		addLogoutButton();
		addHomeComponents();
		addDepositComponents();
		addDispenseComponents();
		log.info("Page successfully created");
	}

	public void addLogoutButton(){
		logout = new Button("logout");
		logout.add(new AjaxEventBehavior("click") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				loginCont.setVisible(true);
				loginCont.setOutputMarkupId(true);
				loginSubmit.setVisible(true);
				usernameField.setVisible(true);
				passwordField.setVisible(true);
				ajaxRequestTarget.add(depositAction);
				ajaxRequestTarget.add(dispenseAction);
				ajaxRequestTarget.add(loginCont);
				homeCont.setVisible(false);
				ajaxRequestTarget.add(homeCont);
				depositCont.setVisible(false);
				ajaxRequestTarget.add(depositCont);
				dispenseCont.setVisible(false);
				ajaxRequestTarget.add(dispenseCont);
				logout.setVisible(false);
				ajaxRequestTarget.add(logout);
			}
		});
		logout.setOutputMarkupPlaceholderTag(true);
		logout.setVisible(false);
		add(logout);
	}
	public void addLoginComponents(){
		loginCont = new WebMarkupContainer("loginContainer");
		loginCont.setOutputMarkupId(true);
		add(loginCont);
		errorLabel = new Label("loginError", "Error Logging In");
		errorLabel.setOutputMarkupPlaceholderTag(true);
		errorLabel.setVisible(false);
		loginCont.add(errorLabel);
		loginSubmit = new Button("submit");
		loginSubmit.add(new AjaxEventBehavior("click") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				Service service = new Service();
				int userId = service.getUserLogin(username, password);
				if(userId!=0) {
					homeCont.setVisible(true);
					ajaxRequestTarget.add(homeCont);
					loginCont.setVisible(false);
					ajaxRequestTarget.add(loginCont);
					logout.setVisible(true);
					ajaxRequestTarget.add(logout);
				}
				else {
					errorLabel.setVisible(true);
					ajaxRequestTarget.add(errorLabel);
				}
			}
		});
		loginSubmit.setOutputMarkupId(true);
		loginCont.add(loginSubmit);

		usernameField = new TextField("username", Model.of(""));
		usernameField.setOutputMarkupId(true);
		loginCont.add(usernameField);
		usernameField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				username = usernameField.getInput();
			}
		});
		passwordField = new TextField("password", Model.of(""));
		passwordField.setOutputMarkupId(true);
		loginCont.add(passwordField);
		passwordField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				password = passwordField.getInput();
			}
		});
	}

	public void addHomeComponents(){
		homeCont = new WebMarkupContainer("homeContainer");
		homeCont.setOutputMarkupPlaceholderTag(true);
		homeCont.setVisible(false);
		add(homeCont);
		depositOption = new Button("depositOption");
		depositOption.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						homeCont.setVisible(false);
						ajaxRequestTarget.add(homeCont);
						depositCont.setVisible(true);
						ajaxRequestTarget.add(depositCont);
					}
				});
		depositOption.setOutputMarkupId(true);
		homeCont.add(depositOption);

		dispenseOption = new Button("dispenseOption");
		dispenseOption.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						dispenseCont.setVisible(true);
						ajaxRequestTarget.add(dispenseCont);
						homeCont.setVisible(false);
						ajaxRequestTarget.add(homeCont);
					}
				});
		dispenseOption.setOutputMarkupId(true);
		homeCont.add(dispenseOption);
	}
	public void addDepositComponents(){
		depositCont = new WebMarkupContainer("depositContainer");
		depositCont.setOutputMarkupPlaceholderTag(true);
		depositCont.setVisible(false);
		add(depositCont);
		depositField = new TextField("depositAmount");
		depositField.setOutputMarkupId(true);
		depositCont.add(depositField);
		depositField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				transactionAmount = depositField.getInput();
			}
		});
		depositAction = new Button("depositAction");
		depositAction.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						Service service = new Service();
						boolean hideErrorMessage = service.deposit(Integer.valueOf(transactionAmount),userId);
						errorLabel.setVisible(!hideErrorMessage);
						ajaxRequestTarget.add(errorLabel);
						if(hideErrorMessage) {
							log.info("Deposit was successful");
						}
						else {
							log.error("Deposit was unsuccessful");
						}
					}
				});
		depositAction.setOutputMarkupId(true);
		depositCont.add(depositAction);
	}
	public void addDispenseComponents() {
		dispenseCont = new WebMarkupContainer("dispenseContainer");
		dispenseCont.setOutputMarkupPlaceholderTag(true);
		dispenseCont.setVisible(false);
		add(dispenseCont);
		dispenseField = new TextField("dispenseAmount");
		dispenseField.setOutputMarkupId(true);
		dispenseField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				transactionAmount = dispenseField.getInput();
			}
		});
		dispenseCont.add(dispenseField);
		dispenseAction = new Button("dispenseAction");
		dispenseAction.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						Service service = new Service();
						boolean hideErrorMessage = service.dispense(Long.parseLong(transactionAmount),userId);
						errorLabel.setVisible(!hideErrorMessage);
						ajaxRequestTarget.add(errorLabel);
						if(hideErrorMessage) {
							log.info("Dispense was successful");
						}
						else {
							log.error("Dispense was unsuccessful");
						}
					}
				});
		dispenseAction.setOutputMarkupId(true);
		dispenseCont.add(dispenseAction);
	}
}
