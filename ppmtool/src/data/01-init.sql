--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `project_identifier` varchar(5) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nh7jg4qyw1dm5y0bn2vwoq6v2` (`project_identifier`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `backlog`
--

DROP TABLE IF EXISTS `backlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `backlog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ptsequence` int DEFAULT NULL,
  `project_identifier` varchar(255) DEFAULT NULL,
  `project_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b68xx4kbe8dn8ev03nkqexcjp` (`project_id`),
  CONSTRAINT `FKl9uchve1jja83kpywpr72gi8k` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
DROP TABLE IF EXISTS `project_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `acceptance_criteria` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `due_date` datetime(6) DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `project_identifier` varchar(255) DEFAULT NULL,
  `project_sequence` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `backlog_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lk2ru5up8ilfm5wkq7wp6rtce` (`project_sequence`),
  KEY `FKnhbtfc4k2v2fsl0s3rm7uc7w3` (`backlog_id`),
  CONSTRAINT `FKnhbtfc4k2v2fsl0s3rm7uc7w3` FOREIGN KEY (`backlog_id`) REFERENCES `backlog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
