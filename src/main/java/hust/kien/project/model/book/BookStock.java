package hust.kien.project.model.book;

import java.util.List;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import java.util.ArrayList;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@SuperBuilder
public class BookStock {

    @ToString.Include
    private int stock;

    @ToString.Include
    private double reimburseCost;

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private List<ClosedTicket> completedContracts = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private List<ActiveTicket> ongoingContracts = new ArrayList<>();


    public BookStock(int stock, double reimburseCost) {
        this.stock = stock;
        this.reimburseCost = reimburseCost;
    }
}
