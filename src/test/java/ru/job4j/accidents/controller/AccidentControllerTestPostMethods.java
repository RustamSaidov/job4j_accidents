package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
public class AccidentControllerTestPostMethods {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidents;

    @MockBean
    private AccidentTypeService types;

    @MockBean
    private RuleService rules;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessagePostMethods() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "Происшествие 1")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).create(argument.capture());
        assertThat(argument.getValue().getName(), is("Происшествие 1"));

        this.mockMvc.perform(post("/edit_accident")
                        .param("name", "Происшествие 1")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument1 = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).replace(argument1.capture());
        assertThat(argument1.getValue().getName(), is("Происшествие 1"));
    }
}
