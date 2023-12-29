package main.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ProductIO {	// ����� �Է¹ޱ�

	ProductDAO dao = new ProductDAO();
	ProductDTO dto = new ProductDTO();
	Cart cart = new Cart();
	Scanner sc = new Scanner(System.in);
	static String productName;
	
	// ��ǰ ���
	public void basket() { 
		
	    System.out.print("� ��ǰ�� �����Ͻðڽ��ϱ� ? ");
	    String productName = sc.next();
	    System.out.print("������ �Է����ּ��� : ");
	    int quantity = sc.nextInt();
	    System.out.println("��ٱ��Ͽ� �߰��Ǿ����ϴ�.");
	    dto.setProductName(productName);
	    dto.setQuantity(quantity);
	    
	    dao.addToCart(dto);
	}
	
	// �����Ϸ��� ���
	public void cartViewAndPurchase() {
		
		// �Է¹��� ��ǰ �̸��� ���� ��ǰ�� ������ ��������
	    List<ProductDTO> cartItems = dao.getCartItems();
	    
	 // ������ ǰ���� �ִ��� Ȯ��
        if (cartItems.isEmpty()) {
            System.out.println("��ٱ��ϰ� ��� �ֽ��ϴ�.");
        } else {
            // ��ٱ��� ���� ���
            System.out.println("��ٱ��� ����:");
            for (ProductDTO cartItem : cartItems) {
                System.out.println(cartItem.toString2());
            }

            // ���� ���� Ȯ��
            System.out.print("�����Ͻðڽ��ϱ�? (Y/N): ");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("Y")) {
                // ��ٱ��Ͽ� �ִ� ǰ����� ����
                dao.purchaseFromCart(cartItems);
            }

            // ��ٱ��� ����
            dao.clearCart();
        }
	    // ������ ��ǰ ������ ������ ���Ÿ�Ͽ� �߰�
//	    if (productInfo != null) {
//	        System.out.println("��ǰ ����: " + productInfo.toString());
//
//	    } else {
//	        System.out.println("��ǰ ������ ������ �� �����ϴ�.");
//	    }
		
	}
	
	public void viewAndPurchaseCart() {
        // ��ٱ��Ͽ� ��� ǰ����� ��ȸ
        List<ProductDTO> cartItems = dao.getCartItems();

        // ������ ǰ���� �ִ��� Ȯ��
        if (cartItems.isEmpty()) {
            System.out.println("��ٱ��ϰ� ��� �ֽ��ϴ�.");
        } else {
            // ��ٱ��� ���� ���
            System.out.println("��ٱ��� ����:");
            for (ProductDTO cartItem : cartItems) {
                System.out.println(cartItem.toString());
            }

            // ���� ���� Ȯ��
            System.out.print("�����Ͻðڽ��ϱ�? (Y/N): ");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("Y")) {
                // ��ٱ��Ͽ� �ִ� ǰ����� ����
                dao.purchaseFromCart(cartItems);
            }

            // ��ٱ��� ����
            dao.clearCart();
        }
    }
	// ����	-- ��ǰ �ٽ� ���? �ƴϸ� �� ���?
	public void update() {
		
	}
	
	// ����
	public void delete() {
		
		System.out.print("������ ��ǰ �ڵ� : ");
		String productId = sc.next();
		
		int result = dao.dataDelete(productId);
		
		if(result != 0) {
			System.out.println("���� ����!");
		} else {
			System.out.println("���� ����!");
		}
	}
	
	// ����� ��ȸ
	public void List() {
		List<ProductDTO> lists = dao.getAllProducts();
		Iterator<ProductDTO> it = lists.iterator();
		while(it.hasNext()) {
			ProductDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}

	// �˻�
	public void search() {
	}
	
	
}
