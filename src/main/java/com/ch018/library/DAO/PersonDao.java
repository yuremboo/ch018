/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ch018.library.DAO;

import com.ch018.library.entity.Person;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Yurik Mikhaletskiy
 */
@Component
public interface PersonDao {
	void save(Person person);
	int delete(int id);
	void update(Person person);
	List<Person> getAll();
	Person getById(int id);
	Person getByIdWithBooks(int id);
	Person getByIdWithOrders(int id);
	Person getByEmail(String email);
	Person getByKey(String key);
	List<Person> getByName(String name);
	List<Person> getBySurname(String surname);
	Person getByCellPhone(String cellphone);
	List<Person> getByRole(String role);
	List<Person> getConfirmed();
	List<Person> getSmsEnabled();
	long isExist(String email);
}
