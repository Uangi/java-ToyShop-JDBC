package main.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ScoreIO {	// 사용자 입력받기

	ScoreDAO dao = new ScoreDAO();
	Scanner sc = new Scanner(System.in);
	
	public void insert() { 

		
	}
	
	// 수정	-- 물품 다시 담기? 아니면 더 사기?
	public void update() {
		
		
	}
	
	// 삭제
	public void delete() {
		
		System.out.println("삭제할 학번이 뭐임 ? ");
		String hak = sc.next();
		
		int result = dao.dataDelete(hak);
		
		if(result != 0) {
			System.out.println("삭제 성공!");
		} else {
			System.out.println("삭제 실패!");
		}
	}
	
	// 조회
	public void inquery() {
		List<ScoreDTO> lists = dao.getList();
		Iterator<ScoreDTO> it = lists.iterator();
		while(it.hasNext()) {
			ScoreDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}

	// 검색
	public void search() {
	}
	
	// 학번으로 검색
		public void searchHak() {
		}
	
}
