import java.util.*;

class Orders{
	private String orderId;
	private String phoneNumber;
	private String name;
	private int qty;
	private int status;
	
	public static final double UNITPRICE = 500.00;

	public static final int PREPARING = 0;
	public static final int DELIVERED = 1;
	public static final int CANCEL = 2;
	
	public Orders(String orderId, String phoneNumber, String name, int qty, int status){
		this.orderId = orderId;
		this.phoneNumber = phoneNumber;
		this.name = name;
		if (qty>=0){
			this.qty = qty;
		}
		this.status = status;
	}
	
	public Orders(){}
	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	
	public String getOrderId(){
		return orderId;
	}
	
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean setQty(int qty){
		if (qty>=0){
			this.qty = qty;
			return true;
		}
		
		return false;
	}
	
	public int getQty(){
		return qty;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
}

//=============================================================================================

   class Example{

    final static double BURGERPRICE = 500;

	public static Orders[] orders = new Orders[0];
	
    // Order status
    public static final int CANCEL = 0;
    public static final int PREPARING = 1;
    public static final int DELIVERED = 2;

    // console clear
    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // Handle any exceptions.
        }
    }
    
    //extend Array
    public static void extendArray(){
		Orders[] temp = new Orders[orders.length+1];
		
		for (int i = 0; i < orders.length; i++){
			temp[i] = orders[i];
		}
		
		temp[temp.length-1] = new Orders();
		
		orders = temp;
		
	}


    // validation Customer ID
    public static boolean validationcustomerId(String customerId) {
        if (customerId.length() == 10) {
            if (customerId.startsWith("0")) {
                try {
                    int i = Integer.parseInt(customerId);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    // generate order Id
    public static String generateOrderId() {
		
        if (orders.length==0){
			return "B0001";
		}
		String lastOrderId = orders[orders.length - 1].getOrderId();
		int number = Integer.parseInt(lastOrderId.split("B")[1]); //1
		number++;//2
		return String.format("B%04d",number); //printf("",) //B0002
    }

    // placeOrder
    public static void placeOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tPLACE ORDER\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n\n");
        System.out.print("ORDER ID - ");
        String orderId = generateOrderId();
        System.out.println(orderId + "\n================\n\n");

        L1: do {
            System.out.print("Enter Customer ID (phone no.): ");
            String customerId = input.next();
            if (customerId.charAt(0)!='0' || customerId.length()!=10){
				continue L1;
			}
            boolean isExistCustomer = false;
            String customerName = "";
            for (int i = 0; i < orders.length; i++) {
                if (customerId.equals(orders[i].getPhoneNumber())) {
                    isExistCustomer = true;
                    System.out.println("Enter Customer Name: " + orders[i].getName());
                    customerName = orders[i].getName();
                    break;
                }
            }
            if (!isExistCustomer) {
                System.out.print("\nEnter Customer Name: ");
                customerName = input.next();
            }
            System.out.print("Enter Burger Quantity - ");
            int qty = input.nextInt();
            if (qty > 0) {
                double billValue = qty * BURGERPRICE;
                System.out.printf("Total value - %.2f", billValue);
                System.out.println();
                L3: do {
                    System.out.print("\tAre you confirm order - ");
                    String option = input.next().toUpperCase();
                    if (option.equalsIgnoreCase("Y")) {
                        extendArray();

                        orders[orders.length - 1] = new Orders(orderId,customerId,customerName,qty,PREPARING);
                        
                        System.out.println("\n\tYour order is enter to the system successfully...");
                        break L1;
                    } else if (option.equalsIgnoreCase("N")) {
                        System.out.println("\n\tYour order is not enter the system...");
                        clearConsole();
                        return;
                    } else {
                        System.out.println("\tInvalid option..input again...");
                        break L1;
                    }
                } while (true);
            }
            
        } while (true);
        L4: do {
            System.out.println();
            System.out.print("Do you want to place another order (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                placeOrder();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                homePage();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L4;
            }
        } while (true);

    }

    // Search best customer
    public static void searchBestCustomer() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tBEST Customer\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        String[] sortCustomerIdArray = new String[0];
        String[] sortCustomerName = new String[0];
        double[] customerTotalBuyingArray = new double[0];

        for (int i = 0; i < orders.length; i++) {
            boolean isExist = false;
            for (int j = 0; j < sortCustomerIdArray.length; j++) {
                if (sortCustomerIdArray[j].equals(orders[i].getPhoneNumber())) {
                    if (orders[i].getStatus()!=CANCEL){
						customerTotalBuyingArray[j] += (orders[i].getQty()*BURGERPRICE);
					}
                    isExist = true;
                }
            }
            if (!isExist) {
                String[] tempSortCustomerArray = new String[sortCustomerIdArray.length + 1];
                String[] tempSortCustomerName = new String[sortCustomerName.length + 1];
                double[] tempCustomerTotalBuyingArray = new double[customerTotalBuyingArray.length + 1];
                for (int j = 0; j < sortCustomerIdArray.length; j++) {
                    tempSortCustomerArray[j] = sortCustomerIdArray[j];
                    tempSortCustomerName[j] = sortCustomerName[j];
                    tempCustomerTotalBuyingArray[j] = customerTotalBuyingArray[j];
                }
                sortCustomerIdArray = tempSortCustomerArray;
                sortCustomerName = tempSortCustomerName;
                customerTotalBuyingArray = tempCustomerTotalBuyingArray;

                sortCustomerIdArray[sortCustomerIdArray.length - 1] = orders[i].getPhoneNumber();
                sortCustomerName[sortCustomerName.length - 1] = orders[i].getName();
                customerTotalBuyingArray[customerTotalBuyingArray.length - 1] = (orders[i].getQty()*BURGERPRICE);
            }
        }
        // sort
        for (int i = 1; i < sortCustomerIdArray.length; i++) {
            for (int j = 0; j < i; j++) {
                if (customerTotalBuyingArray[j] < customerTotalBuyingArray[i]) {
                    String temp = sortCustomerIdArray[j];
                    sortCustomerIdArray[j] = sortCustomerIdArray[i];
                    sortCustomerIdArray[i] = temp;
                    temp = sortCustomerName[j];
                    sortCustomerName[j] = sortCustomerName[i];
                    sortCustomerName[i] = temp;
                    double tempd = customerTotalBuyingArray[j];
                    customerTotalBuyingArray[j] = customerTotalBuyingArray[i];
                    customerTotalBuyingArray[i] = tempd;
                }
            }
        }
        System.out.println("\n----------------------------------------");
        String line1 = String.format("%-14s%-15s%8s", " CustomerID", "Name", "Total");
        System.out.println(line1);
        System.out.println("----------------------------------------");
        for (int i = 0; i < sortCustomerIdArray.length; i++) {

            String line = String.format("%1s%-14s%-15s%8.2f", " ", sortCustomerIdArray[i], sortCustomerName[i], customerTotalBuyingArray[i]);
            System.out.println(line);
            System.out.println("----------------------------------------");

        }
        L: do {
            Scanner input = new Scanner(System.in);
            System.out.print("\n\tDo you want to go back to main menu? (Y/N)> ");
            String exitOption = input.nextLine();
            if (exitOption.equalsIgnoreCase("Y")) {
                clearConsole();
                homePage();
            } else if (exitOption.equalsIgnoreCase("N")) {
                clearConsole();
                searchBestCustomer();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L;
            }
        } while (true);

    }
    
//-----------------------------------------------------------------------------------------------------------
 
    public static void searchOrder(){
		Scanner input= new Scanner(System.in);
		clearConsole();
		L2:while(true){
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("|                                SEARCH ORDER DETAILS                                       |");
	    System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(" ");
		System.out.print("Enter order ID - ");
		String xyz= input.next();				
		int num=0;
		L3:for(int i=0;i<(orders.length);i++){
			 if(xyz.equals(orders[i].getOrderId())){
		          num=i;        
	           }
           }
           
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("OrderID\t\tCustomerID\tName\t\tQuantity\tOrderValue\tOrderStatus  ");
        System.out.println("---------------------------------------------------------------------------------------------");
        if(orders[num].getStatus()==0){
		System.out.println(orders[num].getOrderId()+"\t\t"+orders[num].getPhoneNumber()+"\t"+ orders[num].getName()+"\t\t"+orders[num].getQty()+"\t\t"+(orders[num].getQty())*BURGERPRICE+"\t\t"+"Cancelled");
	    }else if(orders[num].getStatus()==1){
		System.out.println(orders[num].getOrderId()+"\t\t"+orders[num].getPhoneNumber()+"\t"+ orders[num].getName()+"\t\t"+orders[num].getQty()+"\t\t"+(orders[num].getQty())*BURGERPRICE+"\t\t"+"Prepared");
	    }else if(orders[num].getStatus()==2){
		System.out.println(orders[num].getOrderId()+"\t\t"+orders[num].getPhoneNumber()+"\t"+ orders[num].getName()+"\t\t"+orders[num].getQty()+"\t\t"+(orders[num].getQty())*BURGERPRICE+"\t\t"+"Dilevered");
		}
        System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(" ");
		
		
		 System.out.print("Do you want to search another order details (Y/N) : ");
           char exit2= input.next().toLowerCase().charAt(0);
               if(exit2=='y'){
				   clearConsole();
				   continue L2;
                 }else{
				   clearConsole();
				   homePage();
			       break L2;
	           }   
            }
	     }

 //-----------------------------------------------------------------------------------------------------------

    public static void searchCustomer(){
		Scanner input= new Scanner(System.in);
		clearConsole();
		L2:while(true){
		
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("|                                SEARCH CUSTOMER DETAILS                                       |");
	    System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(" ");
		System.out.print("Enter customer ID - "); 
		 String cust=input.next();
		int num=0;
		L3:for(int i=0;i<orders.length;i++){
			 if(cust.equals(orders[i].getPhoneNumber())){
		          num=i;
		          break L3;
			  }
	       }
	    System.out.println("");
	    System.out.println("CustomerID - "+orders[num].getPhoneNumber());
        System.out.println("Name       - "+orders[num].getName());
        System.out.println(" ");
        System.out.println("Customer Order Details ");
        System.out.println("=======================");
        System.out.println("");
        System.out.println("-----------------------------------------------");
        System.out.println("Order_ID\tOrder_Quantity\tTotal_Value");
        System.out.println("-----------------------------------------------");
        
       
                  System.out.println(orders[num].getOrderId()+"\t\t"+orders[num].getQty()+"\t\t"+(orders[num].getQty())*BURGERPRICE);
                  System.out.println("-----------------------------------------------");
                
	       
	       
	       System.out.print("Do you want to search another customer details (Y/N) : ");
           char exit2= input.next().toLowerCase().charAt(0);
               if(exit2=='y'){
				   clearConsole();
				   continue L2;
                 }else{
				   clearConsole();
				    homePage();
			       break L2;
	             }
             } 
          }
		
		
  //==================================================================================================================
  
  public static void viewOrders(){
		Scanner input= new Scanner(System.in);
		clearConsole();
		L2:while(true){
		
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("|                                VIEW ORDER LIST                                       |");
	    System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(" ");
		System.out.println("[1] Delivered Order");
		System.out.println("[2] Preparing Order ");
		System.out.println("[3] Cancel Order ");
		System.out.println(" ");
		System.out.print("Enter an option to continue ");
		int main= input.nextInt();
		switch(main){
			case 1 : 
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|                                Delivered Order                                            |");
	    System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|OrderID\tCustomerID\tName\t\tQuantity\tOrderValue   ");
	    System.out.println("---------------------------------------------------------------------------------------------");
	    for(int i=0; i<orders.length; i++){
			if(orders[i].getStatus()==2){
				System.out.println(orders[i].getOrderId()+"\t\t"+orders[i].getPhoneNumber()+"\t"+orders[i].getName()+"\t\t"+orders[i].getQty()+"\t\t"+(orders[i].getQty())*BURGERPRICE);
			    System.out.println("---------------------------------------------------------------------------------------------");
			 }
		 }
			
		System.out.print("Do you want to go to home page (Y/N) :");
        char exit2= input.next().toLowerCase().charAt(0);
               if(exit2=='y'){
				   clearConsole();
				    homePage();
				   break L2;
		 }else{
			 clearConsole();
			 continue L2;
		 }
		 
   //------------------------------------------------------------------------------------------------------------------------
   		 
		    case 2 :
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|                                PREPARING ORDER                                            |");
	    System.out.println("---------------------------------------------------------------------------------------------");
	    System.out.println(" ");
	    System.out.println(" ");
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|OrderID\tCustomerID\tName\t\tQuantity\tOrderValue   ");
	    System.out.println("---------------------------------------------------------------------------------------------");
	    for(int i=0; i<orders.length; i++){
			if(orders[i].getStatus()==1){
				System.out.println(orders[i].getOrderId()+"\t\t"+orders[i].getPhoneNumber()+"\t"+orders[i].getName()+"\t\t"+orders[i].getQty()+"\t\t"+(orders[i].getQty())*BURGERPRICE);
			    System.out.println("---------------------------------------------------------------------------------------------");
			 }
		 }
		 
	    System.out.println(" ");	  
	    System.out.print("Do you want to go to home page (Y/N) :");    
        char exit3= input.next().toLowerCase().charAt(0);
               if(exit3=='y'){
				   clearConsole();
				    homePage();
				   break L2;
		 }else{
			 clearConsole();
			 continue L2;
		 }
		 
	 //------------------------------------------------------------------------------------------------------------------------	 
		   case 3 :
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|                                CANCEL ORDER                                            |");
	    System.out.println("---------------------------------------------------------------------------------------------");
	    System.out.println(" ");
	    System.out.println(" ");
		System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|OrderID\tCustomerID\tName\t\tQuantity\tOrderValue   ");
	    System.out.println("---------------------------------------------------------------------------------------------");
	   for(int i=0; i<orders.length; i++){
			if(orders[i].getStatus()==0){
				System.out.println(orders[i].getOrderId()+"\t\t"+orders[i].getPhoneNumber()+"\t"+orders[i].getName()+"\t\t"+orders[i].getQty()+"\t\t"+(orders[i].getQty())*BURGERPRICE);
			    System.out.println("---------------------------------------------------------------------------------------------");
			 }
		 }
	   
	    System.out.println(" ");    
	    System.out.print("Do you want to go to home page (Y/N) :");    
        char exit4= input.next().toLowerCase().charAt(0);
               if(exit4=='y'){
				   clearConsole();
				    homePage();
				   break L2;
		 }else{
			 clearConsole();
			 continue L2;
		      }
	
	       }
        }
     }

//===============================================================================================================
   
   
   
   public static void updateOrderDetails(){
		Scanner input= new Scanner(System.in);
		clearConsole();
		L2:while(true){
		
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("|                                UPDATE ORDER DETAILS                                       |");
	    System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(" ");
		System.out.print("Enter order ID - ");
		String order= input.next();
		int num=0;
		for(int i=0;i<orders.length;i++){
		  if(order.equals(orders[i].getOrderId())){
		      num=i;
	          }
		  }
	          if(orders[num].getStatus()==0){	       
			    System.out.println("This order is already cancelled...You can not upate this order");
			    System.out.print("Do you want to go to home page (Y/N)");    
                   char exit= input.next().toLowerCase().charAt(0);
                    if(exit=='y'){
						clearConsole();
						homePage();
						break L2;
					}else{
						clearConsole();
						continue L2;
					}
		      }else if(orders[num].getStatus()==2){  		  
			    System.out.println("This order is already delivered...You can not upate this order");
			    System.out.print("Do you want to go to home page (Y/N)");    
                   char exit= input.next().toLowerCase().charAt(0);
                    if(exit=='y'){
						clearConsole();
						homePage();
						break L2;
					}else{
						clearConsole();
						continue L2;
					}
		      }else if(orders[num].getStatus()==1){
		   
			System.out.println(" ");
			System.out.println("OrderID      -"+orders[num].getOrderId());
			System.out.println("CustomerID   -"+orders[num].getPhoneNumber());	
	        System.out.println("Name         -"+orders[num].getName());
	        System.out.println("Quantity     -"+orders[num].getQty());
	        System.out.println("OrderValue   -"+(orders[num].getQty())*BURGERPRICE);	         
	        System.out.println("OrderStatus  - Preparing");
	        
	        			
            System.out.println("What do you want to update ? ");
           	System.out.println("[1] Quantity");
			System.out.println("[2] Status");
			System.out.println(" ");
			System.out.print("Enter your option -");
             int option=input.nextInt();
            switch(option){
			case 1 :
			System.out.println("Quantity Update ");
			System.out.println("================= ");
			System.out.println(" ");
			System.out.println("OrderID     - "+orders[num].getOrderId());
			System.out.println("CustomerID  - "+orders[num].getPhoneNumber());
			System.out.println("Name        - "+orders[num].getName());
			System.out.println(" ");
			System.out.print("Enter your quantity update value -");
				int x=input.nextInt();
				orders[num].setQty(x);
			System.out.println(" ");				
			System.out.println("           update order quantity successfully...... ");
			System.out.println(" ");
			System.out.println("new order quantity -"+x);
			System.out.println("new order value    -"+(x*BURGERPRICE));
			      
			System.out.println(" ");
			System.out.print("Do you want to update another oredr details (Y/N) :");
				char exit= input.next().toLowerCase().charAt(0);
                    if(exit=='y'){
						clearConsole();
						continue L2;
					}else{
						clearConsole();
						homePage();
						break L2;
					}
					
			case 2:
			System.out.println("Status Update ");
			System.out.println("============== ");
			System.out.println(" ");
			System.out.println("OrderID     - "+orders[num].getOrderId());
			System.out.println("CustomerID  - "+orders[num].getPhoneNumber());
			System.out.println("Name        - "+orders[num].getName());
			System.out.println("  ");
			System.out.println("            (0)Cancel");
			System.out.println("            (1)Preparing");
			System.out.println("            (2)Delivered");
			System.out.println("  ");
			System.out.print("Enter new order status -  ");
                int y= input.nextInt();
               if(y==0){
				   orders[num].setStatus(0);
			   }else if(y==1){
				   orders[num].setStatus(1); 
               }else if(y==2){
				   orders[num].setStatus(2); 
                }
			System.out.println("  ");                
			System.out.println("Update order status successfully..... ");
			System.out.println("  ");
			System.out.print("new order status -");
                if(y==0){
            System.out.println("Cancel");					
			   }else if(y==1){
            System.out.println("Preparing");					
			   }else if(y==2){
            System.out.println("Delivered");					
			   }
			   
		    System.out.println("Do you want to update another oredr details (Y/N) :");
			char exit1= input.next().toLowerCase().charAt(0);
                    if(exit1=='y'){
						clearConsole();
						continue L2;
					}else{
						clearConsole();
						homePage();
						break L2;
					}				   
			     }	        
		       } 
             }
           }

//===========================================================================================================

  // exit
    public static void exit() {
        clearConsole();
        System.out.println("\n\t\tYou left the program...\n");
        System.exit(0);
    }


  public static void homePage() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tiHungry Burger\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("[1] Place Order\t\t\t[2] Search Best Customer");
        System.out.println("[3] Search Order\t\t[4] Search Customer");
        System.out.println("[5] View Orders\t\t\t[6] Update Order Details");
        System.out.println("[7] Exit");

        Scanner input = new Scanner(System.in);
        do {

            System.out.print("\nEnter an option to continue > ");
            char option = input.next().charAt(0);

            switch (option) {
                case '1':
                    clearConsole();
                    placeOrder();
                    break;
                case '2':
                    clearConsole();
                    searchBestCustomer();
                    break;
                case '3':
                    clearConsole();
                    searchOrder();
                    break;
                case '4':
                    clearConsole();
                    searchCustomer();
                    break;
                case '5':
                    clearConsole();
                    viewOrders();
                    break;
                case '6':
                    clearConsole();
                    updateOrderDetails();
                    break;
                case '7':
                    exit();
                    break;
            }
        } while (true);
    }

    public static void main(String args[]) {
        homePage();
    }

}
