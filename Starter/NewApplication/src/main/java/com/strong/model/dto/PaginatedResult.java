package com.strong.model.dto;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
import lombok.ToString;
//import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author cy
 */
//@Accessors(chain = true)
//@NoArgsConstructor
//@Getter
//@Setter
@ToString
public class PaginatedResult implements Serializable {

    private static final long serialVersionUID = 6191745064790884707L;

    private int currentPage; // Current page number
    private int totalPage; // Number of total pages
    private Object data; // Paginated resources
	public int getCurrentPage() {
		return currentPage;
	}
	public PaginatedResult setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		return this;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public PaginatedResult setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		return this;
	}
	public Object getData() {
		return data;
	}
	public PaginatedResult setData(Object data) {
		this.data = data;
		return this;
	}

}
