package com.example.mycontentpages.service;

import com.example.mycontentpages.domain.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface MemberService {
    @POST("member/loginVerification")
    Call<Member> memberInfo();
}
