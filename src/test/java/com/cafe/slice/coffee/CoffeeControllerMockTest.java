package com.cafe.slice.coffee;

import com.cafe.coffee.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class CoffeeControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private CoffeeService coffeeService;

    @MockBean
    private CoffeeMapper mapper;

    @Test
    void postCoffee() throws  Exception {
        //given
        CoffeeDto.Post post = new CoffeeDto.Post(
                "아메리카노",
                "americano",
                5000
        );

        CoffeeDto.Response response = new CoffeeDto.Response(
                1L,
                post.getKorName(),
                post.getEngName(),
                post.getPrice()
        );

        given(mapper.coffeePostDtoToCoffee(Mockito.any(CoffeeDto.Post.class))).willReturn(new Coffee());
        given(coffeeService.createCoffee(Mockito.any(Coffee.class))).willReturn(new Coffee());
        given(mapper.coffeeToCoffeeResponseDto(Mockito.any(Coffee.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(
                post("/coffees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.korName").value(post.getKorName()))
                .andExpect(jsonPath("$.data.engName").value(post.getEngName()))
                .andExpect(jsonPath("$.data.price").value(post.getPrice()))
                .andReturn();

        System.out.println("\nresult = " + result.getResponse().getContentAsString() + "\n");

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
