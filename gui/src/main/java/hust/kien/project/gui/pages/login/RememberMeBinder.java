package hust.kien.project.gui.pages.login;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import hust.kien.project.gui.pages.DataBinder;

class RememberMeBinder implements DataBinder<State> {
    
    @Override
    public void bind(State state) {
        
        String username = "";
        String password = "";
        try {
            File myObj = new File("remember.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (username.equals("")) {
                    username = myReader.nextLine();
                }
                if (password.equals("")) {
                    password = myReader.nextLine();
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        state.username().set(username);
        state.password().set(password);
    }
    
}