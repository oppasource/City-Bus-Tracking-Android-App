-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 05, 2018 at 01:42 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id3353585_bus_locator`
--
CREATE DATABASE IF NOT EXISTS `id3353585_bus_locator` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id3353585_bus_locator`;

-- --------------------------------------------------------

--
-- Table structure for table `Conductors`
--

CREATE TABLE `Conductors` (
  `conductor_id` int(11) NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Conductors`
--

INSERT INTO `Conductors` (`conductor_id`, `username`, `password`, `name`) VALUES
(1, 'pkjoshi', 'pk123', 'Prakash'),
(3, 'k10', 'k1010', 'Ketan');

-- --------------------------------------------------------

--
-- Table structure for table `Live`
--

CREATE TABLE `Live` (
  `id` int(20) NOT NULL,
  `conductor_id` int(20) NOT NULL,
  `conductor_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `route_number` int(20) NOT NULL,
  `vehicle_number` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `next_stop_order` int(20) DEFAULT NULL,
  `direction` int(5) DEFAULT NULL,
  `next_stop` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_stop` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `end_stop` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `loc2stop`
--

CREATE TABLE `loc2stop` (
  `latitude` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_stop_name` varchar(15) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `loc2stop`
--

INSERT INTO `loc2stop` (`latitude`, `longitude`, `bus_stop_name`) VALUES
('19.11945152282715', '72.84542083740234', 'Andheri'),
('19.15162467956543', '72.84441375732422', 'Goregaon'),
('19.13422203063965', '72.84782409667969', 'Jogeshwari'),
('19.11647224', '72.82917786', 'Juhu'),
('19.10237693786621', '72.8396987915039', 'Vile Parle');

-- --------------------------------------------------------

--
-- Table structure for table `next_distance`
--

CREATE TABLE `next_distance` (
  `conductor_id` int(11) NOT NULL,
  `distance_to_next` float NOT NULL,
  `next_stop_lat` double NOT NULL,
  `next_stop_long` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Routes`
--

CREATE TABLE `Routes` (
  `id` int(11) NOT NULL,
  `bus_number` int(11) NOT NULL,
  `bus_stop_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `stop_order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Routes`
--

INSERT INTO `Routes` (`id`, `bus_number`, `bus_stop_name`, `stop_order`) VALUES
(1, 201, 'Goregaon', 1),
(2, 201, 'Jogeshwari', 2),
(3, 201, 'Andheri', 3),
(6, 201, 'Vile Parle', 4),
(7, 202, 'Goregaon', 1),
(8, 202, 'Jogeshwari', 2),
(9, 202, 'Andheri', 3),
(10, 202, 'Juhu', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Conductors`
--
ALTER TABLE `Conductors`
  ADD PRIMARY KEY (`conductor_id`);

--
-- Indexes for table `Live`
--
ALTER TABLE `Live`
  ADD PRIMARY KEY (`id`),
  ADD KEY `conductor_id` (`conductor_id`);

--
-- Indexes for table `loc2stop`
--
ALTER TABLE `loc2stop`
  ADD PRIMARY KEY (`bus_stop_name`);

--
-- Indexes for table `next_distance`
--
ALTER TABLE `next_distance`
  ADD PRIMARY KEY (`conductor_id`);

--
-- Indexes for table `Routes`
--
ALTER TABLE `Routes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_stop_name` (`bus_stop_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Conductors`
--
ALTER TABLE `Conductors`
  MODIFY `conductor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `Live`
--
ALTER TABLE `Live`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=133;

--
-- AUTO_INCREMENT for table `Routes`
--
ALTER TABLE `Routes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Live`
--
ALTER TABLE `Live`
  ADD CONSTRAINT `Live_ibfk_1` FOREIGN KEY (`conductor_id`) REFERENCES `Conductors` (`conductor_id`);

--
-- Constraints for table `Routes`
--
ALTER TABLE `Routes`
  ADD CONSTRAINT `Routes_ibfk_1` FOREIGN KEY (`bus_stop_name`) REFERENCES `loc2stop` (`bus_stop_name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
