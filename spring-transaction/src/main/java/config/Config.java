package config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import store.dao.ItemDao;
import store.dao.PaymentInfoDao;
import store.dao.PurchaseOrderDao;
import store.dao.jdbc.JdbcItemDao;
import store.dao.jdbc.JdbcPaymentInfoDao;
import store.dao.jdbc.JdbcPurchaseOrderDao;
import store.service.PlaceOrderService;
import store.service.PlaceOrderServiceAnnotImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class Config implements TransactionManagementConfigurer {

	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		ds.setJdbcUrl("jdbc:mysql://localhost/shop?characterEncoding=utf8");
		ds.setUser("rlawnsdud05");
		ds.setPassword("Rlawnsdud1@");
		return ds;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager txMgr = new DataSourceTransactionManager();
		txMgr.setDataSource(dataSource());
		return txMgr;
	}

	@Bean
	public PlaceOrderService placeOrderService() {
		PlaceOrderServiceAnnotImpl svc = new PlaceOrderServiceAnnotImpl();
		svc.setItemDao(itemDao());
		svc.setPaymentInfoDao(paymentInformationDao());
		svc.setPurchaseOrderDao(purchaseOrderDao());
		return svc;
	}

	@Bean
	public ItemDao itemDao() {
		return new JdbcItemDao(dataSource());
	}

	@Bean
	public PaymentInfoDao paymentInformationDao() {
		return new JdbcPaymentInfoDao(dataSource());
	}

	@Bean
	public PurchaseOrderDao purchaseOrderDao() {
		return new JdbcPurchaseOrderDao(dataSource());
	}

}