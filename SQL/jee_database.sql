-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : ven. 08 jan. 2021 à 16:15
-- Version du serveur :  8.0.22
-- Version de PHP : 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
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
                            `id` bigint NOT NULL,
                            `utilisateur` bigint NOT NULL,
                            `nom` text NOT NULL,
                            `debut` datetime NOT NULL,
                            `fin` datetime NOT NULL,
                            `lieu` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

CREATE TABLE `amis` (
                        `ami1` bigint NOT NULL,
                        `ami2` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

CREATE TABLE `lieu` (
                        `id` bigint NOT NULL,
                        `nom` varchar(255) NOT NULL,
                        `description` text,
                        `adresse` text NOT NULL,
                        `latitude` double NOT NULL,
                        `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
                                `id` bigint NOT NULL,
                                `message` text NOT NULL,
                                `date` datetime NOT NULL,
                                `etat` enum('lue','non_lue','archivee') NOT NULL,
                                `type` enum('ami','covid','del_ami') NOT NULL,
                                `source` bigint NOT NULL,
                                `destination` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
                               `id` bigint NOT NULL,
                               `email` varchar(255) NOT NULL,
                               `pseudo` varchar(255) NOT NULL,
                               `mot_de_passe` blob NOT NULL,
                               `nom` text NOT NULL,
                               `prenom` text NOT NULL,
                               `date_naissance` date NOT NULL,
                               `contamine` tinyint NOT NULL DEFAULT '0',
                               `date_contamination` date DEFAULT NULL,
                               `role` enum('admin','user') NOT NULL DEFAULT 'user',
                               `image` mediumblob,
                               `nomimage` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `pseudo`, `mot_de_passe`, `nom`, `prenom`, `date_naissance`, `contamine`, `date_contamination`, `role`, `image`, `nomimage`) VALUES
(4, 'admin@admin.fr', 'admin', 0x18dab36755a5d24999012bc13b78839e, 'admin', 'admin', '1999-02-28', 0, NULL, 'admin', NULL, NULL);

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
    ADD PRIMARY KEY (`ami1`,`ami2`),
  ADD KEY `fk_ami2` (`ami2`);

--
-- Index pour la table `lieu`
--
ALTER TABLE `lieu`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nom` (`nom`);

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
  ADD UNIQUE KEY `UNIQUE_EMAIL` (`email`),
  ADD UNIQUE KEY `pseudo` (`pseudo`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activite`
--
ALTER TABLE `activite`
    MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `lieu`
--
ALTER TABLE `lieu`
    MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
    MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
    MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activite`
--
ALTER TABLE `activite`
    ADD CONSTRAINT `fk_activite` FOREIGN KEY (`utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_lieu` FOREIGN KEY (`lieu`) REFERENCES `lieu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `amis`
--
ALTER TABLE `amis`
    ADD CONSTRAINT `fk_ami1` FOREIGN KEY (`ami1`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ami2` FOREIGN KEY (`ami2`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
    ADD CONSTRAINT `fk_destination` FOREIGN KEY (`destination`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_source` FOREIGN KEY (`source`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;