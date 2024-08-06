package com.github.erf88.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.erf88.dao.UserDao;
import com.github.erf88.model.User;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserDao dao;
	
	@Inject
	private FacesContext facesContext;
	
	private User user = new User();

	public User getUser() {
		return user;
	}

	public String login() {
		System.out.println("Fazendo login do usuario " + this.user.getEmail());

		boolean exist = dao.exists(this.user);
		if(exist) {
			facesContext.getExternalContext().getSessionMap().put("loggedUser", this.user);
			return "book?faces-redirect=true";
		}

		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
	}
	
	public String logout() {
		System.out.println("Fazendo logout do usuario " + this.user.getEmail());
		facesContext.getExternalContext().getSessionMap().remove("loggedUser");
		return "login?faces-redirect=true";
	}

}
