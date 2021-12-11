package com.jt.ps.api.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.ps.api.entity.Payment;
import com.jt.ps.api.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepo;

	public Payment makePayment(Payment payment) {
		payment.setTransactionId(UUID.randomUUID().toString());
		payment.setPaymentStatus(paymentProcessing());
		return paymentRepo.save(payment);
	}

	public String paymentProcessing() {
		return new Random().nextBoolean() ? "Success" : "Failure";
	}

}
