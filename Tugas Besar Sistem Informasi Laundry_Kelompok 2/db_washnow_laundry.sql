-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 18, 2025 at 12:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

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
  `nama` varchar(255) DEFAULT NULL,
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

INSERT INTO `data_laundry` (`field_id`, `nama`, `jenis_cuci`, `harga`, `berat`, `total_harga`, `bayar`, `kembalian`) VALUES
(28, 'byhq', 'Cuci Kering', '15000', '2', '30000', '50000', '20000'),
(30, 'abdoor', 'Cuci Setrika', '20000', '1', '20000', '50000', '50000'),
(31, 'udin', 'Cuci Basah', '10000', '7', '70000', '100000', '30000'),
(34, 'nana', 'Cuci Kering', '15000', '2', '30000', '50000', '20000'),
(35, 'nawa bayhaqi', 'Cuci Kering', '15000', '4', '60000', '100000', '40000'),
(36, 'alluka', 'Cuci Setrika', '20000', '4', '80000', '100000', '20000');

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
  MODIFY `field_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
