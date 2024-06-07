package store.service;

import store.dao.ItemDao;
import store.dao.PaymentInfoDao;
import store.dao.PurchaseOrderDao;
import store.domain.Item;
import store.domain.ItemNotFoundException;
import store.domain.PaymentInfo;
import store.domain.PurchaseOrder;

import org.springframework.transaction.annotation.Transactional;

public class PlaceOrderServiceAnnotImpl implements PlaceOrderService {

	private ItemDao itemDao;
	private PaymentInfoDao paymentInfoDao;
	private PurchaseOrderDao purchaseOrderDao;

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setPaymentInfoDao(PaymentInfoDao paymentInformationDao) {
		this.paymentInfoDao = paymentInformationDao;
	}

	public void setPurchaseOrderDao(PurchaseOrderDao purchaseOrderDao) {
		this.purchaseOrderDao = purchaseOrderDao;
	}

	//Transaction 애노테이션은 propagation 속성을 비롯하여 propagation, isolation 등의 속성을 이용해서 트랜잭션 속성을 정의할 수 있다.
	@Override
	@Transactional
	public PurchaseOrderResult order(PurchaseOrderRequest orderRequest) throws ItemNotFoundException {
		Item item = itemDao.findById(orderRequest.getItemId());
		if(item == null) throw new ItemNotFoundException(orderRequest.getItemId());

		PaymentInfo paymentInfo = new PaymentInfo(item.getPrice());
		paymentInfoDao.insert(paymentInfo);

		PurchaseOrder order = new PurchaseOrder(item.getId(), orderRequest.getAddress(), paymentInfo.getId());
		purchaseOrderDao.insert(order);


		return new PurchaseOrderResult(item, paymentInfo, order);
	}

}
