package nl.avisi.ordermanagement.service.invoice;

import nl.avisi.ordermanagement.domain.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceQueryRepository invoiceQueryRepository;

    /**
     * This function finds all the invoices
     *
     * @return Returns the found invoices
     **/
    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    /**
     * This function saves an invoice
     *
     * @param invoice The invoice that has to be saved
     * @return Returns the saved invoice
     */
    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    /**
     * This function finds a specific invoice
     *
     * @param id The id of the invoice that has to be found
     * @return Returns the found invoice
     */
    @Override
    public Invoice findOne(String id) {
        return invoiceRepository.findOne(id);
    }

    /**
     * This function deletes an invoice
     *
     * @param id The ID of the invoice that has to be deleted
     */
    @Override
    public void delete(String id) {
        invoiceRepository.delete(id);
    }

    /**
     * This function searches for an invoice/invoices by invoice number
     *
     * @param invoiceNumber The invoice number of the invoice/invoices that have to be found
     * @return Returns the found invoice/invoices
     */
    public List<Invoice> findByInvoiceNumber(String invoiceNumber) {
        return invoiceQueryRepository.findByInvoiceNumber(invoiceNumber);
    }
}
