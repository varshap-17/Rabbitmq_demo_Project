package com.example.rabbitmqdemo.publisher;

import com.example.rabbitmqdemo.config.MessageConfig;
import com.example.rabbitmqdemo.dto.Order;
import com.example.rabbitmqdemo.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order,@PathVariable String restaurantName){
        order.setOrderId(UUID.randomUUID().toString());
        //paymentService
        OrderStatus orderStatus=new OrderStatus(order,"PROCESS","order placed successfully "+restaurantName);
        rabbitTemplate.convertAndSend(MessageConfig.EXCHANGE,MessageConfig.ROUTING_KEY,orderStatus);
        return "Success!!";
    }
}
