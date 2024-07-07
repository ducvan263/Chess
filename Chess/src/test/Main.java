package test;

import controller.ChessController;
import model.ChessModel;

public class Main {

	public static void main(String[] args) {
		
		ChessController controller = new ChessController();
		
		
		controller.launchGame();

		// Có thể hiện 2 màn hình cùng lúc nhưng chỉ có 1 controller có thể launch, nếu
		// khai báo cả 2 controller đều launch thì sẽ bị lỗi cả 2
		
//		ChessController controller2 = new ChessController();
//		controller2.launchGame();
	}
}
