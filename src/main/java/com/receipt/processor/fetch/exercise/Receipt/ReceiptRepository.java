package com.receipt.processor.fetch.exercise.Receipt;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {

    boolean existsById(String receiptId);

    Receipt findById(String receiptId);
}
