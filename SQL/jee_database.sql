-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 21 déc. 2020 à 16:31
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `jee_database`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite`
--

CREATE TABLE `activite` (
                            `id` bigint(20) NOT NULL,
                            `utilisateur` bigint(20) NOT NULL,
                            `nom` text NOT NULL,
                            `debut` datetime NOT NULL,
                            `fin` datetime NOT NULL,
                            `lieu` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

CREATE TABLE `amis` (
                        `ami1` bigint(20) NOT NULL,
                        `ami2` bigint(20) NOT NULL,
                        `etat` enum('demande_acceptee','demande_en_attente','demande_refusee') NOT NULL DEFAULT 'demande_en_attente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

CREATE TABLE `lieu` (
                        `id` bigint(20) NOT NULL,
                        `nom` text NOT NULL,
                        `description` text DEFAULT NULL,
                        `adresse` text NOT NULL,
                        `latitude` double NOT NULL,
                        `longitude` double NOT NULL,
                        `image` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
                                `id` bigint(20) NOT NULL,
                                `message` text NOT NULL,
                                `date` datetime NOT NULL,
                                `etat` enum('lue','non_lue','archivee') NOT NULL,
                                `source` bigint(20) NOT NULL,
                                `destination` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
                               `id` bigint(20) NOT NULL,
                               `email` varchar(255) NOT NULL,
                               `mot_de_passe` blob NOT NULL,
                               `nom` text NOT NULL,
                               `prenom` text NOT NULL,
                               `date_naissance` date NOT NULL,
                               `contamine` tinyint(4) NOT NULL DEFAULT 0,
                               `date_contamination` date DEFAULT NULL,
                               `role` enum('admin', 'user') NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activite`
--
ALTER TABLE `activite`
    ADD PRIMARY KEY (`id`),
    ADD KEY `fk_activite` (`utilisateur`),
    ADD KEY `fk_lieu` (`lieu`);

--
-- Index pour la table `amis`
--
ALTER TABLE `amis`
    ADD PRIMARY KEY (`ami1`,`ami2`);

--
-- Index pour la table `lieu`
--
ALTER TABLE `lieu`
    ADD PRIMARY KEY (`id`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
    ADD PRIMARY KEY (`id`),
    ADD KEY `fk_source` (`source`),
    ADD KEY `fk_destination` (`destination`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `UNIQUE_EMAIL` (`email`) USING HASH;

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activite`
--
ALTER TABLE `activite`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `lieu`
--
ALTER TABLE `lieu`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activite`
--
ALTER TABLE `activite`
    ADD CONSTRAINT `fk_activite` FOREIGN KEY (`utilisateur`) REFERENCES `utilisateur` (`id`),
    ADD CONSTRAINT `fk_lieu` FOREIGN KEY (`lieu`) REFERENCES `lieu` (`id`);

--
-- Contraintes pour la table `amis`
--
ALTER TABLE `amis`
    ADD CONSTRAINT `fk_ami1` FOREIGN KEY (`ami1`) REFERENCES `utilisateur` (`id`),
    ADD CONSTRAINT `fk_ami2` FOREIGN KEY (`ami2`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
    ADD CONSTRAINT `fk_destination` FOREIGN KEY (`destination`) REFERENCES `utilisateur` (`id`),
    ADD CONSTRAINT `fk_source` FOREIGN KEY (`source`) REFERENCES `utilisateur` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
