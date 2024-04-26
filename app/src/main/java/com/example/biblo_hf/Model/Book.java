package com.example.biblo_hf.Model;

public class Book {

    private int id;
    private String name;

    private int nbPages;

    private String desc;


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

    public void setNbPages(int i){
        nbPages = i;
    }

    public  int  getNbPages(){
        return nbPages;
    }

    public void setDesc(String t){
        desc = t;
    }

    public String getDesc(){
        return desc;
    }

}

