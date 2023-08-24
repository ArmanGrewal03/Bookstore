/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Student
 */
public class Owner {
    private static String CustomerFile="customers.txt";
    ObservableList<Customer> customers=FXCollections.observableArrayList();
    
    public Owner(String name){
        CustomerFile=name;
    }
    
    public void write(Customer customer){
        customers.add(customer);
        try {
            FileWriter writer=new FileWriter(CustomerFile,true);
            writer.write(customer.getUsername()+", "+customer.getPassword()+", "+customer.getPoints()+"\n");
            writer.close();  
        } catch (IOException ex) {
            System.out.println("New Customer Writing Error");
        } 
    }
    
    public void delete(Customer customer){
        File inFile=new File(CustomerFile);
        File x=new File("temp.txt");
        String line;
        BufferedReader reader=null;
        FileWriter writer;
        customers.remove(customer);
        
        try {
            writer=new FileWriter(x,true);
            reader=new BufferedReader(new FileReader(inFile));
            while((line=reader.readLine())!=null){
                if(line.equals(customer.getUsername()+", "+customer.getPassword()+", "+customer.getPoints())){
                    
                }
                else{
                    writer.write(line+"\n");
                }
            }
            writer.close();
            reader.close();
            inFile.delete();
            x.renameTo(inFile);
        } catch (IOException ex) {
            System.out.println("Deleting Customer Error");
        }
        
    }
    
    public ObservableList<Customer> getUsers(){
        String username, password;
        int points;
        
        try {
            BufferedReader reader=new BufferedReader(new FileReader(CustomerFile));
            String line=reader.readLine();
            while(line!=null){
                String info[]=line.split(", ");
                username=info[0];
                password=info[1];
                points=Integer.parseInt(info[2]);
                customers.add(new Customer(username,password,points));
                line=reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customers;
    }
    
    public void editUser(String old, String newcontent){
        File filetobemodified = new File(CustomerFile);
        BufferedReader reader= null;
        FileWriter writer=null;
        String Oldlines="";
        
        try {
            reader=new BufferedReader(new FileReader(filetobemodified));
            String line= reader.readLine();
            
            while(line!=null){
                Oldlines=Oldlines+line+System.lineSeparator();
                line=reader.readLine();
            }
            
            String newLines=Oldlines.replace(old, newcontent);
            writer=new FileWriter(filetobemodified);
            writer.write(newLines);
            
            reader.close();
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    
}
