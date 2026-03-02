package com.junseo.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

    // 타입 함수이름(){
    //    return 타입에 해당하는 값~~
    // }

    // void 함수이름(){
    //      리턴없는 함수
    // }

	public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);

        Friend f1 = new Friend("park", 30);
        Friend f2 = new Friend("Kim", 22);

        // 타입 변수명 = 값;
//        final String lover = "신데렐라";
//        final int wifeCount = 1;
//        System.out.println(wifeCount);

        // Kotlin의 경우는 var x = 5; 식으로 씀 java도 var 쓸 수는 있으나 관용적으로 java에서는 잘 안씀
        // 재실행 커맨드 ctrl + F9

        //        if (3 > 1) {
        //            //실행할 코드
        //        }

        //        for (int i = 0; i < 3; i++){
        //
        //        }

//        Test test = new Test(); // class에 있던 변수랑 함수 복사해서 남겨줘! -> new 클래스()로 복사해서 씀
//        System.out.println(test.name);
//        test.hello();
	}

}
// class는 변수와 함수 보관함
// Java는 클래스 강요함
// 관련있는 변수, 함수들 한 곳에 정리 가능
// 중요한 변수, 함수 원본을 지킬 수 있음
//class Test {
//    String name = "kim";
//    void hello() {
//        System.out.println("안녕");
//    }
//}

class Friend {
    String name;
    int age;

    // Constructor
    Friend(String name, int age){
        this.name = name;
        this.age = age;
    }
}