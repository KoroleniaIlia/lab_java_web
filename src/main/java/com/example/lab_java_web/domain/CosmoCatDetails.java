package com.example.lab_java_web.domain;

import com.example.lab_java_web.domain.order.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CosmoCatDetails {
    Long id;
    UUID cosmoCatId;
    String catCosmoName;
    String realName;
    String catEmail;
    List<OrderDetails> orders;
}