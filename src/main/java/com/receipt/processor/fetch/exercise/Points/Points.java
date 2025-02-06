package com.receipt.processor.fetch.exercise.Points;


import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Points {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String receiptId;

    private int points;

    public ReceiptPointsDto toDto() {
        return ReceiptPointsDto.builder().points(this.points).build();
    }

    @Override
    public String toString() {
        return "Points{" +
                "id=" + id +
                ", receiptId='" + receiptId + '\'' +
                ", points=" + points +
                '}';
    }

}
