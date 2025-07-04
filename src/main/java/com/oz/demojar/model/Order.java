package com.oz.demojar.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="orders")
public class Order {

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String order;

    public Order(String order) {
        this.order = order;
    }

    public Order(Long id, String order) {
        this.order = order;
        this.id = id;
    }

    @Override
    public String toString() { return "Order{ id=" + id + ", order='" + order + "' }"; }
}
