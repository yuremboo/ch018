// TODO: License, WHAT?

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ch018.library.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ch018.library.entity.Genre;
import com.ch018.library.entity.Person;
import com.ch018.library.form.Password;
import com.ch018.library.service.BookService;
import com.ch018.library.service.GenreService;
import com.ch018.library.service.PersonService;


// TODO: use only spaces or only tabs, remove trailing spaces, unnecessary double carriage returns in all files
   
 

@Controller
public class AuthorizedUserController {
   
    @Autowired
    private BookService book;
    
    @Autowired
    private PersonService persService; //TODO: replace with meaningful and consistent variable name - book, persService, genreService. Check other files too.
    
    @Autowired
    private GenreService genreService;
   
    
    // TODO: add carriage return after parameter list to separate parameters and method body
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welomePage(
			@RequestParam(value = "genre", required = false) Integer id,
			Model model) {
		if (id == null) {
			model.addAttribute("latest", book.getAllBooks());
		} else {
			Genre genre = genreService.getGenreByIdWithBooks(id);
			model.addAttribute("latest", genre.getBooks());
		}
		return "index";
	}
    
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN" }) // TODO: these strings are good as constants somewhere
    @RequestMapping(value = "/userAccount", method = RequestMethod.GET)
    public Model viewAccount(Model model, Principal principal) {
     model.addAttribute("person", persService.getByEmail(principal.getName()));
     return model;
    }
    
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN" })
    @RequestMapping(value = "/userAccount", method = RequestMethod.POST)
    public String editProfile(@ModelAttribute("person")Person updtPers, 
                              @ModelAttribute("password")Object password, 
                              BindingResult result, Principal principal) {
        Person person = persService.getByEmail(principal.getName());
        person = persService.updateAccProperties(person, updtPers);
        persService.update(person);
        return "redirect:/userAccount";
    }
    
	@Secured({ "ROLE_USER", "ROLE_LIBRARIAN" })
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public String passwordView(@ModelAttribute("password") Password password,
			BindingResult result, Principal principal) {
		if (password == null)
			return null;
		else {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			Person person = persService.getByEmail(principal.getName());
			if (BCrypt.checkpw(password.getPassword(), person.getPassword()))
				if (password.getNewPassword().equals(
						password.getConfirmPassword())) {
					person.setPassword(passwordEncoder.encode(password
							.getNewPassword()));
					persService.update(person);
				}
			return "redirect:/logout";
		}
	}
    
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN" })
    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    public void changePassword(@ModelAttribute("password")Password password, 
                               BindingResult result, 
                               Principal principal) {
     
    } 
    
}
