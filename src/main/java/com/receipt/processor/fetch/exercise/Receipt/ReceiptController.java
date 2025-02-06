package com.receipt.processor.fetch.exercise.Receipt;


import com.receipt.processor.fetch.exercise.Points.ReceiptPointsDto;
import com.receipt.processor.fetch.exercise.ReceiptLogger;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "/receipt")
@RequiredArgsConstructor
@Transactional
@RestController
public class ReceiptController extends ReceiptLogger {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final ReceiptService receiptService;

    @PostMapping(value = "/process", produces = "application/json")
    public ResponseEntity<ReceiptResponseDto> processReceipt(@Valid @RequestBody Receipt receipt) {
        logger.info("Processing Receipt: {}", receipt.toString());
        Receipt processReceipt = receiptService.processReceipt(receipt);

        logger.info("Processed Receipt Successfully: {}", processReceipt.toString());
        return ResponseEntity.ok(processReceipt.toDto());
    }

    @GetMapping(value = "/{receiptId}/points", produces = "application/json")
    public ResponseEntity<ReceiptPointsDto> getReceiptPoints(@PathVariable String receiptId) {
        logger.info("Getting Points for Receipt: {}", receiptId);

        if (!receiptService.receiptExists(receiptId)) {
            return ResponseEntity.notFound().build();
        }
        ReceiptPointsDto points = receiptService.getReceiptPoints(receiptId);

        return ResponseEntity.ok(points);

    }


}
