package com.cafe.slice.coffee;

import com.cafe.member.MemberController;
import com.cafe.member.MemberMapper;
import com.cafe.member.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class CoffeeControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Test
    void postCoffee() throws  Exception {

    }

    @Test
    void patchCoffee() throws  Exception {

    }

    @Test
    void getCoffee() throws  Exception {

    }

    @Test
    void getCoffees() throws  Exception {

    }

    @Test
    void deleteCoffee() throws  Exception {

    }
}
