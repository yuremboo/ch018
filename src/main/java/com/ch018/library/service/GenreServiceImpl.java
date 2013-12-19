package com.ch018.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ch018.library.DAO.GenreDAO;
import com.ch018.library.entity.Genre;

@Component
public class GenreServiceImpl implements GenreService {

	@Autowired
	GenreDAO genreDAO;
	
	@Transactional
	public void addGenre(Genre genre) {
		// TODO Auto-generated method stub
		genreDAO.addGenre(genre);
	}

	@Override
	@Transactional
	public void updateGenre(int id, Genre genre) {
		// TODO Auto-generated method stub
		genreDAO.updateGenre(id, genre);
	}

	@Override
	@Transactional
	public List<Genre> getAllGenres() {
		// TODO Auto-generated method stub
		return genreDAO.getAllGenres();
	}

	@Override
	@Transactional
	public Genre getGenreById(int id) {
		// TODO Auto-generated method stub
		return genreDAO.getGenreById(id);
	}

	@Override
	@Transactional
	public void deleteGenre(Genre genre) {
		// TODO Auto-generated method stub
		genreDAO.deleteGenre(genre);
	}

	@Override
	@Transactional
	public int getCount() {
		// TODO Auto-generated method stub
		return genreDAO.getCount();
	}

	@Override
	@Transactional
	public Genre getGenreByName(String name) {
		// TODO Auto-generated method stub
		return genreDAO.getGenreByName(name);
	}

}
