-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2025 at 05:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_washnow_laundry`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_laundry`
--

CREATE TABLE `data_laundry` (
  `field_id` int(11) NOT NULL,
  `jenis_cuci` varchar(20) NOT NULL,
  `harga` varchar(15) NOT NULL,
  `berat` varchar(15) NOT NULL,
  `total_harga` varchar(15) NOT NULL,
  `bayar` varchar(15) NOT NULL,
  `kembalian` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `data_laundry`
--

INSERT INTO `data_laundry` (`field_id`, `jenis_cuci`, `harga`, `berat`, `total_harga`, `bayar`, `kembalian`) VALUES
(17, 'Cuci Basah', '3000', '5', '15000', '20000', '5000'),
(18, 'Cuci Setrika', '5000', '2', '10000', '5000', '-5000'),
(19, 'Cuci Basah', '3000', '1', '3000', '5000', '2000'),
(20, 'Cuci Kering', '4000', '2', '8000', '5000', '-3000'),
(21, 'Cuci Basah', '3000', '1', '3000', '10000', '7000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_laundry`
--
ALTER TABLE `data_laundry`
  ADD PRIMARY KEY (`field_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_laundry`
--
ALTER TABLE `data_laundry`
  MODIFY `field_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
