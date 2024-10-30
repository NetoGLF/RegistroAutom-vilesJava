package com.ernestoglf.automovil.persistencia;

import com.ernestoglf.automovil.logica.Automov;
import com.ernestoglf.automovil.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AutomovJpaController implements Serializable {

    public AutomovJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
        public AutomovJpaController(){
    
        emf = Persistence.createEntityManagerFactory("AutoPU");
        
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Automov automov) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(automov);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Automov automov) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            automov = em.merge(automov);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = automov.getId();
                if (findAutomov(id) == null) {
                    throw new NonexistentEntityException("The automov with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Automov automov;
            try {
                automov = em.getReference(Automov.class, id);
                automov.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The automov with id " + id + " no longer exists.", enfe);
            }
            em.remove(automov);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Automov> findAutomovEntities() {
        return findAutomovEntities(true, -1, -1);
    }

    public List<Automov> findAutomovEntities(int maxResults, int firstResult) {
        return findAutomovEntities(false, maxResults, firstResult);
    }

    private List<Automov> findAutomovEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Automov.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Automov findAutomov(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Automov.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutomovCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Automov> rt = cq.from(Automov.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
