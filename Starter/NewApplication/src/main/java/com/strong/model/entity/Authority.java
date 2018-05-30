package com.strong.model.entity;

public enum Authority {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    
    private String code;
    
    private Authority(String code){
        this.code = code;
    }
    
    @Override
    public String toString(){
        return this.code;
    }
    

    public String getCode(){
        return this.code;
    }
}
