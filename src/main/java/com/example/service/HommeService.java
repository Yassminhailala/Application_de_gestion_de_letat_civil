package com.example.service;

import com.example.dao.Dao;
import com.example.beans.Homme;
import com.example.beans.Femme;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class HommeService extends Dao<Homme> {

    public HommeService() {
        super(Homme.class);
    }

    // Afficher les épouses d'un homme entre deux dates
    public List<Femme> getEpousesBetweenDates(int hommeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                    "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId " +
                            "AND m.dateDebut BETWEEN :dateDebut AND :dateFin", Femme.class);
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Afficher les mariages d'un homme avec détails - FORMAT CORRIGÉ
    public void afficherMariagesAvecDetails(int hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé");
                return;
            }

            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

            List<Object[]> result = session.createQuery(
                            "SELECT m.femme.nom, m.femme.prenom, m.dateDebut, m.dateFin, m.nbrEnfants " +
                                    "FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut", Object[].class)
                    .setParameter("hommeId", hommeId)
                    .list();

            // Séparer les mariages en cours et échoués
            List<Object[]> mariagesEnCours = session.createQuery(
                            "SELECT m.femme.nom, m.femme.prenom, m.dateDebut, m.dateFin, m.nbrEnfants " +
                                    "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL ORDER BY m.dateDebut", Object[].class)
                    .setParameter("hommeId", hommeId)
                    .list();

            List<Object[]> mariagesEchoues = session.createQuery(
                            "SELECT m.femme.nom, m.femme.prenom, m.dateDebut, m.dateFin, m.nbrEnfants " +
                                    "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL ORDER BY m.dateDebut", Object[].class)
                    .setParameter("hommeId", hommeId)
                    .list();

            // Afficher les mariages en cours
            System.out.println("Mariages En Cours :");
            int countEnCours = 1;
            for (Object[] row : mariagesEnCours) {
                String nomFemme = (String) row[0];
                String prenomFemme = (String) row[1];
                Date dateDebut = (Date) row[2];
                int nbrEnfants = (int) row[4];

                System.out.println(countEnCours + ". Femme : " + nomFemme + " " + prenomFemme +
                        "   Date Début : " + sdf.format(dateDebut) + "    Nbr Enfants : " + nbrEnfants);
                countEnCours++;
            }

            // Afficher les mariages échoués
            if (!mariagesEchoues.isEmpty()) {
                System.out.println("Mariages échoués :");
                int countEchoues = 1;
                for (Object[] row : mariagesEchoues) {
                    String nomFemme = (String) row[0];
                    String prenomFemme = (String) row[1];
                    Date dateDebut = (Date) row[2];
                    Date dateFin = (Date) row[3];
                    int nbrEnfants = (int) row[4];

                    System.out.println(countEchoues + ". Femme : " + nomFemme + " " + prenomFemme +
                            "  Date Début : " + sdf.format(dateDebut) + "    " +
                            "Date Fin : " + sdf.format(dateFin) + "    Nbr Enfants : " + nbrEnfants);
                    countEchoues++;
                }
            }
        } finally {
            session.close();
        }
    }
}