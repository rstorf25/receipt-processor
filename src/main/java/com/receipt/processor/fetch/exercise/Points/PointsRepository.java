package com.receipt.processor.fetch.exercise.Points;

import org.springframework.data.repository.CrudRepository;

public interface PointsRepository extends CrudRepository<Points, Long> {

    Points findByReceiptId(String receiptId);
}
