package hust.kien.project.model.book;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import hust.kien.project.model.rent.Ticket;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

@Embeddable
public class BookStock {

    private int stock;

    private double reimburseCost;


    @OneToMany(mappedBy = "book")
    @Where(clause = "endDate <= date('now', 'localtime')")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Ticket> completedContracts = new HashSet<>();


    @OneToMany(mappedBy = "book")
    @Where(clause = "endDate > date('now', 'localtime')")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Ticket> ongoingContracts = new HashSet<>();


    public BookStock(int stock, double reimburseCost) {
        this.stock = stock;
        this.reimburseCost = reimburseCost;
    }

    public BookStock() {}

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public double getReimburseCost() {
        return reimburseCost;
    }

    public void setReimburseCost(double reimburseCost) {
        this.reimburseCost = reimburseCost;
    }


    public Set<Ticket> getCompletedContracts() {
        return completedContracts;
    }

    public void setCompletedContracts(Set<Ticket> completedContracts) {
        this.completedContracts = completedContracts;
    }


    public Set<Ticket> getOngoingContracts() {
        return ongoingContracts;
    }

    public void setOngoingContracts(Set<Ticket> ongoingContracts) {
        this.ongoingContracts = ongoingContracts;
    }


}
