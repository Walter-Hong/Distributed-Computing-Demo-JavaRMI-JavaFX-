/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import sample.api.ShopService;
import sample.config.AppConfig;
import sample.entity.*;
import sample.util.DbUtil;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ShopService {

    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws Exception {
        Server app = new Server();

        LocateRegistry.createRegistry(AppConfig.rmiPort);
        Naming.rebind(AppConfig.rmiName, app);

        System.out.print("RMI server is running ...");
    }


    @Override
    public List getCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sqlMethod = "select *from categories";
        ResultSet resultSet = DbUtil.query(sqlMethod);
        while (resultSet.next()) {
            Category product = new Category();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            categories.add(product);
        }
        return categories;
    }

    @Override
    public List getProductsByCategory(Integer categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sqlMethod = "select p.*,c.`name` AS category_name from product p LEFT JOIN categories c on p.category_id = c.id";
        if (categoryId != null) {
            sqlMethod += " WHERE c.id =" + categoryId;
        }
        ResultSet resultSet = DbUtil.query(sqlMethod);
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setCategoryId(resultSet.getInt("category_id"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setDescription(resultSet.getString("description"));
            product.setCategoryName(resultSet.getString("category_name"));
            product.setName(resultSet.getString("name"));
            product.setImage(resultSet.getString("image"));
            products.add(product);
        }
        return products;
    }


    @Override
    public Boolean addProduct(Product product) {
        return null;
    }

    @Override
    public Boolean editProduct(Product product) {
        return null;
    }

    @Override
    public Boolean addStock(int productId, int number, int fromShopNo, int toShopNo) {
        return null;
    }

    @Override
    public List<Stock> getStock(int productId) throws SQLException {
        List<Stock> stocks = new ArrayList<>();
        String sqlMethod = "select * from stock where product_id = " + productId;
        ResultSet resultSet = DbUtil.query(sqlMethod);
        while (resultSet != null && resultSet.next()) {
            Stock stock = new Stock();
            stock.setId(resultSet.getInt("id"));
            stock.setProductId(resultSet.getInt("product_id"));
            stock.setQuantity(resultSet.getInt("quantity"));
            stock.setShopId(resultSet.getInt("shop_id"));
            stocks.add(stock);
        }
        return stocks;
    }

    @Override
    public Boolean createOrder(Order order) throws SQLException {
        PreparedStatement st = DbUtil.getConnect().prepareStatement("INSERT INTO `order` (customer_id,product_id,quantity,create_time) VALUES  (?,?,?,?)");
        st.setObject(1, order.getCustomer().getId());
        st.setObject(2, order.getProduct().getId());
        st.setObject(3, order.getQuantity());
        st.setObject(4, LocalDateTime.now());
        st.executeUpdate();
        st.close();
        return true;
    }

    @Override
    public Boolean addCustomer(Customer customer) throws SQLException {

        PreparedStatement st = DbUtil.getConnect().prepareStatement("INSERT INTO customer (first_name,last_name,birthday,city,postal_code,street) VALUES  (?,?,?,?,?,?)");
        st.setObject(1, customer.getFirstName());
        st.setObject(2, customer.getLastName());
        st.setObject(3, customer.getBirthday());
        st.setObject(4, customer.getCity());
        st.setObject(5, customer.getPostalCode());
        st.setObject(6, customer.getStreet());
        st.executeUpdate();
        st.close();
        return true;
    }

    @Override
    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sqlMethod = "select * from customer";
        ResultSet resultSet = DbUtil.query(sqlMethod);
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setPhone(resultSet.getString("phone"));
            customer.setBirthday(resultSet.getDate("birthday").toLocalDate());
            customer.setCity(resultSet.getString("city"));
            customer.setPostalCode(resultSet.getInt("postal_code"));
            customer.setStreet(resultSet.getString("street"));
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Boolean editCustomer(Customer customer) throws RemoteException, SQLException {
        String sqlMethod = "DELETE FROM customer WHERE id = " + customer.getId();
        return null;
    }

    @Override
    public Boolean delCustomer(int customerId) throws RemoteException, SQLException {
        String sqlMethod = "DELETE FROM customer WHERE id = " + customerId;
        return DbUtil.execSql(sqlMethod);
    }

}
