package com.example.service;

import com.example.dao.Dao;
import com.example.beans.Femme;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.util.HibernateUtil;
import java.util.Date;
import java.util.List;

public class FemmeService extends Dao<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    // Nombre d'enfants d'une femme entre deux dates (HQL) - CORRIGÉ
    public int getNombreEnfantsBetweenDates(int femmeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // CORRECTION : Utiliser Long au lieu de Integer
            Query<Long> query = session.createQuery(
                    "SELECT SUM(m.nbrEnfants) FROM Mariage m " +
                            "WHERE m.femme.id = :femmeId " +
                            "AND m.dateDebut BETWEEN :dateDebut AND :dateFin", Long.class);

            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            Long result = query.uniqueResult();
            return result != null ? result.intValue() : 0;
        } finally {
            session.close();
        }
    }

    // Femmes mariées au moins deux fois
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                    "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2", Femme.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Hommes mariés à 4 femmes entre deux dates
    public List<Object[]> getHommesMarieesQuatreFemmesBetweenDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Object[]> query = session.createQuery(
                    "SELECT h, COUNT(DISTINCT m.femme) FROM Homme h JOIN h.mariages m " +
                            "WHERE m.dateDebut BETWEEN :dateDebut AND :dateFin " +
                            "GROUP BY h HAVING COUNT(DISTINCT m.femme) = 4", Object[].class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            return query.list();
        } finally {
            session.close();
        }
    }

    // Trouver la femme la plus âgée
    public Femme getFemmeLaPlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                    "FROM Femme ORDER BY dateNaissance ASC", Femme.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
}