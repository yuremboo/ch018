package com.ch018.library.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ch018.library.entity.Book;
import com.ch018.library.entity.Orders;

/**
 * 
 * @author okryvortc
 */
public interface OrdersService {
	void addOrder(Orders ord);
	void deleteOrder(Orders ord);
	Orders deleteOrder(int id);
	Collection<Orders> getOrdersByBooksId(int id);
	Collection<Orders> getOrdersByPersonId(int id);
	Collection<Orders> getAllOrders();
	List<Book> getAllBooks();
	List<Book> toIssueToday(int currentPos, int pageSize, String sort);
	List<Book> toIssuePerHour();
	Orders getById(int id);
	boolean orderExist(int personId, int bookId);
	Date minOrderDateOf(int bookId);
	long countOrdersToday();
	long countOrdersPerHour();
	List<Book> toIssuePerHour(int currentPos, int pageSize,
			String string);
}