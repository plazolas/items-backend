
--
-- Table structure for table `app_user`
--
-- DROP TABLE IF EXISTS `app_user`;
CREATE TABLE IF NOT EXISTS `app_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `email` varchar(255) NOT NULL,
                            `password` varchar(255) NOT NULL,
                            `first_name` varchar(255) DEFAULT NULL,
                            `last_name` varchar(255) DEFAULT NULL,
                            `active` tinyint(1) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `email` (`email`)
);
--
-- Table structure for table `country`
--
-- DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
);

--
-- Table structure for table `hibernate_sequence`
--
-- DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
                                      `next_val` bigint DEFAULT NULL
);

--
-- Table structure for table `orders`
--
-- DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `clientName` varchar(50) DEFAULT NULL,
                          `client_name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
);

--
-- Table structure for table `page`
--
-- DROP TABLE IF EXISTS `page`;
CREATE TABLE IF NOT EXISTS `page` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `title` varchar(255) DEFAULT NULL,
                        `url` varchar(255) DEFAULT NULL,
                        `parent_page_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `url` (`url`)
);

--
-- Table structure for table `passport`
--
-- DROP TABLE IF EXISTS `passport`;
CREATE TABLE IF NOT EXISTS `passport` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `number` varchar(255) NOT NULL,
                            `pasword` varchar(255) NOT NULL,
                            `is_active` tinyint(1) DEFAULT NULL,
                            `roles` varchar(255) DEFAULT NULL,
                            `phone` varchar(255) DEFAULT NULL,
                            `expDate` datetime DEFAULT NULL,
                            `country_id` int DEFAULT NULL,
                            `exp_date` date DEFAULT NULL,
                            PRIMARY KEY (`id`)
);

--
-- Table structure for table `person`
--
-- DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `firstname` varchar(123) NOT NULL,
                          `lastname` varchar(123) NOT NULL,
                          `position` varchar(123) DEFAULT NULL,
                          `age` int DEFAULT NULL,
                          `boss` varchar(123) DEFAULT NULL,
                          `updated` datetime DEFAULT NULL,
                          `country_id` int DEFAULT NULL,
                          `passport_id` int DEFAULT NULL,
                          `first_name` varchar(255) NOT NULL,
                          `last_name` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `position` (`position`)
);

--
-- Table structure for table `role`
--
-- DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `role` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `role` (`role`)
);
--
-- Table structure for table `user`
--
-- DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(50) NOT NULL,
                        `is_active` tinyint(1) DEFAULT NULL,
                        `roles` varchar(255) DEFAULT NULL,
                        `phone` varchar(255) DEFAULT NULL,
                        `useremail` varchar(255) DEFAULT NULL,
                        `passport_id` int DEFAULT NULL,
                        `active` bit(1) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`)
);
--
-- Table structure for table `user_roles`
--
-- DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
                              `user_id` bigint NOT NULL,
                              `roles_id` bigint NOT NULL,
                              PRIMARY KEY (`user_id`,`roles_id`)
);