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
public class Error implements Serializable {

    private static final long serialVersionUID = 7660756960387438399L;

    private int code; // Error code
    private String message; // Error message
	public int getCode() {
		return code;
	}
	public Error setCode(int code) {
		this.code = code;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public Error setMessage(String message) {
		this.message = message;
		return this;		
	}
	

}
