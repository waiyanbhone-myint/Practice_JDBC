package com.ps.model;

public class Member {

    private int memberId;
    private String name;
    private String email;
    private String phone;
    private String memberShipDate;

    public Member(int memberId, String name, String email, String phone, String memberShipDate) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.memberShipDate = memberShipDate;
    }

    public Member(String name, String email, String phone, String memberShipDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.memberShipDate = memberShipDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberShipDate() {
        return memberShipDate;
    }

    public void setMemberShipDate(String memberShipDate) {
        this.memberShipDate = memberShipDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", memberShipDate='" + memberShipDate + '\'' +
                '}';
    }
}
