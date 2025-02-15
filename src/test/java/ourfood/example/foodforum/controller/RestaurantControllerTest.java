package ourfood.example.foodforum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ourfood.example.foodforum.dto.restaurant.RestaurantDTO;
import ourfood.example.foodforum.service.RestaurantService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    RestaurantService restaurantService;

    @Test
    public void conditionPageSearch() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/search")
                        .param("restaurantName", "")
                        .param("page", "0")
                        .param("orderField", "reviewCount"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("result : " + responseContent);
    }

    @Test
    public void conditionQueryTest() throws Exception {
        int fixedsize = 5;
        Pageable pageable = PageRequest.of(0, fixedsize);
        Page<RestaurantDTO.Search> resultPage =
                restaurantService.searchConditionPage("", pageable, "reviewCount");

        System.out.println(resultPage.getContent());

    }
}