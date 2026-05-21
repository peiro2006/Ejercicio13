# Feature: Creación de un Socio

## **Scenario 1:** crear socio exitosamente con datos completos y válidos
Given un SocioCreateRequestDto con todos los campos requeridos y válidos
When se invoca el método crear(dto)
Then se debe guardar el socio correctamente
And se debe retornar un SocioResponseDto con los datos correctos

## **Scenario 2:** fallo al mapear DTO a Entity (Mapper lanza excepción)
Given un SocioCreateRequestDto válido
When el mapper falla al intentar convertir el DTO a entidad
Then la excepción debe propagarse desde el servicio
And no se debe realizar ninguna operación de guardado

## **Scenario 3:** fallo al guardar en el repositorio (excepción de base de datos)
Given un SocioCreateRequestDto válido
And el mapper convierte correctamente a entidad
When el socioRepository.save() lanza una excepción (ej: DataIntegrityViolationException)
Then la excepción debe propagarse
And no se debe llamar al mapper de respuesta

## **Scenario 4:** fallo al mapear Entity a ResponseDto
Given un SocioCreateRequestDto válido
When se produce un error al intentar construir la respuesta
Then la excepción debe propagarse desde el servicio

## **Scenario 5:** crear socio con DTO nulo
Given que se pasa null como parámetro al método crear()
When se invoca el método
Then debe lanzar NullPointerException

## **Scenario 6:** crear socio con datos que generan ID automático
Given un SocioCreateRequestDto válido sin ID
When se invoca el método crear(dto)
Then el SocioResponseDto devuelto debe contener un ID generado