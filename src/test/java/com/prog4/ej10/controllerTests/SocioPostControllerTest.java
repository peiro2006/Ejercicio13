package com.prog4.ej10.controllerTests;

import com.prog4.ej10.ISocioRepository;
import com.prog4.ej10.controllers.SocioPostController;
import com.prog4.ej10.dtos.SocioCreateRequestDto;
import com.prog4.ej10.dtos.SocioResponseDto;
import com.prog4.ej10.services.SocioCreateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(SocioPostController.class)
class SocioPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SocioCreateService service;

    @MockitoBean
    private ISocioRepository repo;

    @Test
    @DisplayName("Debe crear un socio correctamente y retornar 201")
    void crearSocio_201Created() throws Exception {

        String request = """
            {
                "nombre": "Test",
                "apellido": "Testing"
            }
            """;

        SocioResponseDto response = new SocioResponseDto(1L, "Test", "Testing");

        when(service.crear(any(SocioCreateRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test"))
                .andExpect(jsonPath("$.apellido").value("Testing"));

        verify(service, times(1)).crear(argThat(req ->
                "Test".equals(req.getNombre()) &&
                        "Testing".equals(req.getApellido())
        ));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando uno de los campos es nulo")
    void crearSocio_badRequest_campoNulo() throws Exception {

        String request = """
        {
            "nombre": "Test",
            "apellido": null
        }
        """;

        mockMvc.perform(post("/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))

                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(service, never()).crear(any(SocioCreateRequestDto.class));
    }
}

