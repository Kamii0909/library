package hust.kien.project.dao.rentdao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import hust.kien.project.dao.LibraryDao;
import hust.kien.project.model.rent.BookRentContract;
/**
 * Generally should be avoided using, as it forces loading corresponding books and clients
 */
public interface BookRentContractLibraryDao extends LibraryDao<BookRentContract, Long> {
    // -------------------------
    // FIND BY DATE
    // -------------------------
    /**
     * Find all contracts between specified 2 {@code LocalDate}
     * <li> For completed contracts, the requirements are {@code from &#8804; startDate &#8804; endDate &#8804; to}
     * <li> For ongoing contracts, {@code from &#8804; startDate}
     */
    public List<BookRentContract> findByDate(LocalDate from, LocalDate to);
    public Optional<BookRentContract> findAnyByDate(LocalDate from, LocalDate to);

    public List<BookRentContract> findByStatus(boolean isActive);
    public Optional<BookRentContract> findAnyByStatus(boolean isActive);
}
