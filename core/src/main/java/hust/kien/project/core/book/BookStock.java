package hust.kien.project.core.book;

import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.model.ticket.ActiveTicket_;
import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.model.ticket.ClosedTicket_;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

@Embeddable
public class BookStock {
    private int stock;
    
    private double reimburseCost;
    
    @OneToMany(mappedBy = ClosedTicket_.BOOK)
    @Fetch(FetchMode.SUBSELECT)
    private List<ClosedTicket> completedContracts;
    
    @OneToMany(mappedBy = ActiveTicket_.BOOK)
    @Fetch(FetchMode.SUBSELECT)
    private List<ActiveTicket> ongoingContracts;
    
    @Deprecated
    protected BookStock() {
        // For Hibernate
    }
    
    public BookStock(int stock, double reimburseCost) {
        this.stock = stock;
        this.reimburseCost = reimburseCost;
    }
    
    public void decreaseStock() {
        this.stock -= 1;
    }
    
    public void increaseStock() {
        this.stock += 1;
    }
    
    public int stock() {
        return this.stock;
    }
    
    public double reimburseCost() {
        return this.reimburseCost;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setReimburseCost(double reimburseCost) {
        this.reimburseCost = reimburseCost;
    }
    
    public List<ClosedTicket> completedContracts() {
        return Collections.unmodifiableList(completedContracts);
    }
    
    public List<ActiveTicket> ongoingContracts() {
        return Collections.unmodifiableList(ongoingContracts);
    }
}
