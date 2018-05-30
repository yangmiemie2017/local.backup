package com.strong.model.entity;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
import lombok.ToString;
//import lombok.experimental.Accessors;

/**
 * @author cy
 */
//@Accessors(chain = true)
//@NoArgsConstructor
//@Getter
//@Setter
@ToString(callSuper = true)
public class BookWithBookStore extends Book {

    private static final long serialVersionUID = -4858710159989616286L;

    private BookStore bookStore;

	public BookStore getBookStore() {
		return bookStore;
	}

	public void setBookStore(BookStore bookStore) {
		this.bookStore = bookStore;
	}

}
