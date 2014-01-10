package com.ch018.library.DAO;

import java.util.Date;
import java.util.List;

import com.ch018.library.entity.Book;
import com.ch018.library.entity.BooksInUse;

public interface BooksInUseDAO {
	void addBooksInUse(BooksInUse booksInUse);
	List<BooksInUse> getAllBooksInUse();
	List<BooksInUse> getByPersonId(int personId);
	List<BooksInUse> getByBookId(int bookId);
	List<BooksInUse> getByIssueDate(Date issueDate);
	List<BooksInUse> getByReturnDate(Date returnDate);
	List<BooksInUse> getInUse(boolean inUse);
	List<Book> getAllBooks();
	List<Book> getReturnBooksToday();
	void removeBooksInUse(int id);
	BooksInUse getById(int id);
}
