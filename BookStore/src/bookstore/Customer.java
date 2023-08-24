/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Student
 */
public class Customer {
    
    public static boolean verify(String user, String pw){
       boolean verification = false;
        String username, password;
        int points;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));

            String line = reader.readLine();
            while (line != null) {
                String info[] = line.split(", ");
                username = info[0];
                password = info[1];
                points = Integer.parseInt(info[2]);
                if ((user.equals(username)) && (pw.equals(password))) {
                    verification = true;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
        }
        return verification;  
    }
    
    private String Username;
    private String Password;
    private int points;
    protected Rank rank;
    
    final int rewardPoints=10;
    final int payPoints =100;
    
    
    public Customer(String user, String pw, int points){
        Username=user;
        Password=pw;
        this.points=points;
    }
    
    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public String getStatus(){
        if(points<1000){
            rank=new SilverRank();
            return rank.buy();
        }
        else{
            rank = new GoldRank();
            return rank.buy();
        }
    }

    public double PayPoints(double TC){
        double transactionCost=0;
        if(this.getPoints()>= (int)TC*payPoints){
            this.setPoints((int) (TC-(points/payPoints)));
            transactionCost = 0;
        }
        else{
            transactionCost = TC-(double)(points/payPoints);
            this.setPoints(0);
        }
        return transactionCost;  
    }
    
    public void PayMoney(double TC){
        int x =(int)TC*rewardPoints;
        this.setPoints(x+this.getPoints());
    }
    
    public ObservableList<Books> selectedBooks(ObservableList<Books> books){
       ObservableList<Books> selectBooks = FXCollections.observableArrayList(); 
       for(int i =0; i<books.size();i++){
           if(books.get(i).getCheck().isSelected()){
               selectBooks.add(books.get(i));
           }
       }
       return selectBooks;
    }
    
    
}

