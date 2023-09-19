/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.MovimentoVenda;
import model.PessoaFisica;
import model.Produtos;

/**
 *
 * @author canal
 */
public class MovimentoVendaJpaController implements Serializable {

    public MovimentoVendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MovimentoVenda movimentoVenda) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaFisica IDPessoaFisica = movimentoVenda.getIDPessoaFisica();
            if (IDPessoaFisica != null) {
                IDPessoaFisica = em.getReference(IDPessoaFisica.getClass(), IDPessoaFisica.getIDPessoa());
                movimentoVenda.setIDPessoaFisica(IDPessoaFisica);
            }
            Produtos IDProduto = movimentoVenda.getIDProduto();
            if (IDProduto != null) {
                IDProduto = em.getReference(IDProduto.getClass(), IDProduto.getIDProduto());
                movimentoVenda.setIDProduto(IDProduto);
            }
            em.persist(movimentoVenda);
            if (IDPessoaFisica != null) {
                IDPessoaFisica.getMovimentoVendaCollection().add(movimentoVenda);
                IDPessoaFisica = em.merge(IDPessoaFisica);
            }
            if (IDProduto != null) {
                IDProduto.getMovimentoVendaCollection().add(movimentoVenda);
                IDProduto = em.merge(IDProduto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimentoVenda(movimentoVenda.getIDMovimento()) != null) {
                throw new PreexistingEntityException("MovimentoVenda " + movimentoVenda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MovimentoVenda movimentoVenda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimentoVenda persistentMovimentoVenda = em.find(MovimentoVenda.class, movimentoVenda.getIDMovimento());
            PessoaFisica IDPessoaFisicaOld = persistentMovimentoVenda.getIDPessoaFisica();
            PessoaFisica IDPessoaFisicaNew = movimentoVenda.getIDPessoaFisica();
            Produtos IDProdutoOld = persistentMovimentoVenda.getIDProduto();
            Produtos IDProdutoNew = movimentoVenda.getIDProduto();
            if (IDPessoaFisicaNew != null) {
                IDPessoaFisicaNew = em.getReference(IDPessoaFisicaNew.getClass(), IDPessoaFisicaNew.getIDPessoa());
                movimentoVenda.setIDPessoaFisica(IDPessoaFisicaNew);
            }
            if (IDProdutoNew != null) {
                IDProdutoNew = em.getReference(IDProdutoNew.getClass(), IDProdutoNew.getIDProduto());
                movimentoVenda.setIDProduto(IDProdutoNew);
            }
            movimentoVenda = em.merge(movimentoVenda);
            if (IDPessoaFisicaOld != null && !IDPessoaFisicaOld.equals(IDPessoaFisicaNew)) {
                IDPessoaFisicaOld.getMovimentoVendaCollection().remove(movimentoVenda);
                IDPessoaFisicaOld = em.merge(IDPessoaFisicaOld);
            }
            if (IDPessoaFisicaNew != null && !IDPessoaFisicaNew.equals(IDPessoaFisicaOld)) {
                IDPessoaFisicaNew.getMovimentoVendaCollection().add(movimentoVenda);
                IDPessoaFisicaNew = em.merge(IDPessoaFisicaNew);
            }
            if (IDProdutoOld != null && !IDProdutoOld.equals(IDProdutoNew)) {
                IDProdutoOld.getMovimentoVendaCollection().remove(movimentoVenda);
                IDProdutoOld = em.merge(IDProdutoOld);
            }
            if (IDProdutoNew != null && !IDProdutoNew.equals(IDProdutoOld)) {
                IDProdutoNew.getMovimentoVendaCollection().add(movimentoVenda);
                IDProdutoNew = em.merge(IDProdutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimentoVenda.getIDMovimento();
                if (findMovimentoVenda(id) == null) {
                    throw new NonexistentEntityException("The movimentoVenda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimentoVenda movimentoVenda;
            try {
                movimentoVenda = em.getReference(MovimentoVenda.class, id);
                movimentoVenda.getIDMovimento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimentoVenda with id " + id + " no longer exists.", enfe);
            }
            PessoaFisica IDPessoaFisica = movimentoVenda.getIDPessoaFisica();
            if (IDPessoaFisica != null) {
                IDPessoaFisica.getMovimentoVendaCollection().remove(movimentoVenda);
                IDPessoaFisica = em.merge(IDPessoaFisica);
            }
            Produtos IDProduto = movimentoVenda.getIDProduto();
            if (IDProduto != null) {
                IDProduto.getMovimentoVendaCollection().remove(movimentoVenda);
                IDProduto = em.merge(IDProduto);
            }
            em.remove(movimentoVenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MovimentoVenda> findMovimentoVendaEntities() {
        return findMovimentoVendaEntities(true, -1, -1);
    }

    public List<MovimentoVenda> findMovimentoVendaEntities(int maxResults, int firstResult) {
        return findMovimentoVendaEntities(false, maxResults, firstResult);
    }

    private List<MovimentoVenda> findMovimentoVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MovimentoVenda.class));
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

    public MovimentoVenda findMovimentoVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimentoVenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimentoVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MovimentoVenda> rt = cq.from(MovimentoVenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
