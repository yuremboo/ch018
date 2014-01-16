package com.ch018.library.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import com.ch018.library.entity.Book;
import com.ch018.library.entity.BooksInUse;
import com.ch018.library.entity.Orders;
import com.ch018.library.entity.Person;
import com.ch018.library.service.BookService;
import com.ch018.library.service.BooksInUseService;
import com.ch018.library.service.OrdersService;
import com.ch018.library.service.PersonService;
import com.ch018.library.service.WishListService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

// TODO: author who?
/**
 * 
 * @author win7
 */
@Controller
public class OrderController {
    
    @Autowired
    private OrdersService order;
    
    @Autowired
    private WishListService wish;
    
    @Autowired
    private PersonService pers;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BooksInUseService booksInUseService;

    @Autowired
    private PersonService personService;
    
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN" })
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(Model model, 
                                    @RequestParam("book") int bookId, 
                                    @RequestParam("wish") int wishId, 
                                    Principal principal) {
        Person p = pers.getByEmail(principal.getName());
        int term=0;
        Date date = new Date();
        int personId = p.getId();
        int  uses = order.getOrdersByPersonId(personId).size();
        uses += booksInUseService.getByPersonId(personId).size();
        int j = p.getMultibookAllowed();
        String fail = "You exceed your limit at the same time to take the book";
        if (j == uses) {            
            return "redirect:/userOrder";
        } 
        if (order.orderExist(personId, bookId)) {
              return "redirect:/userOrder";
          } else {
                Orders newOrder = new Orders();
                Book b = bookService.getBooksById(bookId);
                int available = b.getAvailable();
                if (available == 0) {
                    date = order.minReturnDateOf(bookId);
                }
                if (available == 1) {
                    
                }
                if (available > 1) {
                    term = 14;
                }
                newOrder.setPerson(p);
                newOrder.setBook(b);
                if (wishId > 0)
					wish.deleteWishById(wishId);
                if (wish.bookExistInWishList(bookId, personId)) {
                    int id = wish.getWishWithoutId(bookId, personId).getId();
					wish.deleteWishById(id);
				}
                model.addAttribute("order", newOrder);
                return "order";
             }
    }
    
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN" })
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String createOrder(@ModelAttribute("order") Orders newOrder, 
                              BindingResult result) {
        newOrder.setBook(bookService.getBooksById(newOrder.getBook().getId()));
        newOrder.setPerson(pers.getById(newOrder.getPerson().getId()));
        newOrder.setDate(new java.util.Date());
        order.addOrder(newOrder);
        return "redirect:/userOrder";
    }
    
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN" })
    @RequestMapping(value = "/userOrder")
    public ModelAndView showOrder(Principal principal) {
        return new ModelAndView("userOrder", "showOrders", order.getOrdersByPersonId(personService.getByEmail(principal.getName()).getId()));
    }
    
    /**
     * Librarian
     */
    @Secured("ROLE_LIBRARIAN")
	@RequestMapping(value = "/orders/{query}/{id}", method = RequestMethod.GET)
	public String showOrders(@PathVariable("id") Integer id,
			@PathVariable("query") String query, Model model) {
    	if (query.equals("book")) {
			Book book = bookService.getBooksByIdWithOrders(id);
			Set<Orders> orders = book.getOrders();
			model.addAttribute("orders", orders);
			model.addAttribute("book", book);
			return "orders";
		} else if (query.equals("user")) {
			Person person = personService.getByIdWithOrders(id);
			Set<Orders> orders = person.getOrders();
			model.addAttribute("orders", orders);
			model.addAttribute("person", person);
			return "orders";
		}
		return "404";
	}

	@Secured("ROLE_LIBRARIAN")
	@RequestMapping(value = "/orders/delete{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteOrder(@PathVariable Integer id) {
		order.deleteOrder(id);
		return "order";
	}

	@Secured("ROLE_LIBRARIAN")
	@RequestMapping(value = "/orders/issue{id}/{days}", method = RequestMethod.GET)
	@ResponseBody
	public String issueOrder(@PathVariable int id, @PathVariable int days) {
		booksInUseService.addBooksInUse(days, id);
		return "orders";
	}

}
