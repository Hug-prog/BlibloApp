package com.example.biblo_hf.Model;

public class Author {

    private int id;
    private String name;


    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public String getName(){
        return  name;
    }

    public void setName(String n){
        name = n;
    }

    public String ToString(){
        return name;
    }

}
