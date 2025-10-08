# Integrador 2 - Arquitecturas Web - Grupo 13

Este proyecto implementa un sistema básico de gestión de estudiantes y carreras, utilizando una arquitectura multicapa para garantizar la *separación de responsabilidades*. La persistencia de datos se gestiona a través de la especificación JPA (Java Persistence API).

## Arquitectura del Proyecto
El proyecto sigue un patrón de *arquitectura por capas* con responsabilidades específicas para cada módulo
```
src/
├── main/
│   ├── java/
│   │   └── micro.example/
│   │       ├── dto/            # Objetos de transferencia de datos
│   │       ├── factory/        # Fábricas para objetos complejos
│   │       ├── modelo/         # Entidades JPA
│           ├── repository/     # Repositorios para acceso a datos
│           ├── main            # Ejecuta el menú para probar las operaciones del sistema
│   └── resources/
│       └── META-INF/
│           └── persistence.xml  # Configuración de JPA
│       ├── csv                  # Archivos CSV con datos de prueba
```
## Modelo de Datos
El sistema se basa en tres entidades principales que gestionan la información académica y de identificación

**Estudiante:** Contiene los datos personales y de identificación de cada estudiante
    - **Atributos:** `dni` (**PK**), `nro_libreta` (**Unique**), `nombre`, `apellido`, `edad`, `genero`, `ciudad` 
    
**Carrera:** Almacena la **información identificativa** de cada curso o titulación ofrecida por la institución
    - **Atributos:** `id_carrera` (**PK**), `nombre_carrera`, `duracion`
    
**EstudianteCarrera:** Modela la **relación de inscripción (N:M)** entre un estudiante y una carrera, registrando los detalles de esa matrícula
    - **Atributos:** `inscripcion`, `graduacion`, `antiguedad` 
    
### Diagramas de Entidades

**Diagrama Entidad-Relación (logico)**

<img width="727" height="183" alt="image" src="https://github.com/user-attachments/assets/b6bf574d-1181-446c-90d3-4005796d7f2c" />

**Diagrama Físico (Estructura de la BD)**

<img width="710" height="173" alt="image" src="https://github.com/user-attachments/assets/01973d31-77c4-4c0b-a33e-199a99b1d9c4" />

## Funcionalidad (Operaciones y Consultas)

### Operaciones de Gestión
1. **Dar de alta** un estudiante
2. **Matricular** un estudiante en una carrera
   
### Consultas
3. **Recuperar todos los estudiantes**, y especificar algún criterio de **ordenamiento** simple
4. **Recuperar un estudiante** específico, basado en su **número de libreta universitaria**
5. **Recuperar todos los estudiantes**, filtrados por **género**
6. **Recuperar las carreras con estudiantes inscriptos**, y ordenar el resultado por **cantidad de inscriptos**
7. **Recuperar los estudiantes** de una determinada carrera, aplicando un filtro por **ciudad de residencia**
8. **Generar reporte** de las carreras, mostrando datos de inscripción y graduación

---
## Requisitos Previos
Para la ejecución del proyecto se necesita:

* **Java JDK 21**
* **Maven** (Para la gestión de dependencias y compilación)
