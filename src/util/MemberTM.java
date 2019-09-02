package util;

public class MemberTM {

    private String memberID;
    private String memberName;
    private String memberAddress;
    private String memberPhoneNumber;


    public MemberTM(String memberID, String memberName, String memberAddress, String memberPhoneNumber) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberAddress = memberAddress;
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public MemberTM() {
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberPhoneNumber() {
        return memberPhoneNumber;
    }

    public void setMemberPhoneNumber(String memberPhoneNumber) {
        this.memberPhoneNumber = memberPhoneNumber;
    }

    @Override
    public String toString() {
        return "MemberTM{" +
                "memberID='" + memberID + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberAddress='" + memberAddress + '\'' +
                ", memberPhoneNumber='" + memberPhoneNumber + '\'' +
                '}';
    }
}
