package main.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ScoreIO {	// ����� �Է¹ޱ�

	ScoreDAO dao = new ScoreDAO();
	Scanner sc = new Scanner(System.in);
	
	public void insert() { 

		
	}
	
	// ����	-- ��ǰ �ٽ� ���? �ƴϸ� �� ���?
	public void update() {
		
		
	}
	
	// ����
	public void delete() {
		
		System.out.println("������ �й��� ���� ? ");
		String hak = sc.next();
		
		int result = dao.dataDelete(hak);
		
		if(result != 0) {
			System.out.println("���� ����!");
		} else {
			System.out.println("���� ����!");
		}
	}
	
	// ��ȸ
	public void inquery() {
		List<ScoreDTO> lists = dao.getList();
		Iterator<ScoreDTO> it = lists.iterator();
		while(it.hasNext()) {
			ScoreDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}

	// �˻�
	public void search() {
	}
	
	// �й����� �˻�
		public void searchHak() {
		}
	
}
