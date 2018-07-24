package sample.api;


import sample.entity.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface ShopService extends Remote {

    List<Category> getCategories() throws RemoteException, SQLException;

    List<Product> getProductsByCategory(Integer categoryId) throws RemoteException, SQLException;

    Boolean addProduct(Product product) throws RemoteException;

    Boolean editProduct(Product product) throws RemoteException;

    Boolean addStock(int productId, int number, int fromShopNo, int toShopNo) throws RemoteException;

    List<Stock> getStock(int productId) throws RemoteException, SQLException;

    Boolean createOrder(Order order) throws RemoteException, SQLException;

    Boolean addCustomer(Customer user) throws RemoteException, SQLException;

    List<Customer> getCustomers() throws RemoteException, SQLException;

    Boolean editCustomer(Customer customer) throws RemoteException, SQLException;

    Boolean delCustomer(int customerId) throws RemoteException, SQLException;

}
