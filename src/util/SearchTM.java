package util;

public class SearchTM {
    private String issuedId;
    private String memberId;
    private String memberName;
    private String issuedDate;

    public SearchTM(String issuedId, String memberId, String memberName, String issuedDate) {
        this.issuedId = issuedId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.issuedDate = issuedDate;
    }

    public SearchTM() {
    }

    public String getIssuedId() {
        return issuedId;
    }

    public void setIssuedId(String issuedId) {
        this.issuedId = issuedId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    @Override
    public String toString() {
        return "SearchTM{" +
                "issuedId='" + issuedId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", issuedDate='" + issuedDate + '\'' +
                '}';
    }
}
