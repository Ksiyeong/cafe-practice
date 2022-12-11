package com.cafe.member;

import com.cafe.dto.MultiResponseDto;
import com.cafe.dto.SingleResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Validated
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberPostDto) {

        Member member = memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));

        return new ResponseEntity<>(new SingleResponseDto<>(
                mapper.memberToMemberResponseDto(member)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("member-id") long memberId,
                                      @Valid @RequestBody MemberDto.Patch memberPatchDto) {

        memberPatchDto.setMemberId(memberId);

        Member member = memberService.updateMember(mapper.memberPatchDtoToMember(memberPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        mapper.memberToMemberResponseDto(member)
                ),
                HttpStatus.OK
        );
    }


    @GetMapping("{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.membersToMemberResponsesDto(pageMembers.getContent()), pageMembers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
