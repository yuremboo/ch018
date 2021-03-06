package com.ch018.library.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ch018.library.entity.Book;
import com.ch018.library.form.AdvancedSearch;
import com.ch018.library.service.BookService;
import com.ch018.library.service.BooksInUseService;
import com.ch018.library.service.GenreService;
import com.ch018.library.service.OrdersService;
import com.ch018.library.service.PersonService;
import com.ch018.library.util.IConstants;
import com.ch018.library.util.JsonResponse;

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
	 * Add/Update book
	 * 
	 * @param book - updated or new book
	 * @param result - Binding Result
	 * @return
	 */
	@RequestMapping(value = "/book/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse newOrUpdateBook(@ModelAttribute @Valid Book book, BindingResult result, 
			@RequestParam(value="file") MultipartFile file, HttpSession session) {
		JsonResponse resp = new JsonResponse();
		if (!result.hasErrors()) {
			resp.setStatus("SUCCESS");
			if (book.getId() == 0) {
				log.info("New book {}", book);
				saveImage(book, file, session);
				bookService.addBook(book);
			} else {
				log.info("Update book {}", book);
				saveImage(book, file, session);
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
	 * Show table books
	 * 
	 * @param page - current page
	 * @param sort - sort field
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String showBooks(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
			@RequestParam(value = "isasc", required = false, defaultValue = "false") Boolean isask,
			Model model, HttpSession session) {
		Locale locale = LocaleContextHolder.getLocale();
		if (session.getAttribute("isAsc") == null) {
			session.setAttribute("isAsc", isask);
		}
		Boolean isAsc = (Boolean) session.getAttribute("isAsc");
		if (session.getAttribute("sort") != null && session.getAttribute("sort").toString().equals(sort)) {
			isAsc = isask;
			session.setAttribute("isAsc", isAsc);
		}
		if (!sort.equals("id")) {
			session.setAttribute("sort", sort);
		} else if (session.getAttribute("sort") != null) {
			sort = (String) session.getAttribute("sort");
		}
		session.removeAttribute("search");
		session.removeAttribute("advancedSearch");
		Book book = new Book();
		long count = bookService.countBooks();
		long pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		model.addAttribute("book", book);
		model.addAttribute("genre", genreService.getAllGenres(locale.getLanguage()));
		List<Book> books = new ArrayList<>();
		books = bookService.getAllBooks(currentPos, IConstants.PAGE_SIZE, sort, isAsc);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("books", books);
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
	@RequestMapping(value = "/books/show/{show}", method = RequestMethod.GET)
	public String showBooksFilters(
			@PathVariable(value = "show") String show,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model, HttpSession session) {
		Locale locale = LocaleContextHolder.getLocale();
		String sessionSort = (String) session.getAttribute("sort");
		model.addAttribute("show", "show");
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
				books.addAll(booksInUseService.getAllBooks(currentPos, IConstants.PAGE_SIZE, "id"));
				break;
			case "returntd":
				count = booksInUseService.countBooksInUseToday();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(booksInUseService.getReturnBooksToday(currentPos, IConstants.PAGE_SIZE, "id"));
				break;
			case "issuetd":
				count = ordersService.countOrdersToday();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(ordersService.toIssueToday(currentPos, IConstants.PAGE_SIZE, "id"));
				break;
			case "issueph":
				count = ordersService.countOrdersPerHour();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(ordersService.toIssuePerHour(currentPos, IConstants.PAGE_SIZE, "id"));
				break;
			default:
				session.removeAttribute("search");
				session.removeAttribute("searchField");
				session.removeAttribute("sort");
				session.removeAttribute("SHOW");
				count = bookService.countBooks();
				pages = (int) Math.ceil(count / (float)IConstants.PAGE_SIZE);
				currentPos = (page - 1) * IConstants.PAGE_SIZE;
				books.addAll(bookService.getAllBooks(currentPos, IConstants.PAGE_SIZE, sessionSort, true));
				break;
		}
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("books", books);
		return "books";
	}
	
	/**
	 * Delete book
	 * 
	 * @param id - ID of book to delete
	 * @return
	 */
	@RequestMapping(value = "/book/delete{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteBook(@PathVariable Integer id, @RequestParam(value="all", required=false) Integer all) {
		if (all != null) {
			bookService.deleteBookAll(id);
			return  bookService.deleteBook(id);
		}
		return bookService.deleteBook(id);
	}

	/**
	 * Simple search for librarian
	 * 
	 * @param search - search query
	 * @param field - search by field
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/books/search", method = RequestMethod.GET)
	public String search(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
			@RequestParam(value = "isasc", required = false, defaultValue = "false") Boolean isask,
			Model model, HttpSession session) {
		Locale locale = LocaleContextHolder.getLocale();
		if (session.getAttribute("isAsc") == null) {
			session.setAttribute("isAsc", isask);
		}
		Boolean isAsc = (Boolean) session.getAttribute("isAsc");
		if (session.getAttribute("sort") != null && session.getAttribute("sort").toString().equals(sort)) {
			isAsc = isask;
			session.setAttribute("isAsc", isAsc);
		}
		if (!sort.equals("id")) {
			session.setAttribute("sort", sort);
		} else if (session.getAttribute("sort") != null) {
			sort = (String) session.getAttribute("sort");
		}
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		session.removeAttribute("advancedSearch");
		if (search == null) {
			search = session.getAttribute("search").toString();
		} else {
			session.setAttribute("search", search);
		}
		model.addAttribute("book", book);
		model.addAttribute("genre", genreService.getAllGenres(locale.getLanguage()));
		long count = bookService.simpleSearchCount(search);
		long pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		books.addAll(bookService.simpleSearch(search, currentPos, IConstants.PAGE_SIZE, sort, isAsc));
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("books", books);
		return "books";
	}
	
	/**
	 * 
	 * @param advancedSearch
	 * @param page
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/books/advsearch", method = RequestMethod.GET)
	public String advancedSearch(@ModelAttribute("advancedsearch") AdvancedSearch advancedSearch, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
			@RequestParam(value = "isasc", required = false, defaultValue = "false") Boolean isask,
			Model model, HttpSession session) {
		Locale locale = LocaleContextHolder.getLocale();
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		session.removeAttribute("search");
		if (advancedSearch.getAuthors() == null || advancedSearch.getGenre() == null
				|| advancedSearch.getPublication() == null) {
			advancedSearch = (AdvancedSearch)session.getAttribute("advancedSearch");
		} else {
			session.setAttribute("advancedSearch", advancedSearch);
		}
		model.addAttribute("book", book);
		model.addAttribute("genre", genreService.getAllGenres(locale.getLanguage()));
		long count = bookService.advancedSearchCount(advancedSearch);
		long pages = (int) Math.ceil(count / (float) IConstants.PAGE_SIZE);
		int currentPos = (page - 1) * IConstants.PAGE_SIZE;
		books.addAll(bookService.advancedSearch(advancedSearch, currentPos, IConstants.PAGE_SIZE));
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("books", books);
		return "books";
	}
	
	private void saveImage(Book book, MultipartFile file, HttpSession session) {
		String path = session.getServletContext().getRealPath("/");
		String imagePath = "/resources/img/books/";
		try {
			if (!file.isEmpty()) {
				BufferedImage bi = ImageIO.read(file.getInputStream());
				String imageName = file.getOriginalFilename().replace(".","") + ".png";
			    File outputfile = new File(path + imagePath + imageName);
			    ImageIO.write(bi, "png", outputfile);
			    book.setImage(imagePath + imageName);
			} else {
				String image = (book.getImage() == null || book.getImage().isEmpty()) ? imagePath + "default.gif" : book.getImage();
				book.setImage(image);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
