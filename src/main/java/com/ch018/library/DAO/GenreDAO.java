package com.ch018.library.DAO;

import java.util.List;

import com.ch018.library.entity.Genre;

public interface GenreDAO {
	void addGenre(Genre genre);
	Genre getGenreById(int id);
	List<Genre> getAllGenres(String language);
}
