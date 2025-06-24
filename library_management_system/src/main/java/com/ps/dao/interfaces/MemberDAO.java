package com.ps.dao.interfaces;

import com.ps.model.Member;

import java.util.List;

public interface MemberDAO {

    List<Member> getAllMember();
    Member getMemberById(int id);
    void insertMember(Member member);
    void updateMember(Member member);
    void deleteMember(int id);
}
