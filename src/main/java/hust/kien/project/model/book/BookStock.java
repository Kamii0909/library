package hust.kien.project.model.book;

import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ActiveTicket_;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.model.ticket.ClosedTicket_;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class BookStock {
    private int stock;
    
    private double reimburseCost;
    
    @OneToMany(mappedBy = ClosedTicket_.BOOK)
    private List<ClosedTicket> completedContracts;
    
    @OneToMany(mappedBy = ActiveTicket_.BOOK)
    private List<ActiveTicket> ongoingContracts;
    
    public BookStock(int stock, double reimburseCost) {
        this.stock = stock;
        this.reimburseCost = reimburseCost;
    }
    
    private static List<ClosedTicket> $default$completedContracts() {
        return new ArrayList<>();
    }
    
    private static List<ActiveTicket> $default$ongoingContracts() {
        return new ArrayList<>();
    }
    
    @SuppressWarnings("all")
    public abstract static class BookStockBuilder<C extends BookStock, B extends BookStockBuilder<C, B>> {
        private int stock;
        
        private double reimburseCost;
        
        private boolean completedContracts$set;
        
        private List<ClosedTicket> completedContracts$value;
        
        private boolean ongoingContracts$set;
        
        private List<ActiveTicket> ongoingContracts$value;
        
        protected abstract B self();
        
        public abstract C build();
        
        public B stock(final int stock) {
            this.stock = stock;
            return self();
        }
        
        public B reimburseCost(final double reimburseCost) {
            this.reimburseCost = reimburseCost;
            return self();
        }
        
        public B completedContracts(final List<ClosedTicket> completedContracts) {
            this.completedContracts$value = completedContracts;
            completedContracts$set = true;
            return self();
        }
        
        public B ongoingContracts(final List<ActiveTicket> ongoingContracts) {
            this.ongoingContracts$value = ongoingContracts;
            ongoingContracts$set = true;
            return self();
        }
        
        @Override
        public String toString() {
            return "BookStock.BookStockBuilder(stock=" + this.stock + ", reimburseCost="
                    + this.reimburseCost + ", completedContracts$value=" + this.completedContracts$value
                    + ", ongoingContracts$value=" + this.ongoingContracts$value + ")";
        }
    }
    
    private static final class BookStockBuilderImpl
        extends BookStockBuilder<BookStock, BookStockBuilderImpl> {
        private BookStockBuilderImpl() {
        }
        
        @Override
        protected BookStockBuilderImpl self() {
            return this;
        }
        
        @Override
        public BookStock build() {
            return new BookStock(this);
        }
    }
    
    protected BookStock(final BookStockBuilder<?, ?> b) {
        this.stock = b.stock;
        this.reimburseCost = b.reimburseCost;
        if (b.completedContracts$set)
            this.completedContracts = b.completedContracts$value;
        else
            this.completedContracts = BookStock.$default$completedContracts();
        if (b.ongoingContracts$set)
            this.ongoingContracts = b.ongoingContracts$value;
        else
            this.ongoingContracts = BookStock.$default$ongoingContracts();
    }
    
    @SuppressWarnings("all")
    public static BookStockBuilder<?, ?> builder() {
        return new BookStockBuilderImpl();
    }
    
    public int getStock() {
        return this.stock;
    }
    
    public double getReimburseCost() {
        return this.reimburseCost;
    }
    
    public List<ClosedTicket> getCompletedContracts() {
        return this.completedContracts;
    }
    
    public List<ActiveTicket> getOngoingContracts() {
        return this.ongoingContracts;
    }
    
    public void setStock(final int stock) {
        this.stock = stock;
    }
    
    public void setReimburseCost(final double reimburseCost) {
        this.reimburseCost = reimburseCost;
    }
    
    public void setCompletedContracts(final List<ClosedTicket> completedContracts) {
        this.completedContracts = completedContracts;
    }
    
    public void setOngoingContracts(final List<ActiveTicket> ongoingContracts) {
        this.ongoingContracts = ongoingContracts;
    }
    
    @Override
    public String toString() {
        return "BookStock(stock=" + this.getStock() + ", reimburseCost=" + this.getReimburseCost()
                + ")";
    }
    
    public BookStock() {
    }
}
