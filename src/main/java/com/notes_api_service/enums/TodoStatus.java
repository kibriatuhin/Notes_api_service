package com.notes_api_service.enums;

import lombok.Getter;
import lombok.Setter;





public enum TodoStatus {
    NOT_STARTED(1,"NOT STARTED"),   IN_PROGRESS(2,"IN PROGRESS"),COMPLETED(3,"COMPLETED");
    private Integer id ;

    private String name ;
    TodoStatus(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
