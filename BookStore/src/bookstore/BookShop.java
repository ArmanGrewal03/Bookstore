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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Student
 */
public class BookShop {
    private String BookFile = "books.txt";
    ObservableList<Books> books = FXCollections.observableArrayList();
    
    public BookShop(String name){
        BookFile=name;
    }
    
    public ObservableList<Books> read() throws IOException{
        try {
            BufferedReader reader=new BufferedReader(new FileReader(BookFile));
            String line=reader.readLine();
            while(line!=null){
                String bookData[]=line.split(", ");
                books.add(new Books(bookData[0], Double.parseDouble(bookData[1]),bookData[2]));
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: File Not Found");
        }
        return books;
    }
    
    public void write(Books Book){
        try {
            FileWriter Writer = new FileWriter(BookFile,true);
            Writer.write(Book.getTitle()+", "+String.valueOf(Book.getPrice())+", "+Book.getAuthor()+"\n");
            Writer.close();
        } catch (IOException ex) {
            System.out.println("Writing Error");
        }  
        books.add(Book);
    }
    
    public void delete(Books Book){
        File inFile=new File(BookFile);
        File x = new File("temp.txt");
        String line;
        
        BufferedReader reader=null;
        FileWriter writer=null;
        books.remove(Book);
        
        try {
            reader=new BufferedReader(new FileReader(inFile));
            writer = new FileWriter(x,true);
            while((line=reader.readLine())!=null){
                if(line.equals(Book.getTitle()+", "+Book.getPrice()+", "+Book.getAuthor())){
                    
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
            System.out.println("Deleting Error");
        }
    }
        
    
}
