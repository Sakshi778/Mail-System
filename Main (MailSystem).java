/******************************************************************************
Project:
Mail System using stacks and queues.
Team:
2359 Mrunal Jambenal
2360 Mukta Joshi
2371 Sakshi Khaire
2372 Tanaya Khaire
*******************************************************************************/
import java.util.*;
class Person{
    String mail_id;            //Mail_Id of User
    String name;              //Name of user
    String password;         //password of account
    Stack inbox = new Stack();                    //Inbox to show latest message
    Queue<Node> outbox = new ArrayDeque<Node>(); //Outbox to show sent messages
    Stack dropbox = new Stack();                //Dropbox to maintain read messages
    Person(String mail_id,String name,String password){  //constructor of Person class
        this.mail_id = mail_id;
        this.name = name;
        this.password = password;
    }
    class Node{             //Node that contains message
        String message;
        Node(String message){
            this.message = message;
        }
    }
   //Abstract Data type : Stack using array
    class Stack{
    int max = 100;   //defining maximum capacity of stack
   
    int top;        // initializing the top of stack
   
    Node arr[] = new Node[max];//defining array to be implemented as stack
   
    //default constructor
    Stack(){
        top = -1;      //initializing empty stack
    }
    boolean isEmpty(){//method to check stack is empty or not
        return (top<0);
    }
    void push(Node s){//to push elements inside stack
        if(top >= max - 1){
            System.out.println("Max size of stack reached...");// checking if stack has rached its max capacity
        }
        else{
            arr[++top] = s;//adding element in stack at top
        }
    }
    Node pop(){           //method to get the top element of stack
        if(top<0){
            System.out.println("No unread messages");//checking whether stack is empty
            return null;
        }
        else{
           Node x = arr[top--];                  //popping the top
           return x;
        }
    }
    void printStack(){                       //method to print messages
        if(top>-1){
            for(int i=top;i>-1;i--){
                System.out.println(arr[i].message);
            }
        }
        else{
            System.out.println("No unread messages");
        }
    }
}
   
   
    void send(String msg){  //method to add mail in sender's outbox
        Node new_msg = new Node(msg);
        outbox.add(new_msg);
    }
    void read(){            //method to read latest message
        if(inbox.isEmpty()){
            System.out.println("No unread messages");
            return;
        }
        Node msg = inbox.pop();
        dropbox.push(msg);
        System.out.println(msg.message);
        System.out.println("message read");
    }
   
    void receive(String msg){
        Node new_msg = new Node(msg);
        inbox.push(new_msg);
    }
    void read_dropbox(){     //to read previous messages
        dropbox.printStack();
    }
}


class Mail{
    Scanner scn = new Scanner(System.in);
    int c = 0;
    Person[] contact_list = new Person[15];
    Person user;
    void add_account(){                    //method to create account and add it to database
        System.out.println("***Signup-Window***");
        System.out.println();
        System.out.println("Enter name: ");
        String name = scn.nextLine();
        System.out.println("Enter mail id: ");
        String mail_id = scn.nextLine();
        mail_id = mail_id + "@mail.com";
        System.out.println("mail_id: "+ mail_id);
        System.out.println("Enter password: ");
        String pwd = scn.nextLine();
        Person p = new Person(mail_id,name,pwd);
        contact_list[c] = p;
        c++;
    }
    
    
    Person search(String name){         //method to search user by name
         Person temp = null;
        for(int i=0;i<c;i++){
            temp = contact_list[i];
            if(temp.name.equals(name)){
                return temp;
            }
        }
        return null;
    }
    
    Person search_user(String id,String pwd){      //method to check validity of user
         Person temp = null;
         int f = 0;
        for(int i=0;i<c;i++){
            temp = contact_list[i];
            if(temp.mail_id.equals(id) && temp.password.equals(pwd)){
                f = 1;
                System.out.println("user found..");
                return temp;
            }
            
        }
        
        if(f == 0){
            System.out.println("Invalid Id / Password");
        }
        return null;
    }

    void login(String id,String pwd){         //method to login
        user = search_user(id,pwd);
        if(user!=null){
            System.out.println("Welcome "+user.name +" | Logged in successfully");
        }
        else{
            System.out.println("User not registered");
        }
    }
    void login(){   //wrapper method for above login method
        System.out.println("|---*Login Window*---|");
        System.out.println("Enter mail id");
        String id = scn.nextLine();
        System.out.println("Enter Password");
        String pwd = scn.nextLine();
        login(id,pwd);
    }
    
    void logout(){         // TO logout 
        user = null;
        System.out.println("Loggedout successfully");
        System.out.println("Do you want to login again?  Enter yes/no");
        String choice = scn.nextLine();
        if(choice.equalsIgnoreCase("yes")){
            login();
        }else{
            return;
        }
    }
    
    void display_all(){                       //To display list of contacts
        System.out.println("Contact list: ");
        for(int i=0;i<c;i++){
            Person temp;
            temp = contact_list[i];
            System.out.println("Name: "+ temp.name + "| Mail Id: "+temp.mail_id);
        }
    }
    void read_all(){                        //To read mails from dropbox
        user.read_dropbox();
    }
    void send_mail(String msg){       //method to send mail
        if(user == null){
            System.out.println("user not logged in");
            return;
        }
        Person p;
        System.out.println("Enter reciever's name: ");
        String reciever = scn.nextLine();
        p = search(reciever);
        msg = msg + "\n -- sent by -> "+user.name + " to -> "+p.name;
        user.send(msg);
        p.receive(msg);
        System.out.println("mail sent from "+ user.name + " to "+p.name);
    }
   
    void read_mail(){      //wrapper method to read latest mail
        user.read();
    }
    
    void send_mail(){  // wrapper method to send mail
        if(user == null){
            System.out.println("user not logged in");
            return;
        }
        System.out.println("Enter Message: ");
        String n = scn.nextLine();
        send_mail(n);
    }
}
public class Main
{
public static void main(String[] args) {
   Scanner scn = new Scanner(System.in);
   Mail m = new Mail();
   boolean c = true;
   while(c){
       
       System.out.println("\n--*_*--Welcome--*_*--");
       System.out.println("******************");
       System.out.println("1-To signup");
       System.out.println("2-To login");
       System.out.println("3-To read latest mail");
       System.out.println("4-To read previously read messages");
       System.out.println("5-To send mail");
       System.out.println("6-To logout");
       System.out.println("7-To exit");
       System.out.println("******************");
       System.out.println("Enter choice:");
       int choice = scn.nextInt();
       switch(choice){
           case 1:
               m.add_account();
               break;
           case 2:
               m.login();
               break;
           case 3:
               System.out.println("Unread messages: ");
               m.read_mail();
               break;
           case 4:
               System.out.println("previously read messages: ");
               m.read_all();
               break;
           case 5:
               m.display_all();
               m.send_mail();
               break;
           case 6:
               m.logout();
               break;
           case 7:
               System.out.println("Exited Mail System program..");
               c = false;
               break;
       }
   }
}
}















