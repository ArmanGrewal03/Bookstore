/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Student
 */
public class Books {
    private String Title;
    private double Price;
    private String Author;
    private CheckBox check;
    
    public Books(String Title, Double Price, String Author){
        this.Title=Title;
        this.Price=Price;
        this.Author=Author;
        
        check=new CheckBox();
    }
    
    public double getPrice(){
        return Price;
    }
    
    public String getTitle(){
        return Title;
    }
    
    public String getAuthor(){
        return Author;
    }
    
    public CheckBox getCheck(){
        return check;
    }
    
    public void SetCheck(CheckBox check){
        this.check=check;
    }
    
    public void setPrice(double Price){
        this.Price=Price;
    }
    
    public void setTitle(String Title){
        this.Title=Title;
    }
    
    public void setAuthor(String Author){
        this.Author=Author;
    }
    
    
}
