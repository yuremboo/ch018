package com.ch018.library.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ch018.library.entity.Book;
import com.ch018.library.entity.Person;
import com.ch018.library.form.AdvancedSearch;
import com.ch018.library.form.Password;
import com.ch018.library.service.BookService;
import com.ch018.library.service.BooksInUseService;
import com.ch018.library.service.GenreService;
import com.ch018.library.service.PersonService;
import com.ch018.library.service.WishListService;
import com.ch018.library.util.IConstants;
import com.ch018.library.validator.AccountValidation;
import com.ch018.library.validator.ChangePasswordValid;
import com.ch018.library.validator.PersonValidation;

@Controller
public class AuthorizedUserController {
   
    @Autowired
    private BookService book;
    
    @Autowired
    private PersonService persService; 
    
    @Autowired
    private GenreService genreService;
    
    @Autowired 
    private WishListService wishListService;
   
    @Autowired 
    private AccountValidation accountValidation;
    
    @Autowired 
    private ChangePasswordValid changePass;
    
    @Autowired
    private BooksInUseService inUse;
    
    @Autowired
	private PersonValidation personValidation;
    
    /**
     * 
     * @param page - current page
     * @param show - reset search
     * @param id - genre id
     * @param model
     * @param session
     * @return view
     */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welomePage(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "show", required = false) String show,
			@RequestParam(value = "genre", required = false) Integer id, Model model, HttpSession session) {
		session.removeAttribute("indexSearch");
		session.removeAttribute("advancedSearch");
		long pages = 1;
		long count = book.countBooks();
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		List<Book> books = book.getAllBooks(currentPos, IConstants.PAGE_SIZE, "id");
		model.addAttribute("latest", books);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		return "index";
	}
	
	/**
	 * 
	 * @param search
	 * @param page
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam String search, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model, HttpSession session) {
		List<Book> books = new ArrayList<Book>();
		session.setAttribute("indexSearch", search);
		session.removeAttribute("advancedSearch");
		long count = book.simpleSearchCount(search);
		long pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		books.addAll(book.simpleSearch(search, currentPos, IConstants.PAGE_SIZE, "id"));
		model.addAttribute("latest", books);
		return "index";
	}
	
	/**
	 * 
	 * @param advancedSearch
	 * @param page
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/advsearch", method = RequestMethod.GET)
	public String advancedSearch(@ModelAttribute("advancedsearch") AdvancedSearch advancedSearch, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
			Model model, HttpSession session) {
		session.setAttribute("advancedSearch", advancedSearch);
		session.removeAttribute("indexSearch");
		List<Book> books = new ArrayList<Book>();
		long count = book.advancedSearchCount(advancedSearch);
		long pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		books.addAll(book.advancedSearch(advancedSearch, currentPos, IConstants.PAGE_SIZE));
		model.addAttribute("latest", books);
		return "index";
	}
	
	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public String bookPage(@PathVariable(value = "id") Integer id,
			Model model) {
		Book books = book.getBooksById(id);
		model.addAttribute("book", books);
		return "book";
	}
	
	/**
	 * 
	 * @param bookID
	 * @param buid
	 * @param model
	 */
	@RequestMapping(value = "/rate", method = RequestMethod.GET)
    public void rate(@RequestParam("bookID") int bookID, 
    		         @RequestParam("buID") int buid,
    		         Model model) {
		Book books = book.getBooksById(bookID);
		model.addAttribute("book",books);
		model.addAttribute("buid", buid);
		float mark = books.getRating();
		BigDecimal bd = new BigDecimal(mark);
		float usermark = inUse.getById(buid).getMark();
		BigDecimal bdUserMark = new BigDecimal(usermark);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		bdUserMark.setScale(2,BigDecimal.ROUND_HALF_DOWN);
		model.addAttribute("mark", bd);
		model.addAttribute("usermark", bdUserMark);
		System.out.println(bookID);
	}
	
	/**
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
    @RequestMapping(value = "/userAccount", method = RequestMethod.GET)
    public Model viewAccount(Model model, Principal principal) {
     model.addAttribute("person", persService.getByEmail(principal.getName()));
     return model;
    }
    
    /**
     * 
     * @param updtPers
     * @param result
     * @param principal
     * @param request
     * @return
     */
    @RequestMapping(value = "/userAccount", method = RequestMethod.POST)
    public String editProfile(@ModelAttribute("person") @Valid Person updtPers, 
                              BindingResult result, Principal principal, HttpServletRequest request) {
        accountValidation.validate(updtPers, result);
        Person person = persService.getByEmail(principal.getName());
        if (result.hasErrors()) {
            return "userAccount";
        }
        if(!person.getEmail().equals(updtPers.getEmail())) { 
            person = persService.updateAccProperties(person, updtPers, request);
            persService.update(person);
            return "redirect:/logout"; 
        }
        person = persService.updateAccProperties(person, updtPers, request);
        persService.update(person);
        return "userAccount";
    }
    
    /**
     * 
     * @param password
     * @param result
     * @param principal
     * @return
     */
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public String passwordView(@ModelAttribute("password") Password password,
			BindingResult result, Principal principal) {
	       changePass.validate(password, result);
               if(result.hasErrors()){
                     return "pass";
               } else {
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
	
	@RequestMapping(value = "/profile-email")
	public Model emailView(Model model, Principal principal){
		Person person = persService.getByEmail(principal.getName());
		model.addAttribute("email", person.getEmail());
		person.setEmail("");
		model.addAttribute("person", person);
		return model;
	}
	
	@RequestMapping(value = "/profile-email", method = RequestMethod.POST)
	public String changeEmail(@ModelAttribute("person") @Valid Person person, Principal principal, BindingResult result, HttpServletRequest request){
		personValidation.validate(person, result);
		if(result.hasErrors()){
			return "profile-email";
		}
		Person pers = persService.getByEmail(principal.getName());
		persService.updateEmail(pers, person, request);
		return "redirect:/logout";
	}
    
	/**
	 * 
	 * @param password
	 * @param result
	 * @param principal
	 */
    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    public void changePassword(@ModelAttribute("password")Password password, 
                               BindingResult result, 
                               Principal principal) {
     
    } 
    
}
