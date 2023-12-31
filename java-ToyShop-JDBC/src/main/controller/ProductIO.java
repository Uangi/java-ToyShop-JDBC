package main.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ProductIO {
    ProductDAO dao = new ProductDAO();
    Scanner sc = new Scanner(System.in);

    // 제품 담기
    public void basket() {
        System.out.print("어떤 제품을 구매하시겠습니까 ? ");
        String productName = sc.next();
        System.out.print("수량을 입력해주세요 : ");
        int quantity = sc.nextInt();

        // ProductDTO를 생성하여 필요한 정보 설정
        ProductDTO dto = new ProductDTO();
        dto.setProductId(productName);
        dto.setQuantity(quantity);

        // 장바구니에 제품 추가
        dao.addToCart(dto);
        System.out.println("장바구니에 추가되었습니다.");
    }

    // 구매하려는 목록
    public void cartViewAndPurchase() throws SQLException {
        // 장바구니에 담긴 품목들을 조회
        List<ProductDTO> cartItems = dao.getCartItems();

        // 구매할 품목이 있는지 확인
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
        } else {
            // 장바구니 내용 출력
            System.out.println("장바구니 내용:");
            for (ProductDTO cartItem : cartItems) {
                System.out.println(cartItem.toString());
            }

            // 구매 여부 확인
            System.out.print("구매하시겠습니까? (Y/N): ");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("Y")) {
                dao.purchaseFromCart(cartItems);
            }

            // 장바구니 비우기
            dao.clearCart();
        }
    }

    // 삭제
    public void delete1() {
        System.out.print("삭제할 제품 코드 : ");
        String productId = sc.next();

        // 삭제 메서드 호출
        int result = dao.dataDelete(productId);

        if (result != 0) {
            System.out.println("삭제 성공!");
        } else {
            System.out.println("삭제 실패!");
        }
    }

	// 수정	-- 물품 다시 담기? 아니면 더 사기?
	public void update() {
		
	}
	
	// 삭제
	public void delete() {
		
		System.out.print("삭제할 제품 코드 : ");
		String productId = sc.next();
		
		int result = dao.dataDelete(productId);
		
		if(result != 0) {
			System.out.println("삭제 성공!");
		} else {
			System.out.println("삭제 실패!");
		}
	}
	
	// 재고목록 조회
	public void List() {
		List<ProductDTO> lists = dao.getAllProducts();
		Iterator<ProductDTO> it = lists.iterator();
		while(it.hasNext()) {
			ProductDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}

	// 검색
	public void search() {
	}
	
	
}