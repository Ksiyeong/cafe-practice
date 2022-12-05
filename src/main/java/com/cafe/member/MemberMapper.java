package com.cafe.member;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post memberPostDto);

    MemberDto.Response memberToMemberResponseDto(Member member);

    Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto);

    List<MemberDto.Response> membersToMemberResponsesDto(List<Member> members);

}
