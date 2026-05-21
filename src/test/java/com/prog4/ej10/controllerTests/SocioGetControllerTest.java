package com.prog4.ej10.controllerTests;

import com.prog4.ej10.controllers.SocioGetController;
import com.prog4.ej10.dtos.SocioResponseDto;
import com.prog4.ej10.services.SocioGetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(SocioGetController.class)
public class SocioGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SocioGetService service;

    @Test
    @DisplayName("Debe obtener socios correctamente y retornar 200")
    void obtenerSocios_ok() throws Exception {

        List<SocioResponseDto> responseList = List.of(
                new SocioResponseDto(1L, "Test", "Testing"),
                new SocioResponseDto(2L, "User", "User")
        );

        when(service.all()).thenReturn(responseList);

        mockMvc.perform(get("/socios"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test"))
                .andExpect(jsonPath("$[0].apellido").value("Testing"))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service, times(1)).all();
    }
}
