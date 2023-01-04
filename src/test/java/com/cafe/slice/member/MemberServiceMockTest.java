package com.cafe.slice.member;

import com.cafe.exception.BusinessLogicException;
import com.cafe.member.Member;
import com.cafe.member.MemberRepository;
import com.cafe.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberServiceMockTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void createMemberTest() {
        //given
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setName("테스트");
        member.setPhone("010-1234-5678");

        given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));

        //when

        //then
        assertThrows(BusinessLogicException.class, () -> memberService.createMember(member));

    }
}
