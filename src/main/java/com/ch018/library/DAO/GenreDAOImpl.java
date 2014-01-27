package com.ch018.library.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ch018.library.entity.Book;
import com.ch018.library.entity.Genre;

@Component
public class GenreDAOImpl implements GenreDAO {

	private static Logger log = LogManager.getLogger(GenreDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addGenre(Genre genre) {
		try {
			sessionFactory.getCurrentSession().save(genre);
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public List<Genre> getAllGenres(String language) {
		List<Genre> genres = new ArrayList<>();
		try {
			genres.addAll(sessionFactory.getCurrentSession()
					.createCriteria(Genre.class)//.add(Restrictions.eq("language", language))
					//.addOrder(Order.asc("name"))
					.list());
		} catch (Exception e) {
			log.error(e);
		}
		return genres;
	}
	
	/**
	 * Lazy initialization
	 */
	@Override
	public Genre getGenreByIdWithBooks(int id) {
		Genre genre = null;
		try {
			genre = (Genre) sessionFactory.getCurrentSession().get(Genre.class,
					id);
		} catch (Exception e) {
			log.error(e);
		}
		Hibernate.initialize(genre.getBooks());
		return genre;
	}

	@Override
	public Genre getGenreById(int id) {
		Genre genre = null;
		try {
			genre = (Genre) sessionFactory.getCurrentSession().get(Genre.class,
					id);
		} catch (Exception e) {
			log.error(e);
		}
		return genre;
	}

	@Override
	public Genre getGenreByName(String name) {
		Genre genre = null;
		try {
			Query query = sessionFactory
					.getCurrentSession()
					.createQuery("select G from Genre G where lower(G.name) "
									+ "LIKE lower(:name)")
					.setString("name", name);
			genre = (Genre) query;
		} catch (Exception e) {
			log.error(e);
		}
		return genre;
	}
	
	@Override
	public Genre getGenreByBook(Book book, String locale) {

		return null;
	}

}
