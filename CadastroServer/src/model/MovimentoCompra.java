/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MovimentoCompra")
@NamedQueries({
    @NamedQuery(name = "MovimentoCompra.findAll", query = "SELECT m FROM MovimentoCompra m"),
    @NamedQuery(name = "MovimentoCompra.findByIDMovimento", query = "SELECT m FROM MovimentoCompra m WHERE m.iDMovimento = :iDMovimento"),
    @NamedQuery(name = "MovimentoCompra.findByQuantidade", query = "SELECT m FROM MovimentoCompra m WHERE m.quantidade = :quantidade"),
    @NamedQuery(name = "MovimentoCompra.findByPrecoUnitario", query = "SELECT m FROM MovimentoCompra m WHERE m.precoUnitario = :precoUnitario")})
public class MovimentoCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMovimento")
    private Integer iDMovimento;
    @Column(name = "Quantidade")
    private Integer quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PrecoUnitario")
    private BigDecimal precoUnitario;
    @JoinColumn(name = "IDPessoaJuridica", referencedColumnName = "IDPessoa")
    @ManyToOne
    private PessoaJuridica iDPessoaJuridica;
    @JoinColumn(name = "IDProduto", referencedColumnName = "IDProduto")
    @ManyToOne
    private Produtos iDProduto;

    public MovimentoCompra() {
    }

    public MovimentoCompra(Integer iDMovimento) {
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

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public PessoaJuridica getIDPessoaJuridica() {
        return iDPessoaJuridica;
    }

    public void setIDPessoaJuridica(PessoaJuridica iDPessoaJuridica) {
        this.iDPessoaJuridica = iDPessoaJuridica;
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
        if (!(object instanceof MovimentoCompra)) {
            return false;
        }
        MovimentoCompra other = (MovimentoCompra) object;
        if ((this.iDMovimento == null && other.iDMovimento != null) || (this.iDMovimento != null && !this.iDMovimento.equals(other.iDMovimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MovimentoCompra[ iDMovimento=" + iDMovimento + " ]";
    }
    
}
