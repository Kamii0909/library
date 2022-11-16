package hust.kien.project.model.book;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import hust.kien.project.model.rent.BookRentContract;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Embeddable
public class BookStock {

    private int stock;

    private double reimburseCost;


    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @Where(clause = "endDate <= date('now', 'localtime')")
    @Fetch(FetchMode.JOIN)
    private Set<BookRentContract> completedContracts = new HashSet<>();


    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @Where(clause = "endDate > date('now', 'localtime')")
    @Fetch(FetchMode.JOIN)
    private Set<BookRentContract> ongoingContracts = new HashSet<>();


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


    public Set<BookRentContract> getCompletedContracts() {
        return completedContracts;
    }

    public void setCompletedContracts(Set<BookRentContract> completedContracts) {
        this.completedContracts = completedContracts;
    }


    public Set<BookRentContract> getOngoingContracts() {
        return ongoingContracts;
    }

    public void setOngoingContracts(Set<BookRentContract> ongoingContracts) {
        this.ongoingContracts = ongoingContracts;
    }


}
