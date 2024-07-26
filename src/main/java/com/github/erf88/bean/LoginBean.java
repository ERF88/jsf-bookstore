package com.github.erf88.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.github.erf88.dao.UserDao;
import com.github.erf88.model.User;

@ManagedBean
@ViewScoped
public class LoginBean {

	private User user = new User();

	public User getUser() {
		return user;
	}

	public String login() {
		System.out.println("Fazendo login do usuario " + this.user.getEmail());

		boolean exist = new UserDao().exists(this.user);
		FacesContext facesContext = FacesContext.getCurrentInstance();
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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getSessionMap().remove("loggedUser");
		return "login?faces-redirect=true";
	}

}
