package com.ch018.library.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Yurik Mikhaletskiy
 *
 */
@Entity
@Table(name = "booksinuse")
public class BooksInUse {
	private int buid;
	private Person person;
	private Book book;
	private Date issueDate;
	private Date returnDate;
	private boolean inUse;
	private int term;

	public BooksInUse() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "buid")
	public int getBuid() {
		return buid;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Person_id")
	public Person getPerson() {
		return person;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Books_id")
	public Book getBook() {
		return book;
	}

	@Column(name = "issue_date")
	public Date getIssueDate() {
		return issueDate;
	}

	@Column(name = "return_date")
	public Date getReturnDate() {
		return returnDate;
	}

	@Column(name = "inUse")
	public boolean getInUse() {
		return inUse;
	}

	@Column(name = "term")
	public int getTerm() {
		return term;
	}

	public void setBuid(int buid) {
		this.buid = buid;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public void setTerm(int term) {
		this.term = term;
	}

}
