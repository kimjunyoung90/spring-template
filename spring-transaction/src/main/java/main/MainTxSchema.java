package main;

import org.springframework.context.support.GenericXmlApplicationContext;
import store.service.PlaceOrderService;
import store.service.PurchaseOrderRequest;
import store.service.PurchaseOrderResult;

public class MainTxSchema {

	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:/dataSource.xml", "classpath:/jdbcTxSchema.xml");

		PlaceOrderService orderService = ctx.getBean(PlaceOrderService.class);
		PurchaseOrderRequest orderRequest = new PurchaseOrderRequest();
		orderRequest.setItemId(1);
		orderRequest.setAddress("주소");

		PurchaseOrderResult orderResult = orderService.order(orderRequest);
		System.out.println(orderResult.getOrder().getId());

		ctx.close();
	}

}