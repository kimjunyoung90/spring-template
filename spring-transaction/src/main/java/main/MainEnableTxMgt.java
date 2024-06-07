package main;

import config.Config;
import store.service.PlaceOrderService;
import store.service.PurchaseOrderRequest;
import store.service.PurchaseOrderResult;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainEnableTxMgt {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

		PlaceOrderService orderService = ctx.getBean(PlaceOrderService.class);
		PurchaseOrderRequest orderRequest = new PurchaseOrderRequest();
		orderRequest.setItemId(1);
		orderRequest.setAddress("주소");

		PurchaseOrderResult orderResult = orderService.order(orderRequest);
		System.out.println(orderResult.getOrder().getId());

		ctx.close();
	}

}