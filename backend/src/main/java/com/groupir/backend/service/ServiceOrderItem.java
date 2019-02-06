package com.groupir.backend.service;

import com.groupir.backend.dto.OrderItemDTO;
import com.groupir.backend.model.OrderItem;
import com.groupir.backend.repository.OrderItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceOrderItem {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ServiceProduct serviceProduct;

    /**
     * Find the list of items of an order
     * @param orderId order's id
     * @return list order items
     */
    public List<OrderItemDTO> getProducts(Long orderId) {
        ModelMapper modelMapperOrderItem = new ModelMapper();
        modelMapperOrderItem.createTypeMap(OrderItem.class, OrderItemDTO.class)
                .addMappings(mapping -> {
                    mapping.map(OrderItem::getQuantity,OrderItemDTO::setQuantity);
                    mapping.map(OrderItem::getTrackingNumber,OrderItemDTO::setTrackingNumber);
                    mapping.map(OrderItem::getDispatchmentDate,OrderItemDTO::setDispatchmentDate);
                    mapping.map(source -> source.getKey().getOption().getImage(),OrderItemDTO::setImage);
                    mapping.map(source -> source.getKey().getOption().getOptionName(),OrderItemDTO::setOptionName);
                    mapping.map(source -> source.getKey().getOption().getOptionId(),OrderItemDTO::setOptionId);
                    mapping.map(source -> source.getKey().getOption().getProduct().getProductId(),OrderItemDTO::setProductId);
                    mapping.map(source -> source.getKey().getOption().getProduct().getName(),OrderItemDTO::setProductName);
                });
        List<OrderItem> orderItems = orderItemRepository.findAllByKey_Order_OrderId(orderId);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        orderItems.stream()
                .forEach(orderItem -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    modelMapperOrderItem.map(orderItem, orderItemDTO);
                    orderItemDTO.setPrice(serviceProduct.findPrice(orderItemDTO.getProductId(),orderItemDTO.getOptionId()));
                    orderItemDTOS.add(orderItemDTO);
                });
        return orderItemDTOS;
    }
}
