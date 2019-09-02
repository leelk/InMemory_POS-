package util;

import java.util.ArrayList;

public class IssueBookTM {

    private String issueID;
    private String memberID;
    private String issueDate;
    private ArrayList<AddBookTM>bookDetails;

    public IssueBookTM(String issueID, String memberID, String issueDate, ArrayList<AddBookTM> bookDetails) {
        this.issueID = issueID;
        this.memberID = memberID;
        this.issueDate = issueDate;
        this.bookDetails = bookDetails;
    }

    @Override
    public String toString() {
        return "IssueBookTM{" +
                "issueID='" + issueID + '\'' +
                ", memberID='" + memberID + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", bookDetails=" + bookDetails +
                '}';
    }

    public IssueBookTM() {
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public ArrayList<AddBookTM> getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(ArrayList<AddBookTM> bookDetails) {
        this.bookDetails = bookDetails;
    }
}
