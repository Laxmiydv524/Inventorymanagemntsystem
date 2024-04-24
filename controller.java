package com.jsp.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Properties;

import org.postgresql.Driver;

import com.jsp.Model.Product;
import com.jsp.Model.shop;

public class controller {
	static String dbUrl = "jdbc:postgresql://localhost:5432/shop";
	static Connection connection=null;
	static {

		try {
			Driver driver= new Driver();
			DriverManager.registerDriver(driver);
			FileInputStream fis = new FileInputStream("dbconfig.properties");
			Properties p = new Properties();
			p.load(fis);
		    connection = DriverManager.getConnection(dbUrl, p);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ResultSet fetchAllProducts() {
		try {
			Statement statement = connection.createStatement();
			return checkproductResultSet(statement.executeQuery("SELECT * FROM product"));
			
//			byte count =0;
//			while(products.next()) {
//				if (++count >0) {
//					break;
//				}
//			}
//			if (count == 1) {
//				return statement.executeQuery("SELECT * FROM product");
//			} else
//		{
//				return null;
//			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet checkproductResultSet(ResultSet resultset) {
		Statement statement;
		try {
		statement = connection.createStatement();
		byte count =0;
		while(resultset.next())
		{
			if(++count>0)
				break;
		}
		if(count == 1)
		{
			return statement.executeQuery("SELECT * FROM product");
			
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public  int addShop(shop shop) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO shop_details VALUES(?,?,?,?,?,?)");
			preparedStatement.setInt(1, shop.getId());
			preparedStatement.setString(2, shop.getshopname());
			preparedStatement.setString(3, shop.getAddress());
			preparedStatement.setString(4, shop.getGst());
			preparedStatement.setLong(5, shop.getContact());
			preparedStatement.setString(6, shop.getshopname());
			return preparedStatement.executeUpdate();
		} catch (SQLException e)
		
		{
			e.printStackTrace();
		}
		return 0;
	}
	public void addProducts(shop shop, List<Product> products) {
		PreparedStatement preparedStatement = null;
		 PreparedStatement preparedStatement1 = null;
		try {
	 preparedStatement= connection.prepareStatement("INSERT INTO shop_product VALUES(?,?");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			if( preparedStatement!=null)
			{
				 try {
					 preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(Product product : products) {
			//insert product into product table
		
			try {
				preparedStatement1 = connection.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?)");
				 preparedStatement1.setInt(1, product.getId());
				 preparedStatement1.setString(2,product.getname());
				 preparedStatement1.setDouble(3, product.getPrice());
				 preparedStatement1.setInt(4, product.getQuantity());
				 preparedStatement1.setBoolean(5, product.isAvailability());
				 preparedStatement1.executeUpdate();
				 //insert shopid and productid into shop_product table
				 PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO shop_product VALUES(?,?)");
				 preparedStatement2.setInt(1, shop.getId());
				 preparedStatement2.setInt(2, product.getId());
				 preparedStatement2.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			finally
			{
				if( preparedStatement1!=null)
				{
					 try {
						preparedStatement1.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	public shop isShopExist()
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_details");
			shop isShopExist = new shop();
			while(resultSet.next()) {
				isShopExist.setId(resultSet.getInt(1));
				isShopExist.setshopname(resultSet.getString(2));
				isShopExist.setAddress(resultSet.getString(3));
				isShopExist.setGst(resultSet.getString(4));
				isShopExist.setContact(resultSet.getLong(5));
				isShopExist.setOwmername(resultSet.getString(6));
			}
			
			return isShopExist;
			
		      } 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public int removeProduct(int productId) {
		
	try {
		PreparedStatement removeFromProduct;
		removeFromProduct = connection.prepareStatement("DELETE FROM product WHERE product_id = ?");
		removeFromProduct.setInt(1, productId);
			PreparedStatement removeFromShopProduct = connection.prepareStatement("DELETE FROM shop_product WHERE p_id=?");	
			removeFromShopProduct.setInt(1, productId);
			return removeFromShopProduct.executeUpdate()
					+ removeFromProduct.executeUpdate();

	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	return 0;
	}
//		try {
//			
//			CallableStatement prepareCall = connection.prepareCall("call remove_product(?,?,?)");
//			prepareCall.registerOutParameter(1, Types.INTEGER);
//			prepareCall.setInt(2, productID);
//			prepareCall.registerOutParameter(3 , Types.INTEGER);
//			prepareCall.executeUpdate();
//			int countBefore = prepareCall.getInt(1);
//			int countAfter = prepareCall.getInt(3);
//			if (countBefore > countAfter) {
//				return countAfter;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return -1;
//
//	}
//	

	
	public Product fetchparticularproduct(int id)
	{        Product product = null;

	    try {
	        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM product WHERE product_id = ?");
	        prepareStatement.setInt(1, id);
	        ResultSet productResultSet = prepareStatement.executeQuery();
               
	        if (productResultSet.next()) {
	            product = new Product();
	            product.setId(productResultSet.getInt(1));
	            product.setProduct(productResultSet.getString(2));
	            product.setPrice(productResultSet.getDouble(3));
	            product.setQuantity(productResultSet.getInt(4));
	            product.setAvailability(productResultSet.getBoolean(5));
	        } 
	        else {
	            System.out.println("Id "+id+ " is not exist");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return product;
		
	}
   
	
	public int updateproduct(Product product)
	{
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("UPDATE product SET product_name = ?, product_price = ? , "
					+ "product_quantity = ? , product_availability = ?  WHERE product_id = ? ");
			prepareStatement.setString(1, product.getname()); 
			prepareStatement.setDouble(2, product.getPrice());
			prepareStatement.setInt(3, product.getQuantity());
			prepareStatement.setBoolean(4, product.isAvailability());
			prepareStatement.setInt(5, product.getId());
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public void closeConnection() {
		if (connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}	