package views;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import entities.*;
import repositories.*;
import services.AuthenticationServices;
import tools.*;

public class UserManagementView {

    public void Run() throws IOException {

        while(true) {
            MenuEnumeration choice = RenderMenu();

            switch(choice){

                case List: {
                    List();
                    break;
                }
                case Add: {
                    Add();
                    break;
                }
                case Edit: {
                    Edit();
                    break;
                }
                case Delete: {
                    Delete();
                    break;
                }
                case View: {
                    View();
                    break;
                }
                case Exit: {
                    return;
                }
            }
        }
    }

    private MenuEnumeration RenderMenu() throws IOException {

        while(true){
            ConsoleManager.Clear();

            ConsoleManager.WriteLine("[L]ist Users");
            ConsoleManager.WriteLine("[A]dd user");
            ConsoleManager.WriteLine("[E]dit User");
            ConsoleManager.WriteLine("[D]elete User");
            ConsoleManager.WriteLine("[V]iew User");
            ConsoleManager.WriteLine("E[x]it");

            ConsoleManager.Write(">");
            String choice = ConsoleManager.ReadLine();

            switch(choice.toUpperCase()){
                case "L":{
                    return MenuEnumeration.List;
                }
                case "A":{
                    return MenuEnumeration.Add;
                }
                case "E":{
                    return MenuEnumeration.Edit;
                }
                case "D":{
                    return MenuEnumeration.Delete;
                }
                case "V":{
                    return MenuEnumeration.View;
                }
                case "X":{
                    return MenuEnumeration.Exit;
                }
                default: {
                    ConsoleManager.Clear();
                    ConsoleManager.WriteLine("Invalid choice!");
                    ConsoleManager.WriteLine("Press [Enter] to continue");
                    ConsoleManager.ReadLine();
                    break;
                }
            }
        }
    }

    private void Add() throws IOException {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Add User");

        User item = new User();

        ConsoleManager.Write("Username: ");
        item.setUsername(ConsoleManager.ReadLine());

        ConsoleManager.Write("Password: ");
        item.setPassword(ConsoleManager.ReadLine());

        ConsoleManager.Write("First Name: ");
        item.setFirstName(ConsoleManager.ReadLine());

        ConsoleManager.Write("Last Name: ");
        item.setLastName(ConsoleManager.ReadLine());

        item.setcreationDate(LocalDateTime.now());
        item.setcreatorID(AuthenticationServices.getInstance().getLoggedUser().getId());
        item.setlastChangeDate(LocalDateTime.now());
        item.setuserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

        ConsoleManager.Write("Is Admin: ");
        item.setIsAdmin(Boolean.parseBoolean(ConsoleManager.ReadLine()));

        UserRepository userRepo = new UserRepository("users.txt");
        userRepo.Add(item);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item added successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void List() throws NumberFormatException, IOException {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("List Users");

        UserRepository userRepo = new UserRepository("users.txt");
        ArrayList<User> users = userRepo.GetAll();

        for(int i=0;i<users.size();i++) {

            User user = users.get(i);

            ConsoleManager.Write("ID: ");
            ConsoleManager.WriteLine(user.getId());
            ConsoleManager.Write("Username: ");
            ConsoleManager.WriteLine(user.getUsername());
            ConsoleManager.Write("Password: ");
            ConsoleManager.WriteLine(user.getPassword());
            ConsoleManager.Write("First Name: ");
            ConsoleManager.WriteLine(user.getFirstName());
            ConsoleManager.Write("Last Name: ");
            ConsoleManager.WriteLine(user.getLastName());

            user.setcreationDate(LocalDateTime.now());
            user.setcreatorID(AuthenticationServices.getInstance().getLoggedUser().getcreatorID());
            user.setlastChangeDate(LocalDateTime.now());
            user.setuserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getuserMadeLastChangeId());

            ConsoleManager.Write("Is Admin: ");
            ConsoleManager.WriteLine(user.getIsAdmin());
            ConsoleManager.WriteLine("---------------------------------");
        }

        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Edit() throws NumberFormatException, IOException {
        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Edit user");

        UserRepository userRepo = new UserRepository("users.txt");
        ArrayList<User> users = userRepo.GetAll();

        for(int i=0;i<users.size();i++){

            User user = users.get(i);

            ConsoleManager.Write(user.getUsername() + " ( " + user.getId() + " )\t");

            if(i>0 && i%5==0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of user: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        User user = userRepo.GetById(id);

        ConsoleManager.Write("Username ( " + user.getUsername() + " ): ");
        user.setUsername(ConsoleManager.ReadLine());

        ConsoleManager.Write("Password ( " + user.getPassword() + " ) : ");
        user.setPassword(ConsoleManager.ReadLine());

        ConsoleManager.Write("First Name ( " + user.getFirstName() + " ): ");
        user.setFirstName(ConsoleManager.ReadLine());

        ConsoleManager.Write("Last Name: ( " + user.getLastName() + " ): ");
        user.setLastName(ConsoleManager.ReadLine());

        user.setlastChangeDate(LocalDateTime.now());
        user.setuserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

        ConsoleManager.Write("Is Admin: ( " + user.getIsAdmin() + " ): ");
        user.setIsAdmin(Boolean.parseBoolean(ConsoleManager.ReadLine()));

        userRepo.Edit(user);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item updated successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();

    }

    private void Delete() throws NumberFormatException, IOException {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Delete User");

        UserRepository userRepo = new UserRepository("users.txt");
        ArrayList<User> users = userRepo.GetAll();

        for(int i=0;i<users.size();i++) {

            User user = users.get(i);

            ConsoleManager.Write(user.getUsername() + " ( " + user.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of user: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        User user = userRepo.GetById(id);
        userRepo.Delete(user);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item deleted successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void View() throws NumberFormatException, IOException {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("View User");

        UserRepository userRepo = new UserRepository("users.txt");
        ArrayList<User> users = userRepo.GetAll();

        for(int i=0;i<users.size();i++) {

            User user = users.get(i);

            ConsoleManager.Write(user.getUsername() + " ( " + user.getId() + " )\t");
            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of user: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        User user = userRepo.GetById(id);

        ConsoleManager.Clear();
        ConsoleManager.Write("ID: ");
        ConsoleManager.WriteLine(user.getId());
        ConsoleManager.Write("Username: ");
        ConsoleManager.WriteLine(user.getUsername());
        ConsoleManager.Write("Password: ");
        ConsoleManager.WriteLine(user.getPassword());
        ConsoleManager.Write("First Name: ");
        ConsoleManager.WriteLine(user.getFirstName());
        ConsoleManager.Write("Last Name: ");
        ConsoleManager.WriteLine(user.getLastName());
        ConsoleManager.Write("Is Admin: ");
        ConsoleManager.WriteLine(user.getIsAdmin());

        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }
}

