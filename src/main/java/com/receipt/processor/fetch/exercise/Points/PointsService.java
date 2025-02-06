package com.receipt.processor.fetch.exercise.Points;

import com.receipt.processor.fetch.exercise.Receipt.Receipt;
import com.receipt.processor.fetch.exercise.ReceiptLogger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PointsService extends ReceiptLogger {
    private final PointsRepository pointsRepository;
    private final PointGeneratorUtil pointGeneratorUtil;

    public Points getPointsByReceiptId(String receiptId){
       return pointsRepository.findByReceiptId(receiptId);
    }

    public Points generatePoints(Receipt processedReceipt) {
        int points = pointGeneratorUtil.process(processedReceipt);
        logger.info("processed receipt: {}", processedReceipt);

        Points  receiptPoints = Points.builder()
                        .points(points)
                        .receiptId(processedReceipt.getId())
                        .build();
        logger.info("receipt points: {}", receiptPoints.toString());
        pointsRepository.save(receiptPoints);
        return receiptPoints;
    }
}
