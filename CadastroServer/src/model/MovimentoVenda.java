/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author canal
 */
@Entity
@Table(name = "MovimentoVenda")
@NamedQueries({
    @NamedQuery(name = "MovimentoVenda.findAll", query = "SELECT m FROM MovimentoVenda m"),
    @NamedQuery(name = "MovimentoVenda.findByIDMovimento", query = "SELECT m FROM MovimentoVenda m WHERE m.iDMovimento = :iDMovimento"),
    @NamedQuery(name = "MovimentoVenda.findByQuantidade", query = "SELECT m FROM MovimentoVenda m WHERE m.quantidade = :quantidade")})
public class MovimentoVenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMovimento")
    private Integer iDMovimento;
    @Column(name = "Quantidade")
    private Integer quantidade;
    @JoinColumn(name = "IDPessoaFisica", referencedColumnName = "IDPessoa")
    @ManyToOne
    private PessoaFisica iDPessoaFisica;
    @JoinColumn(name = "IDProduto", referencedColumnName = "IDProduto")
    @ManyToOne
    private Produtos iDProduto;

    public MovimentoVenda() {
    }

    public MovimentoVenda(Integer iDMovimento) {
        this.iDMovimento = iDMovimento;
    }

    public Integer getIDMovimento() {
        return iDMovimento;
    }

    public void setIDMovimento(Integer iDMovimento) {
        this.iDMovimento = iDMovimento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public PessoaFisica getIDPessoaFisica() {
        return iDPessoaFisica;
    }

    public void setIDPessoaFisica(PessoaFisica iDPessoaFisica) {
        this.iDPessoaFisica = iDPessoaFisica;
    }

    public Produtos getIDProduto() {
        return iDProduto;
    }

    public void setIDProduto(Produtos iDProduto) {
        this.iDProduto = iDProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMovimento != null ? iDMovimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimentoVenda)) {
            return false;
        }
        MovimentoVenda other = (MovimentoVenda) object;
        if ((this.iDMovimento == null && other.iDMovimento != null) || (this.iDMovimento != null && !this.iDMovimento.equals(other.iDMovimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MovimentoVenda[ iDMovimento=" + iDMovimento + " ]";
    }
    
}
