package com.cafe.slice.controller.member;

import com.cafe.member.Member;
import com.cafe.member.MemberDto;
import com.cafe.member.MemberRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // 테스트케이스 후 초기화
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void postMemberTest() throws Exception {
        //given
        MemberDto.Post post = new MemberDto.Post("posttest@test.com",
                "포스트",
                "010-1234-5678");
        String content = gson.toJson(post);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/members")
                                .accept(MediaType.APPLICATION_JSON) // 리턴받을 데이터
                                .contentType(MediaType.APPLICATION_JSON) // 입력할 데이터
                                .content(content)
                );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andReturn();

        System.out.println("\nresult = " + result.getResponse().getContentAsString() + "\n");

    }

    @BeforeEach
    void init() throws Exception {
        System.out.println("\n===== 초기화 작업 시작 =====\n");

        for (int i = 1; i < 10; i++) {
            MemberDto.Post post = new MemberDto.Post(
                    "test" + i + "@test.com",
                    "테스트" + i,
                    "010-0000-000" + i
            );
            String content = gson.toJson(post);

            mockMvc.perform(
                    post("/members")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content)
            );

        }

        System.out.println("\n===== 초기화 작업 종료 =====\n");
    }

    @Test
    void patchMemberTest() throws Exception {
        //given
        long memberId = memberRepository.findByEmail("test1@test.com").get().getMemberId();
        String name = "패치멤버";

        MemberDto.Patch patch = new MemberDto.Patch();
        patch.setName(name);

        String patchContent = gson.toJson(patch);

        //when
        ResultActions patchActions =
                mockMvc.perform(
                        patch("/members/" + memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(patchContent)
                );

        //then
        MvcResult result = patchActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andReturn();

        System.out.println("\nresult = " + result.getResponse().getContentAsString() + "\n");

    }

    @Test
    void getMemberTest() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test1@test.com").get();

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/members/" + member.getMemberId())
                                .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.phone").value(member.getPhone()))
                .andReturn();

        System.out.println("\nresult = " + result.getResponse().getContentAsString() + "\n");

    }

    @Test
    void getMembersTest() throws Exception {
        //given
        int page = 1;
        int size = 5;

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/members?page=" + page + "&size=" + size)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.pageInfo.page").value(page))
                .andExpect(jsonPath("$.pageInfo.size").value(size))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(9))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(2))
                .andReturn();

        System.out.println("\nresult = " + result.getResponse().getContentAsString() + "\n");

    }

    @Test
    void deleteMemberTest() throws Exception {
        //given
        long memberId = memberRepository.findByEmail("test1@test.com").get().getMemberId();

        //when
        ResultActions actions =
                mockMvc.perform(
                        delete("/members/" + memberId)
                );

        //then
        MvcResult result = actions
                .andExpect(status().isNoContent())
                .andReturn();

        System.out.println("\nresult = " + result.getResponse().getContentAsString() + "\n");

    }

}