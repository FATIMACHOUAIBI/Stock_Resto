package com.example.stock_resto.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "produitStock")
public class ProduitStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStock", nullable = false)
    private Integer idStock;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Datetime", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date DateTime;
    @ManyToOne
    @JoinColumn(name="idStatue")
    private Statue statue;
    @ManyToOne
    @JoinColumn(name="idProduitRef")
    private ProduitRef produitRef;

    public ProduitStock() {
    }

    public ProduitStock(Date dateTime, Statue statue, ProduitRef produitRef) {
        DateTime = dateTime;
        this.statue = statue;
        this.produitRef = produitRef;
    }

    public Integer getIdStock() {

        return idStock;
    }

    public void setIdStock(Integer idStock) {

        this.idStock = idStock;
    }

    public Date getDateTime() {

        return DateTime;
    }

    public void setDateTime(Date dateTime) {

        DateTime = dateTime;
    }

    public Statue getStatue() {

        return statue;
    }

    public void setStatue(Statue statue) {

        this.statue = statue;
    }

    public ProduitRef getProduitRef() {
        return produitRef;
    }

    public void setProduitRef(ProduitRef produitRef) {

        this.produitRef = produitRef;
    }

    @Override
    public String toString() {
        return "ProduitStock{" +
                "idStock=" + idStock +
                ", DateTime=" + DateTime +
                ", statue=" + statue +
                ", produitRef=" + produitRef +
                '}';
    }
}
