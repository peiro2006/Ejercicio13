package com.prog4.ej10.serviceTests;

import com.prog4.ej10.ISocioRepository;
import com.prog4.ej10.Socio;
import com.prog4.ej10.SocioMapper;
import com.prog4.ej10.dtos.SocioCreateRequestDto;
import com.prog4.ej10.dtos.SocioResponseDto;
import com.prog4.ej10.services.SocioCreateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SocioCreateServiceTest {

    @Mock
    private ISocioRepository repo;

    @InjectMocks
    private SocioCreateService service;

    // --- Scenario 1 ---
    @Test
    void crear_DeberiaGuardarYRetornarSocioCorrectamente() {
        // arrange
        SocioCreateRequestDto dto = new SocioCreateRequestDto("Test", "Testing");
        Socio socioGuardado = new Socio(50L, "Test", "Testing");

        when(repo.save(any(Socio.class))).thenReturn(socioGuardado);

        // act
        SocioResponseDto resultado = service.crear(dto);

        // assert
        assertNotNull(resultado);
        assertEquals(50L, resultado.getId());
        assertEquals("Test", resultado.getNombre());
        assertEquals("Testing", resultado.getApellido());
        verify(repo, times(1)).save(any(Socio.class));
    }

    // --- Scenario 2 ---
    @Test
    void crear_DeberiaPropagarExcepcion_CuandoMapperToEntityFalla() {
        // arrange
        SocioCreateRequestDto dto = new SocioCreateRequestDto("Test", "Testing");

        // act
        try (MockedStatic<SocioMapper> mapper = mockStatic(SocioMapper.class)) {

            mapper.when(() -> SocioMapper.toEntity(dto))
                    .thenThrow(new RuntimeException("Fallo simulado en mapper toEntity"));

            assertThrows(RuntimeException.class, () -> service.crear(dto));

            // assert
            mapper.verify(() -> SocioMapper.toEntity(dto));
            verify(repo, never()).save(any(Socio.class));
            mapper.verify(() -> SocioMapper.toResponseDto(any(Socio.class)), never());
        }
    }

    // --- Scenario 3 ---
    @Test
    void crear_DeberiaPropagarExcepcion_CuandoRepositorySaveFalla() {

        SocioCreateRequestDto dto = new SocioCreateRequestDto("Test", "Testing");

        try (MockedStatic<SocioMapper> mapper = mockStatic(SocioMapper.class)) {

            mapper.when(() -> SocioMapper.toEntity(dto)).thenReturn(new Socio());
            when(repo.save(any(Socio.class))).thenThrow(new DataIntegrityViolationException("Error de integridad en BD"));

            assertThrows(DataIntegrityViolationException.class, () -> service.crear(dto));

            mapper.verify(() -> SocioMapper.toEntity(dto));
            verify(repo).save(any(Socio.class));
            mapper.verify(() -> SocioMapper.toResponseDto(any(Socio.class)), never());
        }
    }

    // --- Scenario 4 ---
    @Test
    void crear_DeberiaPropagarExcepcion_CuandoMapperToResponseDtoFalla() {

        SocioCreateRequestDto dto = new SocioCreateRequestDto("Test", "Testing");
        Socio socio = new Socio();

        try (MockedStatic<SocioMapper> mapper = mockStatic(SocioMapper.class)) {

            mapper.when(() -> SocioMapper.toEntity(dto)).thenReturn(socio);
            mapper.when(() -> SocioMapper.toResponseDto(any(Socio.class)))
                    .thenThrow(new RuntimeException("Fallo simulado en mapper toResponseDto"));

            when(repo.save(any(Socio.class))).thenReturn(new Socio());

            assertThrows(RuntimeException.class, () -> service.crear(dto));

            mapper.verify(() -> SocioMapper.toEntity(dto));
            verify(repo).save(any(Socio.class));
            mapper.verify(() -> SocioMapper.toResponseDto(any(Socio.class)));
        }
    }

    // --- Scenario 5 ---
    @Test
    void crear_DeberiaLanzarExcepcion_CuandoDtoEsNull() {

        assertThrows(NullPointerException.class, () -> service.crear(null));

        verify(repo, never()).save(any(Socio.class));
    }

    // --- Scenario 6 ---
    @Test
    void crear_DeberiaRetornarSocioConIdGenerado() {

        SocioCreateRequestDto dto = new SocioCreateRequestDto("Test", "Testing");
        Socio socioGuardado = new Socio();
        socioGuardado.setId(150L);

        when(repo.save(any(Socio.class))).thenReturn(socioGuardado);

        SocioResponseDto resultado = service.crear(dto);

        assertEquals(150L, resultado.getId(), "Debe retornar el ID generado por la base de datos");
    }
}
