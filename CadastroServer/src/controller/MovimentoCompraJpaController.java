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
import model.MovimentoCompra;
import model.PessoaJuridica;
import model.Produtos;

/**
 *
 * @author canal
 */
public class MovimentoCompraJpaController implements Serializable {

    public MovimentoCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MovimentoCompra movimentoCompra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PessoaJuridica IDPessoaJuridica = movimentoCompra.getIDPessoaJuridica();
            if (IDPessoaJuridica != null) {
                IDPessoaJuridica = em.getReference(IDPessoaJuridica.getClass(), IDPessoaJuridica.getIDPessoa());
                movimentoCompra.setIDPessoaJuridica(IDPessoaJuridica);
            }
            Produtos IDProduto = movimentoCompra.getIDProduto();
            if (IDProduto != null) {
                IDProduto = em.getReference(IDProduto.getClass(), IDProduto.getIDProduto());
                movimentoCompra.setIDProduto(IDProduto);
            }
            em.persist(movimentoCompra);
            if (IDPessoaJuridica != null) {
                IDPessoaJuridica.getMovimentoCompraCollection().add(movimentoCompra);
                IDPessoaJuridica = em.merge(IDPessoaJuridica);
            }
            if (IDProduto != null) {
                IDProduto.getMovimentoCompraCollection().add(movimentoCompra);
                IDProduto = em.merge(IDProduto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimentoCompra(movimentoCompra.getIDMovimento()) != null) {
                throw new PreexistingEntityException("MovimentoCompra " + movimentoCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MovimentoCompra movimentoCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimentoCompra persistentMovimentoCompra = em.find(MovimentoCompra.class, movimentoCompra.getIDMovimento());
            PessoaJuridica IDPessoaJuridicaOld = persistentMovimentoCompra.getIDPessoaJuridica();
            PessoaJuridica IDPessoaJuridicaNew = movimentoCompra.getIDPessoaJuridica();
            Produtos IDProdutoOld = persistentMovimentoCompra.getIDProduto();
            Produtos IDProdutoNew = movimentoCompra.getIDProduto();
            if (IDPessoaJuridicaNew != null) {
                IDPessoaJuridicaNew = em.getReference(IDPessoaJuridicaNew.getClass(), IDPessoaJuridicaNew.getIDPessoa());
                movimentoCompra.setIDPessoaJuridica(IDPessoaJuridicaNew);
            }
            if (IDProdutoNew != null) {
                IDProdutoNew = em.getReference(IDProdutoNew.getClass(), IDProdutoNew.getIDProduto());
                movimentoCompra.setIDProduto(IDProdutoNew);
            }
            movimentoCompra = em.merge(movimentoCompra);
            if (IDPessoaJuridicaOld != null && !IDPessoaJuridicaOld.equals(IDPessoaJuridicaNew)) {
                IDPessoaJuridicaOld.getMovimentoCompraCollection().remove(movimentoCompra);
                IDPessoaJuridicaOld = em.merge(IDPessoaJuridicaOld);
            }
            if (IDPessoaJuridicaNew != null && !IDPessoaJuridicaNew.equals(IDPessoaJuridicaOld)) {
                IDPessoaJuridicaNew.getMovimentoCompraCollection().add(movimentoCompra);
                IDPessoaJuridicaNew = em.merge(IDPessoaJuridicaNew);
            }
            if (IDProdutoOld != null && !IDProdutoOld.equals(IDProdutoNew)) {
                IDProdutoOld.getMovimentoCompraCollection().remove(movimentoCompra);
                IDProdutoOld = em.merge(IDProdutoOld);
            }
            if (IDProdutoNew != null && !IDProdutoNew.equals(IDProdutoOld)) {
                IDProdutoNew.getMovimentoCompraCollection().add(movimentoCompra);
                IDProdutoNew = em.merge(IDProdutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimentoCompra.getIDMovimento();
                if (findMovimentoCompra(id) == null) {
                    throw new NonexistentEntityException("The movimentoCompra with id " + id + " no longer exists.");
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
            MovimentoCompra movimentoCompra;
            try {
                movimentoCompra = em.getReference(MovimentoCompra.class, id);
                movimentoCompra.getIDMovimento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimentoCompra with id " + id + " no longer exists.", enfe);
            }
            PessoaJuridica IDPessoaJuridica = movimentoCompra.getIDPessoaJuridica();
            if (IDPessoaJuridica != null) {
                IDPessoaJuridica.getMovimentoCompraCollection().remove(movimentoCompra);
                IDPessoaJuridica = em.merge(IDPessoaJuridica);
            }
            Produtos IDProduto = movimentoCompra.getIDProduto();
            if (IDProduto != null) {
                IDProduto.getMovimentoCompraCollection().remove(movimentoCompra);
                IDProduto = em.merge(IDProduto);
            }
            em.remove(movimentoCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MovimentoCompra> findMovimentoCompraEntities() {
        return findMovimentoCompraEntities(true, -1, -1);
    }

    public List<MovimentoCompra> findMovimentoCompraEntities(int maxResults, int firstResult) {
        return findMovimentoCompraEntities(false, maxResults, firstResult);
    }

    private List<MovimentoCompra> findMovimentoCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MovimentoCompra.class));
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

    public MovimentoCompra findMovimentoCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimentoCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimentoCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MovimentoCompra> rt = cq.from(MovimentoCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
