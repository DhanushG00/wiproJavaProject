package com.wipro.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


 //  Many items belong to one order
     @ManyToOne
     @JoinColumn(name = "order_id")
     private OrderEntity order;

  //  Many items refer to one menu item
      @ManyToOne
      @JoinColumn(name = "menu_id")
      private MenuEntity menu;

    private int quantity;
}