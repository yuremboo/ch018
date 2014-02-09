package com.ch018.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ch018.library.DAO.BookDAO;
import com.ch018.library.entity.Book;
import com.ch018.library.form.AdvancedSearch;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private LocalizationService localizationService;
	
	@Transactional
	public void addBook(Book book) {
		bookDAO.addBook(book);
	}

	@Transactional
	public void updateBook(Book book) {
		bookDAO.updateBook(book);
	}


	@Transactional
	public List<Book> getAllBooks() {
		return bookDAO.getAllBooks();
	}
	
	
	@Override
	@Transactional
	public List<Book> getAllBooks(int currentPos, int pageSize, String sort) {
		//List<Localization> l = new ArrayList<>();
		List<Book> books = bookDAO.getAllBooks(currentPos, pageSize, sort);
		/*for (Book book : books) {
			Genre genre = book.getGenre();
			l.addAll(genre.getLocalizations());
			for (Localization localization : l) {
				if (localization.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
					genre.setName(localization.getLocalizedName());
				}
			}
			book.setGenre(genre);
		}*/
		return books;
	}
	
	@Override
	@Transactional
	public long countBooks() {
		return bookDAO.countBooks();
	}

	@Transactional
	public Book getBooksById(int id) {
		return bookDAO.getBooksById(id);
	}
	
	@Transactional
	public Book getBooksByIdWithUses(int id) {
		return bookDAO.getBooksByIdWithUses(id);
	}
	
	@Transactional
	public Book getBooksByIdWithOrders(int id) {
		return bookDAO.getBooksByIdWithOrders(id);
	}

	@Transactional
	public int deleteBook(int id) {
		return bookDAO.deleteBook(id);
	}

	@Transactional
	public List<Book> simpleSearch(String parametr) {
		return bookDAO.simpleSearch(parametr);
	}
	
	@Transactional
	public List<Book> simpleSearch(String parametr, int currentPos, int pageSize, String sort) {
		List<Book> books = bookDAO.simpleSearch(parametr, currentPos, pageSize, sort);
		return books;
	}
	
	@Transactional
	public List<Book> paramSearch(String searchField, String search,
			int currentPos, int pageSize, String sort) {
		List<Book> books = bookDAO.paramSearch(searchField, search, currentPos, pageSize, sort);
		return books;
	}

	

    @Override
    @Transactional
    public long simpleSearchCount(String search) {
    	return bookDAO.simpleSearchCount(search);
    }
    
    @Override
    @Transactional
    public long countBooksByGenre(String search, Integer id) {
    	return bookDAO.countBooksByGenre(search, id);
    }
    
    @Override
    @Transactional
    public List<Book> getBooksByGenre(String search, Integer id, int currentPos, int pageSize,	String field) {
    	return bookDAO.getBooksByGenre(search, id, currentPos, pageSize, field);
    }
    
    @Override
    @Transactional
    public List<Book> advancedSearch(AdvancedSearch search, int currentPos,	int pageSize) {
    	List<Book> books = bookDAO.advancedSearch(search, currentPos, pageSize);
		return books;
    }
    
    @Override
    @Transactional
    public long advancedSearchCount(AdvancedSearch search) {
    	return bookDAO.advancedSearchCount(search);
    }

	@Override
	@Transactional
	public List<Book> getBooksByRating() {
		return bookDAO.getBooksByRating();
	}
	
	@Override
	@Transactional
	public long countBooksByGenreWithAdvSearch(AdvancedSearch advancedSearch, Integer id) {
		return bookDAO.countBooksByGenreWithAdvSearch(advancedSearch, id);
	}
	
	@Override
	@Transactional
	public List<Book> getBooksByGenreWithAdvSearch(AdvancedSearch advancedSearch, Integer id, int currentPos, int pageSize) {
		return bookDAO.getBooksByGenreWithAdvSearch(advancedSearch, id, currentPos, pageSize);
	}

}
