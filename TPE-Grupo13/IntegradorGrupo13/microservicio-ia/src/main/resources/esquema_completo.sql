/**
 * 💾 Esquema Completo del Microservicio de Usuarios/Cuentas (MySQL).
 *
 * Este script DDL (Data Definition Language) y DML (Data Manipulation Language)
 * define la estructura y los datos iniciales para la persistencia del
 * Microservicio de Usuarios/Cuentas.
 *
 * ========================================================================
 * 1. Tabla: `usuario`
 * ========================================================================
 * - Rol Principal: Gestiona la identidad básica y el rol de los usuarios
 * (ADMIN, MANTENIMIENTO, USUARIO).
 * - Campos Clave: 'email' (UNIQUE) y 'rol'.
 *
 * ========================================================================
 * 2. Tabla: `cuenta`
 * ========================================================================
 * - Rol Principal: Gestiona la información financiera y de suscripción (Premium).
 * - Campos Clave: 'saldo', 'tipo_cuenta' (ENUM: BASICA, PREMIUM), 'estado'
 * (ENUM: ACTIVA, SUSPENDIDA).
 * - Integración con Facturación: Contiene 'km_consumidos_mes' y
 * 'fecha_renovacion_cupo', datos esenciales que el Microservicio de Facturación
 * consulta y actualiza.
 *
 * ========================================================================
 * 3. Tabla: `cuenta_usuario`
 * ========================================================================
 * - Rol Principal: Tabla de unión (relación N:M o 1:N) que asocia uno o más
 * usuarios a una cuenta (Aunque los datos de ejemplo sugieren una relación
 * cercana al 1:1, el diseño soporta N:M).
 * - Relaciones: Claves foráneas hacia `cuenta` y `usuario`.
 * ========================================================================
 */

-- MySQL dump 10.13  Distrib 9.4.0, for Linux (aarch64)
--
-- Host: localhost    Database: usuarios
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` enum('ACTIVA','SUSPENDIDA') NOT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_renovacion_cupo` date DEFAULT NULL,
  `id_mercado_pago` varchar(255) NOT NULL,
  `km_consumidos_mes` double DEFAULT NULL,
  `saldo` decimal(38,2) DEFAULT NULL,
  `tipo_cuenta` enum('BASICA','PREMIUM') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,'ACTIVA','2025-11-11','2025-12-11','MP67890',0,2500.00,'PREMIUM'),(2,'ACTIVA','2025-11-11',NULL,'MP12345',0,1000.00,'BASICA'),(3,'SUSPENDIDA','2025-09-11',NULL,'MP54321',0,500.00,'BASICA'),(4,'ACTIVA','2025-10-11','2025-12-11','MP98765',0,3000.00,'PREMIUM'),(5,'ACTIVA','2025-11-11',NULL,'MP11223',0,750.00,'BASICA');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_usuario`
--

DROP TABLE IF EXISTS `cuenta_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_usuario` (
  `id_cuenta` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_cuenta`,`id_usuario`),
  KEY `FKglb2y52194gl9h907fuvp1mc7` (`id_usuario`),
  CONSTRAINT `FKa7m95ojwhc9mb44pucoxpwbxq` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `FKglb2y52194gl9h907fuvp1mc7` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_usuario`
--

LOCK TABLES `cuenta_usuario` WRITE;
/*!40000 ALTER TABLE `cuenta_usuario` DISABLE KEYS */;
INSERT INTO `cuenta_usuario` VALUES (1,1),(2,1),(3,2),(4,3),(5,4);
/*!40000 ALTER TABLE `cuenta_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `rol` enum('ADMIN','MANTENIMIENTO','USUARIO') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5171l57faosmj8myawaucatdw` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Perez',NULL,NULL,NULL,'Juan','ADMIN'),(2,'Gomez',NULL,NULL,NULL,'Maria','USUARIO'),(3,'Lopez',NULL,NULL,NULL,'Carlos','USUARIO'),(4,'Martinez',NULL,NULL,NULL,'Ana','USUARIO'),(5,'Rodriguez',NULL,NULL,NULL,'Luis','USUARIO');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-17 20:01:27
