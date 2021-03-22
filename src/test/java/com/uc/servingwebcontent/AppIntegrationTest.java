package com.uc.servingwebcontent;

import com.uc.servingwebcontent.webController.WebController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(args = "src/test/resources/import.csv")
@AutoConfigureMockMvc
public class AppIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void WebControllerConvertOkTest() throws Exception {
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"м\", \"to\": \"м\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"км\", \"to\": \"м\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1000")));
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"м / с\", \"to\": \"км / час\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3.6")));
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"час\", \"to\": \"с\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3600")));
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"м/с\", \"to\": \"км/час\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3.6")));
    }

    @Test
    public void WebControllerConvertBadRequestTest() throws Exception {
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"моль\", \"to\": \"м\" }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"км/па\", \"to\": \"м/с\" }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void WebControllerConvertNotFoundTest() throws Exception {
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"с\", \"to\": \"м\" }"))
                .andDo(print())
                .andExpect(status().isNotFound());
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"from\": \"км/час\", \"to\": \"м/км\" }"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
