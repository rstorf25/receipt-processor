package com.receipt.processor.fetch.exercise.Receipt;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.receipt.processor.fetch.exercise.Item.Item;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Retailer cannot be blank.")
    @NotNull(message = "Retailer is a required field.")
    private String retailer;

    @NotNull(message = "Purchase date is a required field.")
    @NotBlank(message = "Purchase Date cannot be blank.")
    private String purchaseDate;

    @NotNull(message = "Purchase time is a required field.")
    @NotBlank(message = "Purchase Time cannot be blank.")
    private String purchaseTime;


    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    @NotEmpty(message = "At least one item is required.")
    @Valid
    private List<Item> items = new ArrayList<>();

    @NotBlank(message = "Receipt total cannot be blank.")
    @NotNull(message = "Receipt total is required.")
    private String total;


    protected ReceiptResponseDto toDto() {
        return ReceiptResponseDto.builder().id(this.id).build();
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id='" + id + '\'' +
                ", retailer='" + retailer + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", items=" + items +
                ", total='" + total + '\'' +
                '}';
    }
}
