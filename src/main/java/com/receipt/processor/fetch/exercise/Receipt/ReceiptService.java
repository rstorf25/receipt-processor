package com.receipt.processor.fetch.exercise.Receipt;

import com.receipt.processor.fetch.exercise.Points.Points;
import com.receipt.processor.fetch.exercise.Points.PointsService;
import com.receipt.processor.fetch.exercise.Points.ReceiptPointsDto;
import com.receipt.processor.fetch.exercise.ReceiptLogger;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ReceiptService extends ReceiptLogger {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private PointsService pointsService;


    @Transactional
    public Receipt processReceipt(Receipt receipt) {
        logger.info("Processing Receipt: {}", receipt.toString());

        if (receipt.getItems() != null) {
            receipt.getItems().forEach(item -> item.setReceipt(receipt));
        }

        logger.info("Saving Receipt: {}", receipt);
        return receiptRepository.save(receipt);

    }

    public boolean receiptExists(String receiptId) {
        return receiptRepository.existsById(receiptId);
    }

    public ReceiptPointsDto getReceiptPoints(String receiptId) {
        Points points = pointsService.getPointsByReceiptId(receiptId);

        return points == null ? generatePoints(receiptId) : points.toDto();
    }

    private ReceiptPointsDto generatePoints(String receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId);
        logger.info("Generating Points for Receipt");
        return pointsService.generatePoints(receipt).toDto();
    }

}
