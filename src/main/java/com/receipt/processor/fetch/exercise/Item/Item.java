package com.receipt.processor.fetch.exercise.Item;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.receipt.processor.fetch.exercise.Receipt.Receipt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @NotBlank(message = "Item description cannot be blank.")
    @NotNull(message = "Item description is required.")
    public String shortDescription;

    @NotBlank(message = "Item price cannot be blank.")
    @NotNull(message = "Item price is required.")
    public String price;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    @JsonBackReference
    private Receipt receipt;

}
