package db;

import util.BookTM;
import util.IssueBookTM;
import util.MemberTM;

import java.util.ArrayList;

public class DB {

    public  static ArrayList<MemberTM> membersDB = new ArrayList<>();
    public static ArrayList<BookTM> booksDB = new ArrayList();
    public static ArrayList<IssueBookTM> isseBookDB = new ArrayList<>();

    static {
        membersDB.add(new MemberTM("M001","Sahan Rajakaruna","Ratnapura","0714875487"));
        membersDB.add(new MemberTM("M002","Malith Uduwage","Hidellana","0778547875"));
        membersDB.add(new MemberTM("M003","Hirantha Welivita","Matugama","0342248874"));

        booksDB.add(new BookTM("B001","Ape Gama","Sujuth Wedamulla",true));
        booksDB.add(new BookTM("B002","Wedanawa","Nipun Chaturanga",false));
        booksDB.add(new BookTM("B003","Monawa Karannada","Heshan Rodrigo",true));
    }
}
