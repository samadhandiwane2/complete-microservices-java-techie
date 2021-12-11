package com.jt.os.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jt.os.api.dto.Payment;
import com.jt.os.api.dto.TransactionRequest;
import com.jt.os.api.dto.TransactionResponse;
import com.jt.os.api.entity.Order;
import com.jt.os.api.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private RestTemplate template;

	public TransactionResponse saveOrder(TransactionRequest request) {

		String responseMsg = "";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());

		Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/makePayment", payment,
				Payment.class);
		responseMsg = paymentResponse.getPaymentStatus().equalsIgnoreCase("success")
				? "Payment Processing is successful and order is placed"
				: "Payment is failed and order is cancelled";
		orderRepo.save(order);
		return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(),
				responseMsg);
	}

}
