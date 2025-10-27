package com.example;

import com.example.beans.*;
import com.example.service.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Initialisation des services
            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();

            System.out.println("=== APPLICATION DE GESTION DE L'ÉTAT CIVIL ===\n");

            // 1. Créer 10 femmes et 5 hommes
            System.out.println("1. CRÉATION DES 10 FEMMES ET 5 HOMMES :");
            System.out.println("===========================================");

            // Création des hommes
            Homme h1 = new Homme("SAFI", "SAID", "0612345678", "Casablanca", "AB123456", sdf.parse("15/05/1970"));
            Homme h2 = new Homme("ALAMI", "MOHAMED", "0612345679", "Rabat", "AB123457", sdf.parse("20/08/1975"));
            Homme h3 = new Homme("RAJI", "AHMED", "0612345680", "Marrakech", "AB123458", sdf.parse("10/03/1980"));
            Homme h4 = new Homme("BENNANI", "KARIM", "0612345681", "Fès", "AB123459", sdf.parse("25/12/1978"));
            Homme h5 = new Homme("CHOUKRI", "YOUSSEF", "0612345682", "Tanger", "AB123460", sdf.parse("30/06/1982"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);

            // Création des femmes
            Femme f1 = new Femme("SALIMA", "RAMI", "0612345683", "Casablanca", "CD123456", sdf.parse("12/02/1972"));
            Femme f2 = new Femme("AMAL", "ALI", "0612345684", "Rabat", "CD123457", sdf.parse("18/07/1976"));
            Femme f3 = new Femme("WAFA", "ALAOUI", "0612345685", "Marrakech", "CD123458", sdf.parse("22/11/1980"));
            Femme f4 = new Femme("KARIMA", "ALAMI", "0612345686", "Fès", "CD123459", sdf.parse("05/04/1974"));
            Femme f5 = new Femme("FATIMA", "BENNANI", "0612345687", "Tanger", "CD123460", sdf.parse("15/09/1978"));
            Femme f6 = new Femme("NADIA", "CHOUKRI", "0612345688", "Casablanca", "CD123461", sdf.parse("30/01/1982"));
            Femme f7 = new Femme("SOUAD", "RAJI", "0612345689", "Rabat", "CD123462", sdf.parse("25/06/1975"));
            Femme f8 = new Femme("HANAE", "SAFI", "0612345690", "Marrakech", "CD123463", sdf.parse("08/12/1979"));
            Femme f9 = new Femme("IMANE", "ALAOUI", "0612345691", "Fès", "CD123464", sdf.parse("19/03/1981"));
            Femme f10 = new Femme("LEILA", "BENNANI", "0612345692", "Tanger", "CD123465", sdf.parse("14/07/1977"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);

            System.out.println("✓ 5 hommes créés avec succès");
            System.out.println("✓ 10 femmes créées avec succès");

            // 2. Création des mariages
            System.out.println("\n2. CRÉATION DES MARIAGES :");
            System.out.println("===========================");

            // Mariages pour SAFI SAID (h1)
            Mariage m1 = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1);  // En cours
            Mariage m2 = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2);  // En cours
            Mariage m3 = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3);  // En cours
            Mariage m4 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4); // Échoué

            // Mariages pour ALAMI MOHAMED (h2)
            Mariage m5 = new Mariage(sdf.parse("15/06/1998"), null, 1, h2, f5);
            Mariage m6 = new Mariage(sdf.parse("20/08/2002"), null, 2, h2, f6);

            // Mariages pour RAJI AHMED (h3)
            Mariage m7 = new Mariage(sdf.parse("10/05/2001"), null, 3, h3, f7);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);

            System.out.println("✓ 7 mariages créés avec succès");

            // 3. Tests demandés dans l'exercice
            System.out.println("\n3. TESTS DE L'APPLICATION :");
            System.out.println("===========================");

            // A. Afficher la liste des femmes (sans doublons)
            System.out.println("\nA. LISTE DES FEMMES :");
            System.out.println("---------------------");
            List<Femme> femmes = femmeService.findAll();
            Set<Integer> idsDejaVus = new HashSet<>();
            int count = 1;
            for (Femme femme : femmes) {
                if (idsDejaVus.add(femme.getId())) { // Éviter les doublons
                    System.out.println(count + ". " + femme.getNom() + " " + femme.getPrenom() +
                            " - CIN: " + femme.getCin() + " - Tél: " + femme.getTelephone());
                    count++;
                }
                if (count > 10) break; // Seulement 10 femmes
            }

            // B. Afficher la femme la plus âgée
            System.out.println("\nB. FEMME LA PLUS ÂGÉE :");
            System.out.println("-----------------------");
            Femme femmeAgee = femmeService.getFemmeLaPlusAgee();
            if (femmeAgee != null) {
                System.out.println("✓ " + femmeAgee.getNom() + " " + femmeAgee.getPrenom() +
                        " (Née le: " + sdf.format(femmeAgee.getDateNaissance()) + ")");
            }

            // C. Afficher les épouses d'un homme donné
            System.out.println("\nC. ÉPOUSES DE SAFI SAID ENTRE 1990 ET 2000 :");
            System.out.println("---------------------------------------------");
            List<Femme> epouses = hommeService.getEpousesBetweenDates(h1.getId(),
                    sdf.parse("01/01/1990"), sdf.parse("31/12/2000"));
            if (epouses.isEmpty()) {
                System.out.println("Aucune épouse trouvée dans cette période");
            } else {
                for (Femme epouse : epouses) {
                    System.out.println("✓ " + epouse.getNom() + " " + epouse.getPrenom());
                }
            }

            // D. Afficher le nombre d'enfants d'une femme entre deux dates (CORRIGÉ)
            System.out.println("\nD. NOMBRE D'ENFANTS DE SALIMA RAMI ENTRE 1990 ET 2000 :");
            System.out.println("-------------------------------------------------------");
            int nbrEnfants = femmeService.getNombreEnfantsBetweenDates(f1.getId(),
                    sdf.parse("01/01/1990"), sdf.parse("31/12/2000"));
            System.out.println("✓ Nombre d'enfants: " + nbrEnfants);

            // E. Afficher les femmes mariées deux fois ou plus
            System.out.println("\nE. FEMMES MARIÉES AU MOINS DEUX FOIS :");
            System.out.println("--------------------------------------");
            List<Femme> femmesMultiMaries = femmeService.getFemmesMarieesAuMoinsDeuxFois();
            if (femmesMultiMaries.isEmpty()) {
                System.out.println("Aucune femme mariée au moins deux fois");
            } else {
                for (Femme femme : femmesMultiMaries) {
                    System.out.println("✓ " + femme.getNom() + " " + femme.getPrenom());
                }
            }

            // F. Afficher les hommes mariés à 4 femmes entre deux dates
            System.out.println("\nF. HOMMES MARIÉS À 4 FEMMES ENTRE 1980 ET 2005 :");
            System.out.println("------------------------------------------------");
            List<Object[]> hommes4Femmes = femmeService.getHommesMarieesQuatreFemmesBetweenDates(
                    sdf.parse("01/01/1980"), sdf.parse("31/12/2005"));
            if (hommes4Femmes.isEmpty()) {
                System.out.println("Aucun homme marié à 4 femmes dans cette période");
            } else {
                for (Object[] result : hommes4Femmes) {
                    Homme homme = (Homme) result[0];
                    Long countFemmes = (Long) result[1];
                    System.out.println("✓ " + homme.getNom() + " " + homme.getPrenom() +
                            " (" + countFemmes + " femmes)");
                }
            }

            // G. Afficher les mariages d'un homme avec tous les détails
            System.out.println("\nG. DÉTAILS DES MARIAGES DE SAFI SAID :");
            System.out.println("--------------------------------------");
            hommeService.afficherMariagesAvecDetails(h1.getId());

            System.out.println("\n=== APPLICATION TERMINÉE AVEC SUCCÈS ===");

        } catch (Exception e) {
            System.err.println("❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
        }
    }
}