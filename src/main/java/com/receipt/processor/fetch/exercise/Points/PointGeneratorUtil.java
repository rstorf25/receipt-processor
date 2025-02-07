package com.receipt.processor.fetch.exercise.Points;


import com.receipt.processor.fetch.exercise.Item.Item;
import com.receipt.processor.fetch.exercise.Receipt.Receipt;
import com.receipt.processor.fetch.exercise.ReceiptLogger;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class PointGeneratorUtil extends ReceiptLogger {

    private int calculatedPoints;
    private Receipt receipt;

    public int process(Receipt receipt) {
        logger.info("Generating Points for ReceiptId: {}", receipt.getId());
        this.receipt = receipt;
        calculatedPoints = 0;

        ruleOne();
        ruleTwo();
        ruleThree();
        ruleFour();
        ruleFive();
        //6. SKIP - Not using large language model
        ruleSeven();
        ruleEight();

        logger.info("Generated Point: {}", calculatedPoints);
        return calculatedPoints;
    }

    private void ruleOne() {
        // 1 point for each alpha numeric char in retailer name
        logger.info("Rule 1");
        String retailer = receipt.getRetailer();
        String cleaned = retailer.replaceAll("[^a-zA-Z0-9]", "");

        calculatedPoints = calculatedPoints + cleaned.length();
    }

    private void ruleTwo() {
        logger.info("Rule 2");
        //  50 points if total is round dollar amount w/o cents
        String total = receipt.getTotal();
        if (total.split("\\.")[1].equals("00")) {
            calculatedPoints = calculatedPoints + 50;
        }
    }


    private void ruleThree() {
        logger.info("Rule 3");
        // 25 points if total is multiple of .25
        double totalD = Double.parseDouble(receipt.getTotal());
        calculatedPoints = totalD % .25 == 0 ? calculatedPoints + 25 : calculatedPoints;
    }


    private void ruleFour() {
        // 5 points for every two items on the receipt
        logger.info("Rule 4");
        List<Item> receiptItems = receipt.getItems();
        int numItems = receiptItems.size();
        if (numItems % 2 != 0) {
            numItems--;
        }
        calculatedPoints = calculatedPoints + (5 * (numItems / 2));
    }


    private void ruleFive() {
        logger.info("Rule 5");
        List<Item> receiptItems = receipt.getItems();
        //If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer.
        // The result is the number of points earned.
        receiptItems.forEach(item -> {
            int trimmedLen = item.getShortDescription().trim().length();
            if (trimmedLen % 3 == 0) {
                double priceD = Double.parseDouble(item.getPrice());
                calculatedPoints = (int) (calculatedPoints + Math.ceil(priceD * 0.2));
            }
        });
    }


    private void ruleSeven() {
        logger.info("Rule 7");
        // 6 points if day in the purchase date is odd "2022-01-01" YEAR-MM-DD
        String stringPurchaseDate = receipt.getPurchaseDate();
        String[] date = stringPurchaseDate.split("-");
        int day = Integer.parseInt(date[2]);
        if (day % 2 != 0) {
            calculatedPoints = calculatedPoints + 6;
        }
    }


    private void ruleEight() {
        logger.info("Rule 8");
        // if purchase time is after 2pm (14) and before 4pm (16)
        LocalTime twoPm = LocalTime.of(14, 0);
        LocalTime fourPm = LocalTime.of(16, 0);
        String[] convertedTime = receipt.getPurchaseTime().split(":");
        LocalTime purchaseTime = LocalTime.of(Integer.parseInt(convertedTime[0]), Integer.parseInt(convertedTime[1]));

        calculatedPoints = purchaseTime.isAfter(twoPm) && purchaseTime.isBefore(fourPm) ? calculatedPoints + 10 : calculatedPoints;
    }

}