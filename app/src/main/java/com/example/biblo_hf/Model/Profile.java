package com.example.biblo_hf.Model;

public class Profile {

    private int id;
    private String name;

    private String emojiCode;


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

    public String getEmojiCode(){return emojiCode;}

    public void setEmojiCode(String e){emojiCode = e;}



}
