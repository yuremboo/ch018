package com.ch018.library.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch018.library.domain.JsonResponse;
import com.ch018.library.entity.Book;
import com.ch018.library.service.BookService;
import com.ch018.library.service.BooksInUseService;
import com.ch018.library.service.GenreService;
import com.ch018.library.service.OrdersService;
import com.ch018.library.service.PersonService;
import com.ch018.library.util.IConstants;

/**
 * 
 * @author Yurik Mikhaletskiy
 * 
 */
@Controller
public class BooksController {
	
	private static Logger log = LogManager.getLogger(BooksController.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private GenreService genreService;

	@Autowired
	private PersonService personService;

	@Autowired
	private BooksInUseService booksInUseService;

	@Autowired
	private OrdersService ordersService;
	
	@Autowired 
	private MessageSource messageSource;
	

	/**
	 * Add new book
	 * 
	 * @param book
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/book/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonResponse newOrUpdateBook(@RequestBody @Valid Book book,
			BindingResult result) {
		JsonResponse resp = new JsonResponse();
		if (!result.hasErrors()) {
			resp.setStatus("SUCCESS");
			if (book.getId() == 0) {
				log.info("New book {}", book);
				bookService.addBook(book);
			} else {
				log.info("Update book {}", book);
				book.setImage(bookService.getBooksById(book.getId()).getImage());
				bookService.updateBook(book);
			}
			resp.setResult(book);
		} else {
			Map<String,String> errors = new HashMap<>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				String[] resolveMessageCodes = result.resolveMessageCodes(fieldError.getCode());
				String string = resolveMessageCodes[0];
				String message = messageSource.getMessage(string + "." + fieldError.getField(), new Object[]{fieldError.getRejectedValue()},LocaleContextHolder.getLocale());
				errors.put(fieldError.getField(), message);
				log.error("Error updating book {}", message);
			}
			resp.setErrorsMap(errors);	
			resp.setStatus("FAIL");
			resp.setResult(result.getAllErrors());
		}
		return resp;
	}

	/**
	 * Show table edit book
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String showBooks(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
			Model model, HttpSession session) {
		/*Locale locale = LocaleContextHolder.getLocale();
		String field =(String) session.getAttribute("sort");
		if (field == null) {
			session.setAttribute("sort", sort);
			field =(String) session.getAttribute("sort");
		}
		if (!sort.equals("id")) {
			session.setAttribute("sort", sort);
			field =(String) session.getAttribute("sort");
		}
		String search = (String) session.getAttribute("search");
		String searchField = (String) session.getAttribute("searchField");
		Book book = new Book();
		long count = bookService.countBooks(search, null, null);
		long pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		model.addAttribute("book", book);
		model.addAttribute("genre", genreService.getAllGenres(locale.getLanguage()));
		List<Book> books = new ArrayList<>();
		books = bookService.getBooks(search, null, null, currentPos);
		
		if (searchField == null || search == null) {
			count = bookService.countBooks();
			pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
			currentPos = (page - 1) * IConstants.PAGE_SIZE;
			books.addAll(bookService.getAllBooks(currentPos, IConstants.PAGE_SIZE, field));
		} else if (searchField.equals("all")) {
			count = bookService.simpleSearchCount(search);
			pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
			currentPos = (page - 1) * IConstants.PAGE_SIZE;
			books.addAll(bookService.simpleSearch(search, currentPos, IConstants.PAGE_SIZE, field));
		} else {
			count = bookService.simpleSearchCount(search);
			pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
			currentPos = (page - 1) * IConstants.PAGE_SIZE;
			books.addAll(bookService.paramSearch(searchField, search, currentPos, IConstants.PAGE_SIZE, field));
		}
		*/
		model.addAttribute("pages", 2);
		model.addAttribute("page", page);
		//model.addAttribute("books", books);
		return "books";
	}
	
	/**
	 * 
	 * @param show
	 * @param page
	 * @param sort
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/books/{show}", method = RequestMethod.GET)
	public String showBooksFilters(
			@PathVariable(value = "show") String show,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
			Model model, HttpSession session) {
		Locale locale = LocaleContextHolder.getLocale();
		String sessionSort = (String) session.getAttribute("sort");
		if (sessionSort == null) {
			session.setAttribute("sort", sort);
			sessionSort = (String) session.getAttribute("sort");
		}
		if (!sort.equals("id")) {
			session.setAttribute("sort", sort);
			sessionSort = (String) session.getAttribute("sort");
		}	
		Book book = new Book();
		long count;
		long pages = 1;
		int currentPos;
		model.addAttribute("book", book);
		model.addAttribute("genre", genreService.getAllGenres(locale.getLanguage()));
		List<Book> books = new ArrayList<>();
		
		switch (show) {
			case "return":
				count = booksInUseService.countBooksInUse();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(booksInUseService.getAllBooks(currentPos,IConstants.PAGE_SIZE, "id"));
				break;
			case "returntd":
				count = booksInUseService.countBooksInUseToday();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(booksInUseService.getReturnBooksToday(currentPos,IConstants.PAGE_SIZE, "id"));
				break;
			case "issuetd":
				count = ordersService.countOrdersToday();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(ordersService.toIssueToday(currentPos,IConstants.PAGE_SIZE, "id"));
				break;
			case "issueph":
				count = ordersService.countOrdersPerHour();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(ordersService.toIssuePerHour(currentPos,IConstants.PAGE_SIZE, "id"));
				break;
			default:
				session.removeAttribute("search");
				session.removeAttribute("searchField");
				session.removeAttribute("sort");
				session.removeAttribute("SHOW");
				count = bookService.countBooks();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(bookService.getAllBooks(currentPos,IConstants.PAGE_SIZE, sessionSort));
				break;
		}
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("books", books);
		return "books";
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/book/delete{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteBook(@PathVariable Integer id) {
		return bookService.deleteBook(id);
	}

	/**
	 * 
	 * @param search
	 * @param field
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/books", method = RequestMethod.POST)
	public String search(@RequestParam String search,
			@RequestParam String field, Model model, HttpSession session) {
		Locale locale = LocaleContextHolder.getLocale();
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		session.setAttribute("search", search);
		session.setAttribute("searchField", field);
		model.addAttribute("book", book);
		model.addAttribute("genre", genreService.getAllGenres(locale.getLanguage()));
		long count;
		long pages = 1;
		if (field.equals("all")) {
			count = bookService.simpleSearchCount(search);
			pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
			books.addAll(bookService.simpleSearch(search, 0, IConstants.PAGE_SIZE, "id"));
		} else {
			count = bookService.simpleSearchCount(search);
			pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
			books.addAll(bookService.paramSearch(field, search, 0, IConstants.PAGE_SIZE, "id"));
		}
		model.addAttribute("pages", pages);
		model.addAttribute("page", 1);
		model.addAttribute("books", books);
		return "books";
	}

}
