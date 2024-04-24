package com.jsp.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

import com.jsp.Model.Product;
import com.jsp.Model.shop;
import com.jsp.controller.controller;



public class view {
	static Scanner myinput= new Scanner(System.in);
    static controller Controller = new controller();
    static shop Shop = new shop();

    static
	{
    	//Ask shop details for 1st run of appln 
    			//From 2nd run onwards check if shop exists, if yes then use existing.
    			shop shopExist= Controller.isShopExist();
    			if (shopExist.getId() != 0) {//shop exist
    				//Maintaining only one ref for further operation.
    				Shop=shopExist;
    				System.out.println("----- Welcome back to Shop -----");
    				System.out.println("Shop details :");
    				System.out.println("Id :" + Shop.getId());
    				System.out.println("Name :" + Shop.getshopname());
    				System.out.println("Address :" + Shop.getAddress());
    				System.out.println("Gst :" + Shop.getGst());
    				System.out.println("Contact :" + Shop.getContact());
    				System.out.println("Owner :" + Shop.getOwmenamer());
    			} 
    			
    			else {
    				  System.out.println("-------Welcome to the shop ---------");
    				     System.out.println("Enter id :");
    				     Shop.setId(myinput.nextInt());
    				      myinput.nextLine();
    				      System.out.println("Enter shop name :");
    				      Shop.setshopname(myinput.nextLine());
    				      System.out.println("Enter shop address:");
    				      Shop.setAddress(myinput.nextLine());
    				      System.out.println("Enter GST no :");
    				      Shop.setGst(myinput.nextLine());
    				      System.out.println("Enter contact number :");
    				      Shop.setContact(myinput.nextLong());
    				      myinput.nextLine();
    				      System.out.println("Enter the owner name :");
    				      Shop.setOwmername(myinput.nextLine());
    				      if (Controller.addShop(Shop) != 0)
    				        {
    							System.out.println("Shop added\n");
    						}
    				      
    			           }
	                   }

	public static void main(String[] args) {
		
		do {
			System.out.println("Select operation to perform :");
			System.out.println("1.Add product\n2.removeProduct\n3.Updateproduct\n4.Fetchproduct\n0.Exist");
		    System.out.print("Enter digit respective to desired option : ");
		
			byte userChoice = myinput.nextByte();
			myinput.nextLine();
			switch (userChoice)
			{
			case 1:// 1.Add product/s
				List<Product> products = new ArrayList<Product>();
		         boolean continueToAdd = true;
				do {
					Product product = new Product();
					System.out.print("Enter product id : ");
					product.setId(myinput.nextInt());
					myinput.nextLine();
					System.out.print("Enter product name : ");
					product.setname(myinput.nextLine());
					System.out.print("Enter product price : ");
					product.setPrice(myinput.nextDouble());
					myinput.nextLine();
					System.out.print("Enter product quantity : ");
					int quantity = myinput.nextInt();
					myinput.nextLine();
					product.setQuantity(quantity);
					
					if (quantity>0) 
					{
						// set availability true
						product.setAvailability(true);
					}
					
					else 
					{
						// set availability false
						product.setAvailability(false);
					}
					
					products.add(product);
					System.out.println("Continue to add product? y/n:");
					String continueto = myinput.nextLine();
					if (continueto.equalsIgnoreCase("n")) 
					{
						continueToAdd = false;
					}
				} while (continueToAdd);
				Controller.addProducts(Shop, products);
				break;
				
			case 2:
				ResultSet productsResultSet = Controller.fetchAllProducts();
				if (productsResultSet == null) 
				{
					System.out.println("No product ,No remove operation can be performed.");
				} 
			
				else 
				{
					System.out.println("\nAvailable products in shop :");
					System.out.println(" id   |   product name ");
					try {
						while(productsResultSet.next()) 
						{
							System.out.print(productsResultSet.getInt(1) + "    ");
							System.out.println(productsResultSet.getString(2));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					myinput.nextLine();
					System.out.print("Enter product id to remove :");
					int productIdtoremove = myinput.nextInt();
					myinput.nextLine();
					if (Controller.removeProduct(productIdtoremove)==2) {
						System.out.println(" Product Removed !");
					} else 
					{
						System.out.println(" Product Not Remove !");
					}

				}
				
					
				break;
				
				
			    case 3:
				 System.out.println("List of available products to update :");
				 ResultSet resultset = Controller.fetchAllProducts();
				 if(resultset == null)
				 {
					 System.out.println("No products available in shop ,no updation can performed ");
				 }
				 else
				 {
					 System.out.printf("| %-5s | %-15s  | %-15s  | %-12s | %-12s |%n" , "product_id " ,
							  "product_name" ,"product_price" ,"product_quantity" , "product_availability");
					 
				 }
				 try {
						while ( resultset .next()) {
							System.out.printf(" |%-3d  |",  resultset.getInt(1));
							System.out.printf("%-13s   |",  resultset.getString(2));
							System.out.printf("%-14f   |",  resultset.getDouble(3));
							System.out.printf("%-11d   |",  resultset.getInt(4));
							System.out.printf("%-11b   |",  resultset.getBoolean(5));
							System.out.println();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
						System.out.print("Enter product id on which you want to update : ");
						int productIdToUpdate = myinput.nextInt();
	                    myinput.nextLine();
						Product product = Controller.fetchparticularproduct(productIdToUpdate);
						if(product == null) 
						{
							System.out.println("Product not available :");
						}
						else {
						System.out.println("Product is avialable, please select what you want to update:");
					    System.out.println("1. Name");
					    System.out.println("2. Price");
					    System.out.println("3. Quantity");
					    System.out.println("4. Availability");
					    System.out.print("Enter your choice: ");
					    int choice = myinput.nextInt();
					    myinput.nextLine(); 

					    switch (choice) {
					        case 1:
					            System.out.print("Enter the new name to update: ");
					            String Name = myinput.nextLine();
					            product.setname(Name);
					            break;
					        case 2:
					            System.out.print("Enter the new price to update: ");
					            double Price = myinput.nextDouble();
					            product.setPrice(Price);
					            break;
					        case 3:
					            System.out.print("Enter the new quantity to update: ");
					            int Quantity = myinput.nextInt();
					            product.setQuantity(Quantity);
					            break;
					        case 4:
					            System.out.print("Enter the new availability to update: ");
					            boolean isAvailability = myinput.nextBoolean();
					            product.setAvailability(isAvailability);
					            break;
					        default:
					            System.out.println("Invalid choice!");
					            break;
					    }}
					
					
					    Controller.updateproduct(product);
					    System.out.println("Product updated successfully");
					
				
						
					
					break;

				
			        
			    case 4:
			    	ResultSet productResultSet3 = Controller.fetchAllProducts();
					if (productResultSet3 == null) {
						System.out.println("No product exists, No update operation can be performed.");

					} else {
						System.out.println("\nAvailable products in shop : ");
					System.out.println("id   | product name | price |  quantity  | availability ");
						System.out.printf("%-5s| %-13s| %-14s| %-11s| %-11s |%n", "id", "product name", "price", "quantity",
								"availability");
						System.out.println("----------------------------------------------------------------------------");
						try {
							while (productResultSet3.next()) {
								System.out.printf("%-3d  |", productResultSet3.getInt(1));
								System.out.printf("%-13s |", productResultSet3.getString(2));
								System.out.printf("%-14f |", productResultSet3.getDouble(3));
								System.out.printf("%-11d |", productResultSet3.getInt(4));
								System.out.printf("%-11b   |", productResultSet3.getBoolean(5));
								System.out.println();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
			    	break;
				case 0: //Exit
					System.exit(0);
					Controller.closeConnection();
					break;
			default:
				System.out.println("----- INVALID SELECTION -----");
				
				break;
			}
		} while (true);
}
}