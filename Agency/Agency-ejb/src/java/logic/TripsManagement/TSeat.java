/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import logic.UsersManagement.TUser;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "t_seat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TSeat.findAll", query = "SELECT t FROM TSeat t")
    , @NamedQuery(name = "TSeat.findById", query = "SELECT t FROM TSeat t WHERE t.id = :id")
    , @NamedQuery(name = "TSeat.findByLuggage", query = "SELECT t FROM TSeat t WHERE t.luggage = :luggage")
    , @NamedQuery(name = "TSeat.findByAuctioned", query = "SELECT t FROM TSeat t WHERE t.auctioned = :auctioned")
    , @NamedQuery(name = "TSeat.findByPrice", query = "SELECT t FROM TSeat t WHERE t.price = :price")})
public class TSeat implements Serializable {

    @ManyToMany(mappedBy = "tSeatCollection")
    private Collection<TPurchase> tPurchaseCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "luggage")
    private String luggage;
    @Column(name = "auctioned")
    private Boolean auctioned;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @JoinColumn(name = "tripid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TTrip tripid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TUser userid;

    public TSeat() {
    }

    public TSeat(Integer id) {
        this.id = id;
    }

    public TSeat(Integer id, String luggage) {
        this.id = id;
        this.luggage = luggage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public Boolean getAuctioned() {
        return auctioned;
    }

    public void setAuctioned(Boolean auctioned) {
        this.auctioned = auctioned;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TTrip getTripid() {
        return tripid;
    }

    public void setTripid(TTrip tripid) {
        this.tripid = tripid;
    }

    public TUser getUserid() {
        return userid;
    }

    public void setUserid(TUser userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TSeat)) {
            return false;
        }
        TSeat other = (TSeat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TSeat[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TPurchase> getTPurchaseCollection() {
        return tPurchaseCollection;
    }

    public void setTPurchaseCollection(Collection<TPurchase> tPurchaseCollection) {
        this.tPurchaseCollection = tPurchaseCollection;
    }
    
}
