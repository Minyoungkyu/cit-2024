import dedent from 'dedent';

// export const codeArray = [
//     {
//         'id' : 0,
//         'stage' : '스테이지1',
//         'code' : 'go()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         플레이어 캐릭터가 바라보는 방향으로 한 칸 이동합니다.

//         두 칸 이상을 이동할 때 인자를 사용합니다.
//         `,
//         'example' : dedent
//         `
//         한칸 이동
//         go()     

//         3칸 이동
//         go(3)    
//         `
        
//     },
//     {
//         'id' : 1,
//         'stage' : '스테이지1',
//         'code' : 'turnLeft()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         플레이어 캐릭터의 방향을 왼쪽으로 90도 회전합니다. 
//         `,
//         'example' : dedent
//         `
//         왼쪽으로 회전
//         turnLeft()
        
//         180도 회전 (뒤돌기) 
//         turnLeft(2)   
//         `
        
//     },
//     {
//         'id' : 2,
//         'stage' : '스테이지1',
//         'code' : 'turnRight()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         플레이어 캐릭터의 방향을 오른쪽으로 90도 회전합니다.
//         `,
//         'example' : dedent
//         `
//         오른쪽으로 회전
//         turnRight()
        
//         180도 회전 (뒤돌기)
//         turnRight(2)
//         `
        
//     },
//     {
//         'id' : 3,
//         'stage' : '스테이지1',
//         'code' : 'range()',
//         'type' : '함수/메서드',
//         'description' : dedent 
//         `
//         일정 범위의 정수 집합을 생성하는 함수로, for와 함께 많이 사용됩니다. 
//         range는 아래와 같이 구성되어있습니다. 
//         range(시작값, 끝값, 증가값) 

//         -시작값 : 범위의 시작 숫자입니다. 생략 시, 0으로 설정됩니다. 
//         -끝값 : 범위의 마지막 숫자입니다. 단, 이 숫자는 범위에 포함되지 않습니다.  
//         -증가값 : 증가값만큼 간격을 설정합니다. 생략 시, 1로 설정됩니다. 음수를 입력하면 역순으로 순회합니다.   

//         `,
//         'example' : dedent
//         `
//         1~5까지 범위 중, 1 간격으로 정수 생성 : 1, 2, 3, 4, 5
//         range(1, 6) : 

//         1~5까지 범위 중, 2 간격으로 정수 생성 : 1, 3, 5 
//         range(1, 6, 2) : 

//         5~1까지 범위 중, 1 간격으로 역순 정수 생성 : 5, 4, 3, 2, 1 
//         range(5, 0, -1 ) :
//         `
        
//     },
//     {
//         'id' : 4,
//         'stage' : '스테이지1',
//         'code' : 'for',
//         'type' : '반복문',
//         'description' : dedent 
//         `
//         동일한 코드블록을 일정 횟수 반복합니다. 
//         range() 함수나 리스트 등과 함께 사용됩니다. 

//         for i in range(3) : 
//         for문은 반복 작업을 수행하는데 사용됩니다. range(3)은 0부터 2까지 정수범위를 생성합니다.
//         따라서 for i in range(3): 은 0, 1, 2 숫자를 각각 i에 할당하고 코드를 수행하므로 3번 반복하게 됩니다.
//         첫 번째 반복에서는 'i'에 0이 할당되고, 두 번째 반복에서는 1이 할당되며, 세 번째 반복에서는 2가 할당됩니다.

//         numList = [10, 20, 30] 
//         for i in numList : 
//         numList는 10, 20, 30을 요소로 가지고 있는 길이 3의 리스트입니다. 
//         for i in numList: 구문은 numList의 각 요소를 하나씩 i에 할당하여 반복 작업을 수행합니다.
//         첫 번째 반복에서는 'i'에 10이 할당되고, 두 번째 반복에서는 20가 할당되며, 세 번째 반복에서는 30이 할당됩니다. 

//         `,
//         'example' : dedent
//         `
//         플레이어가 한 칸 전진 후 왼쪽을 바라보는 동작을 3회 실행하기 
//         for i in range(3):
//         go()
//         tunrLeft()

//         리스트의 각 요소를 순서대로 출력하기 
//         for i in numList:
//             print(i)
//         `
        
//     },
//     {
//         'id' : 5,
//         'stage' : '스테이지1',
//         'code' : 'setItem()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         로켓 발사체에 엔진, 연료, 추진제 등 아이템을 장착하기 위해 사용되는 명령어입니다. 
//         괄호 안에 큰따옴표("") 또는 작은따옴표('')를 사용하여 아이템명을 작성하여야 합니다. 
//         플레이어의 방향이나 장착될 방향이 올바르다면 정상적으로 장착됩니다.

//         `,
//         'example' : dedent
//         `
//         고체추진제 장착하기
//         setItem("고체추진제")
        
//         액체연료 장착하기
//         setItem("액체연료")
        
        
//         추가엔진 장착하기
//         setItem("추가엔진")
//         `
        
//     },
//     {
//         'id' : 6,
//         'stage' : '스테이지2',
//         'code' : 'and',
//         'type' : '연산자',
//         'description' : dedent 
//         `
//         and 연산자는 모든 논리값이 참(True)일 때 결과가 참이 되는 연산자입니다. 
//         만약 A and B 라는 표현식이 있다면, A와 B가 모두 참이어야 전체 표현식이 참이 됩니다.
//         하나라도 거짓(False)이라면 전체 표현식은 거짓이 됩니다. 

//         `,
//         'example' : dedent
//         `
//         x = 5
//         y = 10

//         if  x >= 5 and y < 9:
//             # 두 조건 중 하나가 거짓이므로 
//             # 아래 코드는 실행되지 않습니다.
//             turnLeft()
//             go(3)
//         elif  x == 5 and y == 10:
//             #두 조건 모두 참이므로 아래 코드 실행됩니다.
//             go(2)
//             turnLeft()
//         else : 
//             go(3)

//         `
        
//     },
//     {
//         'id' : 7,
//         'stage' : '스테이지2',
//         'code' : 'or',
//         'type' : '연산자',
//         'description' : dedent 
//         `
//         or 연산자는 논리값 중 하나라도 참(True)이면 결과가 참이 되는 연산자입니다. 
//         만약 A or B 라는 표현식이 있다면, A나 B 중 하나라도 참이면 전체 표현식이 참이 됩니다.
//         모든 조건이 거짓(False)이라면 전체 표현식은 거짓이 됩니다.

//         `,
//         'example' : dedent
//         `
//         x = 5
//         y = 10

//         if  x >= 5 or y < 9:
//             # 앞조건 충족하기에 
//             # 코드가 실행됩니다.
//             go()
//         `
        
//     },
//     {
//         'id' : 8,
//         'stage' : '스테이지2',
//         'code' : 'variable 변수',
//         'type' : '변수',
//         'description' : dedent 
//         `
//         변수는 객체(프로그램에서 메모리에 저장되는 모든 데이터)를 가리키는 식별자입니다. 일반적으로 특정 데이터값이나 문자열, 리스트에 있는 값을 저장할 수 있는 임시 저장소라고 알려져 있습니다. 
//         변수에 값을 할당한다는 것은 변수가 객체를 가리킨다는 의미로 객체에 이름표(변수 이름)를 붙인다는 것과 같습니다. 
//         변수값을 변경했다는 말은 다른 데이터 값을 가진 객체에 이름표를 이동하여 붙였다는 의미입니다. 

//         변수의 사용방법은 다음과 같습니다.
//         healPack = "치료제"
//         healPack이라는 변수에 "치료제" 문자열이 저장되어 있습니다.

//         변수 이름을 지을 때 규칙이 있습니다. 
//         1. 알파벳, 숫자, _(언더바)로 구성 (한글도 가능하지만 권장하지 않아요) 
//         2. 숫자로 시작할 수 없음 
//         3. 파이썬 키워드 (if, for 등)는 사용할 수 없음
//         4. 공백 포함하지 말 것 
//         또한, 변수 이름은 저장할 값을 유추할 수 있는 변수명으로 지정하는 것이 좋습니다.

//         `,
//         'example' : dedent
//         `
//         변수값에 따라 변수에 변수를 할당하거나 연산도 가능합니다. 

//         item1 = "box" 
//         item2 = "gold" 
//         items = item1 + item2

//         num1 = 10
//         num2 = 50
//         multiply = num1 * num2
//         `
        
//     },
//     {
//         'id' : 9,
//         'stage' : '스테이지2',
//         'code' : 'print()',
//         'type' : '함수/메서드',
//         'description' : dedent 
//         `
//         화면에 값을 출력해주는 함수로, 괄호 속 내용이 화면에 출력됩니다. 
//         문자를 출력할 때, 큰따옴표("")나 작은따옴표('')를 함께 사용해주세요. 

//         `,
//         'example' : dedent
//         `
//         숫자 출력
//         print(100)

//         문자 출력
//         print("100") 

//         하나씩 출력 
//         print("1")
//         print("2")

//         for 반복문을 사용하여 출력
//         for i in range(1, 6):
//         print(i)

//         변수를 포함하여 출력
//         num1 = 3
//         num2 = 10 
//         print("좋아하는 숫자는", num1, "입니다")  
//         print("싫어하는 숫자는" + num2 + "입니다")

//         2개 이상의 문자열을 합친 후 출력 
//         charA = "Code"
//         charB = "Ython"
//         print(charA + charB)
//         `
        
//     },
//     {
//         'id' : 10,
//         'stage' : '스테이지2',
//         'code' : 'getInfo()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         게임에서 정보를 획득할때 사용되는 명령어입니다.
//         컴퓨터 앞에서 사용할 수 있으며, 문자열 정보를 얻을 수 있습니다. 

//         `,
//         'example' : dedent
//         `
//         컴퓨터로부터 정보를 얻어 변수 data에 저장
//         data = getInfo()
//         `
        
//     },
//     {
//         'id' : 11,
//         'stage' : '스테이지2',
//         'code' : 'getNumber()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         게임에서 정보를 획득할때 사용되는 명령어입니다.
//         컴퓨터 앞에서 사용할 수 있으며, 숫자 정보를 얻을 수 있습니다. 

//         `,
//         'example' : dedent
//         `
//         컴퓨터로부터 정보를 얻어 변수 num에 저장
//         num = getNumber()
//         `
        
//     },
//     {
//         'id' : 12,
//         'stage' : '스테이지2',
//         'code' : 'checkFront()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         캐릭터의 앞에 위치한 객체를 탐지할 때 사용하는 명령어입니다. 
//         벽이나 폭탄 등 객체가 있을 시, 객체명을 반환합니다. 
//         if문과 함께 사용하여 다음과 같이 확인할 수 있습니다. 

//         `,
//         'example' : dedent
//         `
//         if checkFront() == "폭탄" : 
//             # 캐릭터 앞에 폭탄이 있을 시, 코드 작성 
//         elif checkFront() == "없음" : 
//             # 캐릭터 앞에 아무 것도 없을 시, 코드 작성 
//         else : 
//             # 캐릭터 앞에 폭탄 외 객체가 있거나 아무 것도 없을 시, 코드 작성
//         `
        
//     },
//     {
//         'id' : 13,
//         'stage' : '스테이지2',
//         'code' : 'checkFar()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         jump() 명령어를 사용하기 전, 점프할 위치에 객체(장애물)가 있는지 확인할 시 사용합니다. 
//         벽이나 폭탄 등 객체가 있을 시, 객체명을 반환합니다. 
//         if문과 함께 사용하여 다음과 같이 확인할 수 있습니다. 

//         `,
//         'example' : dedent
//         `
//         if  checkFar() == "폭탄": 
//             go()
//         else:  
//             jump()
//         `
        
//     },
//     {
//         'id' : 14,
//         'stage' : '스테이지2',
//         'code' : 'if',
//         'type' : '조건문',
//         'description' : dedent 
//         `
//         조건문 if는 특정 조건이 참(True)이면 해당 조건에 대한 코드 블록을 실행합니다. 
//         코드 블록은 들여쓰기로 범위를 나타냅니다. 

//         if 조건 :
//             # 조건이 참일 때 실행할 코드

//         `,
//         'example' : dedent
//         `
//         캐릭터 앞에 폭탄이 있다면 왼쪽으로 회전하여 한 칸 전진하기 
//         if checkFront() == "폭탄":
//             turnLeft()
//             go()
//         `
        
//     },
//     {
//         'id' : 15,
//         'stage' : '스테이지2',
//         'code' : 'elif',
//         'type' : '조건문',
//         'description' : dedent 
//         `
//         elif는 "else if"의 줄임말로, 
//         이전 조건이 거짓(False)일 때 새로운 조건을 확인하는 데 사용됩니다.
//         여러 개의 조건을 순차적으로 검사할 때 유용합니다. 
//         elif는 if를 1회 이상 사용한 이후에 쓸 수 있으며, 단독으로 사용할 수 없습니다.

//         if 조건1 :
//             # 조건1이 참일 때 실행할 코드
//         elif 조건2 :
//             # 조건1이 거짓이고, 조건2가 참일 때 실행할 코드

//         `,
//         'example' : dedent
//         `
//         캐릭터 앞에 폭탄이 있다면 왼쪽으로 회전하여 한 칸 전진하기 
//         만약 폭탄이 아닌 회복약이라면 앞으로 한 칸 전진하기 
//         if checkFront() == "폭탄":
//             turnLeft()
//             go()
//         elif checkFront() == "회복약":
//             go()
//         `
        
//     },
//     {
//         'id' : 16,
//         'stage' : '스테이지2',
//         'code' : 'else',
//         'type' : '조건문',
//         'description' : dedent 
//         `
//         else는 이전의 모든 조건이 거짓일 때 실행됩니다. 즉, 마지막으로 기본 동작을 정의할 때 사용됩니다.
//         else는 if와 사용하거나 if/elif/else와 사용할 수 있지만, 단독으로 사용할 수 없습니다. 
//         if, elif, else 문을 적절히 사용하여 특정 조건에 맞게 동작하도록 프로그래밍 해보세요. 

//         if 조건1 :
//             # 조건1이 참일 때 실행할 코드
//         elif 조건2 :
//             # 조건1이 거짓이고, 조건2가 참일 때 실행할 코드
//         else :
//             # 모든 조건이 거짓일 때 실행할 코드

//         `,
//         'example' : dedent
//         `
//         캐릭터 앞에 폭탄이 있다면 왼쪽으로 회전하여 한 칸 전진하기 
//         만약 폭탄이 아닌 회복약이라면 앞으로 한 칸 전진하기 
//         만약 폭탄도 아니고 회복약도 아니라면 오른쪽으로 회전하여 한 칸 전진하기
//         if checkFront() == "폭탄":
//             turnLeft()
//             go()
//         elif checkFront() == "회복약":
//             go()
//         else : 
//             turnRight()
//             go()
//         `
        
//     },
//     {
//         'id' : 17,
//         'stage' : '스테이지2',
//         'code' : '비교연산자',
//         'type' : '연산자',
//         'description' : dedent 
//         `
//         비교연산자는 수치형 데이터 또는 다른 비교 가능한 데이터를 비교하는 데 사용됩니다. 
//         주로 조건문과 함께 사용되어 조건을 평가하고 분기를 제어하는 데 사용됩니다.

//         작다(<): 왼쪽 피연산자가 오른쪽 피연산자보다 작은지를 확인합니다.
//         크다(>): 왼쪽 피연산자가 오른쪽 피연산자보다 큰지를 확인합니다.
//         작거나 같다(<=): 왼쪽 피연산자가 오른쪽 피연산자와 같거나 작은지를 확인합니다.
//         크거나 같다(>=): 왼쪽 피연산자가 오른쪽 피연산자와 같거나 큰지를 확인합니다.
//         같다  ( == ) : 왼쪽 피연산자와 오른쪽 피연산자가 같은지 확인합니다.
        
//         `,
//         'example' : dedent
//         `
//         변수 number가 10보다 작다면 1칸 전진, 10과 같다면 2칸 전진, 10보다 크다면 3칸 전진하기 

//         number = 10

//         if number < 10:
//             go()
//         elif number == 10:
//             go(2)
//         else number > 10:
//             go(3)
//         `
        
//     },
//     {
//         'id' : 18,
//         'stage' : '스테이지2',
//         'code' : '%',
//         'type' : '연산자',
//         'description' : dedent 
//         `
//         모듈러 연산자(%)는 나눗셈을 시행한 후 나머지를 구하는 연산자입니다. 
        
//         `,
//         'example' : dedent
//         `
//         리스트 a의 각 요소가 짝수인지 홀수인지 판별하기 
//         (2로 나눈 나머지가 0이면 짝수, 그렇지 않으면 홀수로 출력하기) 

//         a = [10, 3, 5, 80]

//         for i in range(0,5):
//             if a[i] % 2 == 0:
//                 print("짝수")
//             else 
//                 print("홀수")
//         `
        
//     },
//     {
//         'id' : 19,
//         'stage' : '스테이지2',
//         'code' : 'jump()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         캐릭터의 방향에서 한 칸을 건너뛰어 다음 칸으로 착지하는 명령어입니다. 
//         폭탄 등 장애물을 피할 수 있으니, if문과 checkFront()를 조합하여 적절히 사용해보세요. 

//         `,
//         'example' : dedent
//         `
//         캐릭터의 앞에 폭탄이 있다면, 점프하기
//         if checkFront() == "폭탄":
//             jump()
//         `
        
//     },
//     {
//         'id' : 20,
//         'stage' : '스테이지2',
//         'code' : 'while()',
//         'type' : '반복문',
//         'description' : dedent 
//         `
//         조건식이 참인 동안 동일한 코드블록을 반복합니다.
//         for문은 주로 반복횟수를 사용하는 반면 while문은 반복조건을 제시합니다. 

//         들여쓰기로 반복할 코드블록을 나타내며, 조건은 참/거짓으로 구분합니다. 
//         while True: 를 사용하는 경우 코드가 무한반복되니 적절하게 break를 사용할 필요가 있습니다. 
//         미션코드이썬 게임에서 무한반복이 걸려 목표를 달성하지 못하는 경우 코드가 실행되지 않으니 유의하세요. 
//         (while True:를 사용하더라도 목표를 달성하는 코드는 실행됩니다.) 

//         `,
//         'example' : dedent
//         `
//         캐릭터의 Hp가 30보다 클 경우 계속 반복하기 
//         -캐릭터의 앞에 아무 것도 없다면 1칸 이동하기 
//         -캐릭터의 앞에 폭탄이 있다면 왼쪽으로 회전 후 1칸 이동하기 

//         while getHp() > 30:
//         if  checkFront() == "없음": 
//             go()
//         elif checkfront() =="폭탄":
//             turnLeft()
//             go()
  
//         `
        
//     },
//     {
//         'id' : 21,
//         'stage' : '스테이지2',
//         'code' : 'getHp()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         현재 플레이어 캐릭터의 Hp 정보(체력 정보)를 가져오는 명령어입니다.
//         `,
//         'example' : dedent
//         `
//         캐릭터의 Hp가 20보다 클 경우, 1칸 이동하기 
//         그렇지 않다면 응급치료제 아이템 사용하기

//         if getHp()  > 20:
//             go()
//         else : 
//             use("응급치료제")   
//         `
        
//     },
//     {
//         'id' : 22,
//         'stage' : '스테이지2',
//         'code' : 'use()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         현재 보유중인 아이템을 사용하는 코드입니다.
//         응급치료제, 회복약, 치료키트등 회복 아이템을 사용할 수 있습니다. 
//         `,
//         'example' : dedent
//         `
//         계속 반복하기 
//         -캐릭터의 Hp가 30보다 작을 경우, 치료키트 사용하기 
//         -그렇지 않다면 1칸 이동하기 
//         while True :
//         if getHp() < 30:
//             use("치료키트")
//         else : 
//             go()
 
//         `
        
//     },
//     {
//         'id' : 23,
//         'stage' : '스테이지2',
//         'code' : 'break',
//         'type' : '반복제어문',
//         'description' : dedent 
//         `
//         break는 특정 조건이 되었을 때 반복문을 중단시킬 수 있습니다.
//         `,
//         'example' : dedent
//         `
//         i를 0에서 4까지 출력하기 
//         5가 되는 순간 반복문 종료하기 

//         i = 0
//         while True:
//             if i > 5:  
//                 break
//             i += 1
//             print(i)
 
//         `
        
//     },
//     {
//         'id' : 24,
//         'stage' : '스테이지2',
//         'code' : 'continue',
//         'type' : '반복제어문',
//         'description' : dedent 
//         `
//         continue는 반복문에서 사용되는 반복제어 키워드입니다. 
//         현재 반복 구문을 중단하고 다음 반복으로 넘어가도록 합니다. 
//         즉, continue가 실행되면, 반복문의 나머지 부분은 실행되지 않고, 다음 반복으로 진행됩니다. 
//         반복 중 특정 조건이 충족될 때 일부 코드를 건너뛰고자 할 때 유용합니다. 

//         `,
//         'example' : dedent
//         `
//         i가 짝수라면 아무것도 하지않고, 홀수라면 1칸 이동하기

//         for i in range(1, 6):
//             if i%2 ==0 :
//                 continue
//             go()
 
//         `
        
//     },
//     {
//         'id' : 25,
//         'stage' : '스테이지3',
//         'code' : 'upper()',
//         'type' : '함수/메서드',
//         'description' : dedent 
//         `
//         upper()는 입력받은 문자를 모두 대문자로 변환해주는 문자열처리 함수입니다. 
//         `,
//         'example' : dedent
//         `
//         inputData = getInfo()
//         result = inputData.upper()
//         `
        
//     },
//     {
//         'id' : 26,
//         'stage' : '스테이지3',
//         'code' : 'len()',
//         'type' : '함수/메서드',
//         'description' : dedent 
//         `
//         입력받은 문자의 길이 정보를 리턴해줍니다. 
//         `,
//         'example' : dedent
//         `
//         length 변수에 문자 길이 5 저장하기 
//         ch = "Hello" 
//         length = len(ch)
//         `
        
//     },
//     {
//         'id' : 27,
//         'stage' : '스테이지3',
//         'code' : 'pass',
//         'type' : '반복제어문',
//         'description' : dedent 
//         `
//         pass문은 아무런 동작도 하지 않고, 코드 블록을 건너뜁니다. 

//         보통 제어문이나 함수 등에서 블록을 채우는 것이 요구되지만, 구현은 나중에 할 때나 빈 블록을 일시적으로 만들고자 할 때 사용됩니다. 
//         예를 들어, 함수를 정의할 때 내용을 나중에 추가할 경우가 있습니다.

//         `,
//         'example' : dedent
//         `
//         짝수가 입력되면 아무것도 하지 않고, 홀수가 입력되면 값 더하기 

//         i = 1;
//         while True:
//             if i %2 == 0 :  
//                     pass
//             i += 1

//         `
        
//     },
//     {
//         'id' : 28,
//         'stage' : '스테이지3',
//         'code' : 'slicing 슬라이싱',
//         'type' : '슬라이싱',
//         'description' : dedent 
//         `
//         슬라이싱은 파이썬에서 시퀀스(문자열, 리스트, 튜플 등)의 일부를 추출하는 방법입니다. 
//         시퀀스의 일부를 선택하여 새로운 시퀀스를 만들거나 해당 부분의 값을 검색할 때 사용됩니다. 

//         `,
//         'example' : dedent
//         `
//         text 변수에 저장된 문자열 중, 1~5번째 인덱스(2~6번째 문자) 출력하기 
//         text = "Hello, World!"
//         result = text[1:6]
//         print(result)  # 출력: "ello," 

//         문자열 "Hello, World!"를 슬라이싱하여 2개씩 건너뛰면서 추출하기 
//         v = text[::2]
//         print(v)  # 출력: "Hlo ol!"
//         `
        
//     },
//     {
//         'id' : 29,
//         'stage' : '스테이지3',
//         'code' : 'isupper()',
//         'type' : '함수/메서드',
//         'description' : dedent 
//         `
//         문자열이 대문자인지 판별하는 문자열 처리 함수입니다. 
//         True 또는 False값을 반환합니다. 

//         `,
//         'example' : dedent
//         `
//         입력받은 문자가 대문자라면 1칸 이동, 그렇지않으면 오른쪽으로 회전하기 
//         info = getInfo()

//         for char in info:
//             if char.isupper():
//                 go()
//             else:
//                 turnRight()
//         `
        
//     },
//     {
//         'id' : 30,
//         'stage' : '스테이지3',
//         'code' : 'List 리스트',
//         'type' : '리스트',
//         'description' : dedent 
//         `
//         리스트는 여러 개의 데이터를 하나로 관리할 수 있는 자료형입니다. 
//         `,
//         'example' : dedent
//         `
//         리스트 생성 후, 제일 끝에 "회복약" 저장하기
//         itemList = []
//         itemList.append("회복약")
//         `
        
//     },
//     {
//         'id' : 31,
//         'stage' : '스테이지3',
//         'code' : 'check()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         아이템이 무엇인지 확인할 때 사용합니다. 
//         아이템명을 반환합니다. 

//         `,
//         'example' : dedent
//         `
//         myList = []
//         itemList  = getInfo()

//         for item in itemList:
//             if check(item) == "폭탄":
//                 continue
//             else :
//                 myList.append(item)
//         `
        
//     },
//     {
//         'id' : 32,
//         'stage' : '스테이지3',
//         'code' : 'function 함수',
//         'type' : '사용자 정의 함수',
//         'description' : dedent 
//         `
//         함수는 특정 기능을 실행하는 재사용 가능한 코드블록입니다. 함수는 한 번 작성한 후, 필요할 때 언제든 사용할 수 있습니다. 
//         단, 함수를 실행하기 전 미리 정의해놓아야 합니다. 

//         `,
//         'example' : dedent
//         `
//         -폭탄을 설치한 후, 뒤로 1칸 이동 및 앞으로 회전하는 함수 
//         -앞으로 1칸 이동 및 함수 실행

//         def  setTrapAndGoBack():
//             set("폭탄")
//             turnLeft(2)
//             go()
//             turnLeft(2)

//         go()
//         setTrapAndGoBack()
//         `
        
//     },
//     {
//         'id' : 33,
//         'stage' : '스테이지3',
//         'code' : 'getNumberList()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         숫자 리스트를 가져오는 명령어입니다.
//         `,
//         'example' : dedent
//         `
//         numList = getNumberList()
//         for i in numList:
//             print(i)
//         `
        
//     },
//     {
//         'id' : 34,
//         'stage' : '스테이지3',
//         'code' : 'getItem()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         아이템을 획득하는 명령어입니다.
    
//         아이템은 문자열로 주어집니다.
//         `,
//         'example' : dedent
//         `
//         item = getItem()
//         print(item)
//         `
        
//     },
//     {
//         'id' : 35,
//         'stage' : '스테이지3',
//         'code' : 'getItemList()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         아이템리스트를 획득 하는 함수입니다.
    
//         아이템리스트는 문자열 리스트로 주어집니다.
//         `,
//         'example' : dedent
//         `
//         itemList = getItemList()
//         myList = []
    
//         for item in itemList:
//             if(item != "고장난 폭탄") :
//                 myList.append(item)
//         `
//     },
//     {
//         'id' : 36,
//         'stage' : '스테이지3',
//         'code' : 'attack()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         적을 공격하는 명령어입니다.
//         일반적인 공격범위는 8칸이며, attack("적이름") 으로 사용할 수 있습니다. 

//         `,
//         'example' : dedent
//         `
//         go(2)
//         attack("해적1")
//         turnLeft()
//         go()
//         attack("해적2")
//         `
//     },
//     {
//         'id' : 37,
//         'stage' : '스테이지3',
//         'code' : 'chargeShot()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         chargeShot()을 사용하면 우주해적 보스에게 공격을 할 수 있습니다. 
//         주로 보스가 던진 폭탄을 획득하여 에너지원으로 사용합니다. 

//         `,
//         'example' : dedent
//         `
//         while True:
//         if checkfront() == "":
//             get("폭탄")
//             chargeShot()
//         else: 
//             go()
//         `
//     },
//     {
//         'id' : 38,
//         'stage' : '스테이지3',
//         'code' : 'findEnemy()',
//         'type' : '코드이쎤 명령어',
//         'description' : dedent 
//         `
//         근처에 있는 적의 이름을 알아내는 명령어입니다.
//         일반적으로 적의 이름을 알 수 없을 때 사용합니다.

//         `,
//         'example' : dedent
//         `
//         enemy = findEnemy()
//         attack(enemy)
//         `
//     },
//     {
//         'id' : 39,
//         'stage' : '스테이지3',
//         'code' : 'getBomb()',
//         'type' : '코드이썬 명령어',
//         'description' : dedent 
//         `
//         아이템을 획득할 때 사용합니다. 
//         괄호 속에 아이템명을 기재해야 하며, 주로 보스가 던진 폭탄을 획득합니다. 

//         `,
//         'example' : dedent
//         `
//         while True:
//             if checkFront() == "폭탄":
//                 getBomb("폭탄")
//                 chargeShot()
//         `
//     },
//     {
//         'id' : 40,
//         'stage' : '스테이지2',
//         'code' : 'checkLeft()',
//         'type' : '탐지코드',
//         'description' : dedent 
//         `
//         현재 캐릭터의 왼쪽에 있는 오브젝트를 탐지 할때 사용됩니다.

//         if문과 함께 사용하여 다음과 같이 확인 할수 있습니다.
//         `,
//         'example' : dedent
//         `
//         if  checkLeft() == "폭탄": 
//         # 왼편에 폭탄이 있을때 실행하고싶은 코드
//         `
        
//     },
//     {
//         'id' : 41,
//         'stage' : '스테이지2',
//         'code' : 'checkRight()',
//         'type' : '탐지코드',
//         'description' : dedent 
//         `
//         현재 캐릭터의 오른쪽에 있는 오브젝트를 탐지 할때 사용됩니다.

//         if문과 함께 사용하여 다음과 같이 확인 할수 있습니다.
//         `,
//         'example' : dedent
//         `
//         if  checkRight() == "폭탄": 
//         # 오른편에 폭탄이 있을때 실행하고싶은 코드
//         `
        
//     },
// ]

export const codeArray = [
    {
        'id' : 0,
        'stage' : '스테이지1',
        'code' : 'for',
        'type' : '반복문',
        'description' : dedent 
        `
        동일한 코드블록을 일정 횟수 반복합니다. 
        range() 함수나 리스트 등과 함께 사용됩니다. 

        for i in range(3) : 
        for문은 반복 작업을 수행하는데 사용됩니다. range(3)은 0부터 2까지 정수범위를 생성합니다.
        따라서 for i in range(3): 은 0, 1, 2 숫자를 각각 i에 할당하고 코드를 수행하므로 3번 반복하게 됩니다.
        첫 번째 반복에서는 'i'에 0이 할당되고, 두 번째 반복에서는 1이 할당되며, 세 번째 반복에서는 2가 할당됩니다.

        numList = [10, 20, 30] 
        for i in numList : 
        numList는 10, 20, 30을 요소로 가지고 있는 길이 3의 리스트입니다. 
        for i in numList: 구문은 numList의 각 요소를 하나씩 i에 할당하여 반복 작업을 수행합니다.
        첫 번째 반복에서는 'i'에 10이 할당되고, 두 번째 반복에서는 20가 할당되며, 세 번째 반복에서는 30이 할당됩니다. 

        `,
        'example' : dedent
        `
        플레이어가 한 칸 전진 후 왼쪽을 바라보는 동작을 3회 실행하기 
        for i in range(3):
        go()
        tunrLeft()

        리스트의 각 요소를 순서대로 출력하기 
        for i in numList:
            print(i)
        `
        
    },
    {
        'id' : 1,
        'stage' : '스테이지1',
        'code' : 'go()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        캐릭터가 바라보는 방향으로 한 칸 움직입니다. 
        두 칸 이상 움직이려면 인자(괄호 안에 입력하는 숫자)를 입력해야 합니다.

        `,
        'example' : dedent
        `
        한칸 이동
        go()     

        3칸 이동
        go(3)    
        `
        
    },
    {
        'id' : 2,
        'stage' : '스테이지1',
        'code' : 'range()',
        'type' : '함수/메서드',
        'description' : dedent 
        `
        일정 범위의 정수 집합을 생성하는 함수로, for와 함께 많이 사용됩니다. 
        range는 아래와 같이 구성되어있습니다. 
        range(시작값, 끝값, 증가값) 

        -시작값 : 범위의 시작 숫자입니다. 생략 시, 0으로 설정됩니다. 
        -끝값 : 범위의 마지막 숫자입니다. 단, 이 숫자는 범위에 포함되지 않습니다.  
        -증가값 : 증가값만큼 간격을 설정합니다. 생략 시, 1로 설정됩니다. 음수를 입력하면 역순으로 순회합니다.   

        `,
        'example' : dedent
        `
        1~5까지 범위 중, 1 간격으로 정수 생성 : 1, 2, 3, 4, 5
        range(1, 6) : 

        1~5까지 범위 중, 2 간격으로 정수 생성 : 1, 3, 5 
        range(1, 6, 2) : 

        5~1까지 범위 중, 1 간격으로 역순 정수 생성 : 5, 4, 3, 2, 1 
        range(5, 0, -1 ) :
        `
        
    },
    {
        'id' : 3,
        'stage' : '스테이지1',
        'code' : 'setItem()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        로켓 발사체에 엔진, 연료, 추진제 등 아이템을 장착하기 위해 사용되는 명령어입니다. 
        괄호 안에 큰따옴표("") 또는 작은따옴표('')를 사용하여 아이템명을 작성하여야 합니다. 
        플레이어의 방향이나 장착될 방향이 올바르다면 정상적으로 장착됩니다.

        `,
        'example' : dedent
        `
        고체추진제 장착하기
        setItem("고체추진제")
        
        액체연료 장착하기
        setItem("액체연료")
        
        
        추가엔진 장착하기
        setItem("추가엔진")
        `
        
    },
    {
        'id' : 4,
        'stage' : '스테이지1',
        'code' : 'turnLeft()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        플레이어 캐릭터의 방향을 왼쪽으로 90도 회전합니다. 
        `,
        'example' : dedent
        `
        왼쪽으로 회전
        turnLeft()
        
        180도 회전 (뒤돌기) 
        turnLeft(2)   
        `
        
    },
    {
        'id' : 5,
        'stage' : '스테이지1',
        'code' : 'turnRight()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        플레이어 캐릭터의 방향을 오른쪽으로 90도 회전합니다.
        `,
        'example' : dedent
        `
        오른쪽으로 회전
        turnRight()
        
        180도 회전 (뒤돌기)
        turnRight(2)
        `
        
    },
    {
        'id' : 6,
        'stage' : '스테이지2',
        'code' : 'and',
        'type' : '연산자',
        'description' : dedent 
        `
        and 연산자는 모든 논리값이 참(True)일 때 결과가 참이 되는 연산자입니다. 
        만약 A and B 라는 표현식이 있다면, A와 B가 모두 참이어야 전체 표현식이 참이 됩니다.
        하나라도 거짓(False)이라면 전체 표현식은 거짓이 됩니다. 

        `,
        'example' : dedent
        `
        x = 5
        y = 10

        if  x >= 5 and y < 9:
            # 두 조건 중 하나가 거짓이므로 
            # 아래 코드는 실행되지 않습니다.
            turnLeft()
            go(3)
        elif  x == 5 and y == 10:
            #두 조건 모두 참이므로 아래 코드 실행됩니다.
            go(2)
            turnLeft()
        else : 
            go(3)

        `
        
    },
    {
        'id' : 7,
        'stage' : '스테이지2',
        'code' : 'break',
        'type' : '반복제어문',
        'description' : dedent 
        `
        break는 특정 조건이 되었을 때 반복문을 중단시킬 수 있습니다.
        `,
        'example' : dedent
        `
        i를 0에서 4까지 출력하기 
        5가 되는 순간 반복문 종료하기 

        i = 0
        while True:
            if i > 5:  
                break
            i += 1
            print(i)
 
        `
        
    },
    {
        'id' : 8,
        'stage' : '스테이지2',
        'code' : 'checkFar()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        현재 캐릭터의 한 칸 앞부터 두 칸 앞까지 위치에 객체(장애물)가 있는지 확인할 시 사용합니다. 
        벽이나 폭탄 등 객체가 있을 시, 객체명을 반환합니다. 
        if문과 함께 사용하여 다음과 같이 확인할 수 있습니다. 

        `,
        'example' : dedent
        `
        if  checkFar() == "폭탄": 
            go()
        else:  
            jump()
        `
        
    },
    {
        'id' : 9,
        'stage' : '스테이지2',
        'code' : 'checkFront()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        캐릭터의 앞에 위치한 객체를 탐지할 때 사용하는 명령어입니다. 
        벽이나 폭탄 등 객체가 있을 시, 객체명을 반환합니다. 
        if문과 함께 사용하여 다음과 같이 확인할 수 있습니다. 

        `,
        'example' : dedent
        `
        if checkFront() == "폭탄" : 
            # 캐릭터 앞에 폭탄이 있을 시, 코드 작성 
        elif checkFront() == "없음" : 
            # 캐릭터 앞에 아무 것도 없을 시, 코드 작성 
        else : 
            # 캐릭터 앞에 폭탄 외 객체가 있거나 아무 것도 없을 시, 코드 작성
        `
        
    },
    {
        'id' : 10,
        'stage' : '스테이지2',
        'code' : 'checkLeft()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        캐릭터의 왼쪽에 위치한 객체를 탐지할 때 사용하는 명령어입니다. 
        벽이나 폭탄 등 객체가 있을 시, 객체명을 반환합니다. 
        if문과 함께 사용하여 다음과 같이 확인할 수 있습니다. 

        `,
        'example' : dedent
        `
        if checkLeft() != "없음":
            turnLeft()
            go()
        `
        
    },
    {
        'id' : 11,
        'stage' : '스테이지2',
        'code' : 'checkRight()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        캐릭터의 오른쪽에 위치한 객체를 탐지할 때 사용하는 명령어입니다. 
        벽이나 폭탄 등 객체가 있을 시, 객체명을 반환합니다. 
        if문과 함께 사용하여 다음과 같이 확인할 수 있습니다. 

        `,
        'example' : dedent
        `
        if checkRight() == "없음":
            turnRight()
            go()
        `
        
    },
    {
        'id' : 12,
        'stage' : '스테이지2',
        'code' : 'continue',
        'type' : '반복제어문',
        'description' : dedent 
        `
        continue는 반복문에서 사용되는 반복제어 키워드입니다. 
        현재 반복 구문을 중단하고 다음 반복으로 넘어가도록 합니다. 
        즉, continue가 실행되면, 반복문의 나머지 부분은 실행되지 않고, 다음 반복으로 진행됩니다. 
        반복 중 특정 조건이 충족될 때 일부 코드를 건너뛰고자 할 때 유용합니다. 

        `,
        'example' : dedent
        `
        i가 짝수라면 아무것도 하지않고, 홀수라면 1칸 이동하기

        for i in range(1, 6):
            if i%2 ==0 :
                continue
            go()
 
        `
        
    },
    {
        'id' : 13,
        'stage' : '스테이지2',
        'code' : 'else',
        'type' : '조건문',
        'description' : dedent 
        `
        else는 이전의 모든 조건이 거짓일 때 실행됩니다. 즉, 마지막으로 기본 동작을 정의할 때 사용됩니다.
        else는 if와 사용하거나 if/elif/else와 사용할 수 있지만, 단독으로 사용할 수 없습니다. 
        if, elif, else 문을 적절히 사용하여 특정 조건에 맞게 동작하도록 프로그래밍 해보세요. 

        if 조건1 :
            # 조건1이 참일 때 실행할 코드
        elif 조건2 :
            # 조건1이 거짓이고, 조건2가 참일 때 실행할 코드
        else :
            # 모든 조건이 거짓일 때 실행할 코드

        `,
        'example' : dedent
        `
        캐릭터 앞에 폭탄이 있다면 왼쪽으로 회전하여 한 칸 전진하기 
        만약 폭탄이 아닌 회복약이라면 앞으로 한 칸 전진하기 
        만약 폭탄도 아니고 회복약도 아니라면 오른쪽으로 회전하여 한 칸 전진하기
        if checkFront() == "폭탄":
            turnLeft()
            go()
        elif checkFront() == "회복약":
            go()
        else : 
            turnRight()
            go()
        `
        
    },
    {
        'id' : 14,
        'stage' : '스테이지2',
        'code' : 'elif',
        'type' : '조건문',
        'description' : dedent 
        `
        elif는 "else if"의 줄임말로, 
        이전 조건이 거짓(False)일 때 새로운 조건을 확인하는 데 사용됩니다.
        여러 개의 조건을 순차적으로 검사할 때 유용합니다. 
        elif는 if를 1회 이상 사용한 이후에 쓸 수 있으며, 단독으로 사용할 수 없습니다.

        if 조건1 :
            # 조건1이 참일 때 실행할 코드
        elif 조건2 :
            # 조건1이 거짓이고, 조건2가 참일 때 실행할 코드

        `,
        'example' : dedent
        `
        캐릭터 앞에 폭탄이 있다면 왼쪽으로 회전하여 한 칸 전진하기 
        만약 폭탄이 아닌 회복약이라면 앞으로 한 칸 전진하기 
        if checkFront() == "폭탄":
            turnLeft()
            go()
        elif checkFront() == "회복약":
            go()
        `
        
    },
    {
        'id' : 15,
        'stage' : '스테이지2',
        'code' : 'getHp()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        현재 플레이어 캐릭터의 Hp 정보(체력 정보)를 가져오는 명령어입니다.
        `,
        'example' : dedent
        `
        캐릭터의 Hp가 20보다 클 경우, 1칸 이동하기 
        그렇지 않다면 응급치료제 아이템 사용하기

        if getHp()  > 20:
            go()
        else : 
            use("응급치료제")   
        `
        
    },
    {
        'id' : 16,
        'stage' : '스테이지2',
        'code' : 'getInfo()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        게임에서 정보를 획득할때 사용되는 명령어입니다.
        컴퓨터 앞에서 사용할 수 있으며, 문자열 정보를 얻을 수 있습니다. 

        `,
        'example' : dedent
        `
        컴퓨터로부터 정보를 얻어 변수 data에 저장
        data = getInfo()
        `
        
    },
    {
        'id' : 17,
        'stage' : '스테이지2',
        'code' : 'getNumber()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        게임에서 정보를 획득할때 사용되는 명령어입니다.
        컴퓨터 앞에서 사용할 수 있으며, 숫자 정보를 얻을 수 있습니다. 

        `,
        'example' : dedent
        `
        컴퓨터로부터 정보를 얻어 변수 num에 저장
        num = getNumber()
        `
        
    },
    {
        'id' : 18,
        'stage' : '스테이지2',
        'code' : 'if',
        'type' : '조건문',
        'description' : dedent 
        `
        조건문 if는 특정 조건이 참(True)이면 해당 조건에 대한 코드 블록을 실행합니다. 
        코드 블록은 들여쓰기로 범위를 나타냅니다. 

        if 조건 :
            # 조건이 참일 때 실행할 코드

        `,
        'example' : dedent
        `
        캐릭터 앞에 폭탄이 있다면 왼쪽으로 회전하여 한 칸 전진하기 
        if checkFront() == "폭탄":
            turnLeft()
            go()
        `
        
    },
    {
        'id' : 19,
        'stage' : '스테이지2',
        'code' : 'or',
        'type' : '연산자',
        'description' : dedent 
        `
        or 연산자는 논리값 중 하나라도 참(True)이면 결과가 참이 되는 연산자입니다. 
        만약 A or B 라는 표현식이 있다면, A나 B 중 하나라도 참이면 전체 표현식이 참이 됩니다.
        모든 조건이 거짓(False)이라면 전체 표현식은 거짓이 됩니다.

        `,
        'example' : dedent
        `
        x = 5
        y = 10

        if  x >= 5 or y < 9:
            # 앞조건 충족하기에 
            # 코드가 실행됩니다.
            go()
        `
        
    },
    {
        'id' : 20,
        'stage' : '스테이지2',
        'code' : 'use()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        현재 보유중인 아이템을 사용하는 코드입니다.
        응급치료제, 회복약, 치료키트등 회복 아이템을 사용할 수 있습니다. 
        `,
        'example' : dedent
        `
        계속 반복하기 
        -캐릭터의 Hp가 30보다 작을 경우, 치료키트 사용하기 
        -그렇지 않다면 1칸 이동하기 
        while True :
        if getHp() < 30:
            use("치료키트")
        else : 
            go()
 
        `
        
    },
    {
        'id' : 21,
        'stage' : '스테이지2',
        'code' : 'variable 변수',
        'type' : '변수',
        'description' : dedent 
        `
        변수는 객체(프로그램에서 메모리에 저장되는 모든 데이터)를 가리키는 식별자입니다. 일반적으로 특정 데이터값이나 문자열, 리스트에 있는 값을 저장할 수 있는 임시 저장소라고 알려져 있습니다. 
        변수에 값을 할당한다는 것은 변수가 객체를 가리킨다는 의미로 객체에 이름표(변수 이름)를 붙인다는 것과 같습니다. 
        변수값을 변경했다는 말은 다른 데이터 값을 가진 객체에 이름표를 이동하여 붙였다는 의미입니다. 

        변수의 사용방법은 다음과 같습니다.
        healPack = "치료제"
        healPack이라는 변수에 "치료제" 문자열이 저장되어 있습니다.

        변수 이름을 지을 때 규칙이 있습니다. 
        1. 알파벳, 숫자, _(언더바)로 구성 (한글도 가능하지만 권장하지 않아요) 
        2. 숫자로 시작할 수 없음 
        3. 파이썬 키워드 (if, for 등)는 사용할 수 없음
        4. 공백 포함하지 말 것 
        또한, 변수 이름은 저장할 값을 유추할 수 있는 변수명으로 지정하는 것이 좋습니다.

        `,
        'example' : dedent
        `
        변수값에 따라 변수에 변수를 할당하거나 연산도 가능합니다. 

        item1 = "box" 
        item2 = "gold" 
        items = item1 + item2

        num1 = 10
        num2 = 50
        multiply = num1 * num2
        `
        
    },
    {
        'id' : 22,
        'stage' : '스테이지2',
        'code' : 'while()',
        'type' : '반복문',
        'description' : dedent 
        `
        조건식이 참인 동안 동일한 코드블록을 반복합니다.
        for문은 주로 반복횟수를 사용하는 반면 while문은 반복조건을 제시합니다. 

        들여쓰기로 반복할 코드블록을 나타내며, 조건은 참/거짓으로 구분합니다. 
        while True: 를 사용하는 경우 코드가 무한반복되니 적절하게 break를 사용할 필요가 있습니다. 
        미션코드이썬 게임에서 무한반복이 걸려 목표를 달성하지 못하는 경우 코드가 실행되지 않으니 유의하세요. 
        (while True:를 사용하더라도 목표를 달성하는 코드는 실행됩니다.) 

        `,
        'example' : dedent
        `
        캐릭터의 Hp가 30보다 클 경우 계속 반복하기 
        -캐릭터의 앞에 아무 것도 없다면 1칸 이동하기 
        -캐릭터의 앞에 폭탄이 있다면 왼쪽으로 회전 후 1칸 이동하기 

        while getHp() > 30:
        if  checkFront() == "없음": 
            go()
        elif checkfront() =="폭탄":
            turnLeft()
            go()
  
        `
        
    },
    {
        'id' : 23,
        'stage' : '스테이지2',
        'code' : '%',
        'type' : '연산자',
        'description' : dedent 
        `
        모듈러 연산자(%)는 나눗셈을 시행한 후 나머지를 구하는 연산자입니다. 
        
        `,
        'example' : dedent
        `
        리스트 a의 각 요소가 짝수인지 홀수인지 판별하기 
        (2로 나눈 나머지가 0이면 짝수, 그렇지 않으면 홀수로 출력하기) 

        a = [10, 3, 5, 80]

        for i in range(0,5):
            if a[i] % 2 == 0:
                print("짝수")
            else 
                print("홀수")
        `
        
    },
    {
        'id' : 24,
        'stage' : '스테이지3',
        'code' : 'attack()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        적을 공격하는 명령어입니다.
        일반적인 공격범위는 8칸이며, attack("적이름") 으로 사용할 수 있습니다. 

        `,
        'example' : dedent
        `
        go(2)
        attack("해적1")
        turnLeft()
        go()
        attack("해적2")
        `
    },
    {
        'id' : 25,
        'stage' : '스테이지3',
        'code' : 'chargeShot()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        chargeShot()을 사용하면 우주해적 보스에게 공격을 할 수 있습니다. 
        주로 보스가 던진 폭탄을 획득하여 에너지원으로 사용합니다. 

        `,
        'example' : dedent
        `
        while True:
        if checkfront() == "폭탄":
            getBomb()
            chargeShot()
        else: 
            go()
        `
    },
    {
        'id' : 26,
        'stage' : '스테이지3',
        'code' : 'check()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        아이템 리스트를 확인하여 목표에서 요구하는 구성에 맞는지 검사합니다.

        `,
        'example' : dedent
        `
        myList = []
        itemList  = getInfo()

        for item in itemList:
            if item != "고장난 폭탄":
                myList.append(item)

        check(myList)
        `
        
    },
    {
        'id' : 27,
        'stage' : '스테이지3',
        'code' : 'findEnemy()',
        'type' : '코드이쎤 명령어',
        'description' : dedent 
        `
        근처에 있는 적의 이름을 알아내는 명령어입니다.
        일반적으로 적의 이름을 알 수 없을 때 사용합니다.

        `,
        'example' : dedent
        `
        enemy = findEnemy()
        attack(enemy)
        `
    },
    {
        'id' : 28,
        'stage' : '스테이지3',
        'code' : 'function 함수',
        'type' : '사용자 정의 함수',
        'description' : dedent 
        `
        함수는 특정 기능을 실행하는 재사용 가능한 코드블록입니다. 함수는 한 번 작성한 후, 필요할 때 언제든 사용할 수 있습니다. 
        단, 함수를 실행하기 전 미리 정의해놓아야 합니다. 

        `,
        'example' : dedent
        `
        -폭탄을 설치한 후, 뒤로 1칸 이동 및 앞으로 회전하는 함수 
        -앞으로 1칸 이동 및 함수 실행

        def  setTrapAndGoBack():
            setItem("폭탄")
            turnLeft(2)
            go()
            turnLeft(2)

        go()
        setTrapAndGoBack()
        `
        
    },
    {
        'id' : 29,
        'stage' : '스테이지3',
        'code' : 'getBomb()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        아이템을 획득할 때 사용합니다. 
        보스가 던진 폭탄을 획득합니다. 

        `,
        'example' : dedent
        `
        while True:
            if checkFront() == "폭탄":
                getBomb()
                chargeShot()
        `
    },
    {
        'id' : 30,
        'stage' : '스테이지3',
        'code' : 'getItem()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        아이템을 획득할 때 사용할 수 있습니다. 
        `,
        'example' : dedent
        `
        아이템을 얻어 변수 item에 저장
        item = getItem() 
        `
        
    },
    {
        'id' : 31,
        'stage' : '스테이지3',
        'code' : 'getItemList()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        아이템 리스트를 획득할 때 사용하는 명령어입니다. 
        `,
        'example' : dedent
        `
        아이템 리스트를 얻어 itemListAll에 저장한 후 확인 
        itemListAll = getItemList()
        check(itemListAll)  
        `
    },
    {
        'id' : 32,
        'stage' : '스테이지3',
        'code' : 'getNumberList()',
        'type' : '코드이썬 명령어',
        'description' : dedent 
        `
        숫자 리스트를 가져오는 명령어입니다.
        `,
        'example' : dedent
        `
        numList = getNumberList()
        for i in numList:
            print(i)
        `
        
    },
    {
        'id' : 33,
        'stage' : '스테이지3',
        'code' : 'isupper()',
        'type' : '함수/메서드',
        'description' : dedent 
        `
        문자열이 대문자인지 판별하는 문자열 처리 함수입니다. 
        True 또는 False값을 반환합니다. 

        `,
        'example' : dedent
        `
        입력받은 문자가 대문자라면 1칸 이동, 그렇지않으면 오른쪽으로 회전하기 
        info = getInfo()

        for char in info:
            if char.isupper():
                go()
            else:
                turnRight()
        `
        
    },
    {
        'id' : 34,
        'stage' : '스테이지3',
        'code' : 'len()',
        'type' : '함수/메서드',
        'description' : dedent 
        `
        입력받은 문자의 길이 정보를 리턴해줍니다. 
        `,
        'example' : dedent
        `
        length 변수에 문자 길이 5 저장하기 
        ch = "Hello" 
        length = len(ch)
        `
        
    },
    {
        'id' : 35,
        'stage' : '스테이지3',
        'code' : 'List 리스트',
        'type' : '리스트',
        'description' : dedent 
        `
        리스트는 여러 개의 데이터를 하나로 관리할 수 있는 자료형입니다. 
        `,
        'example' : dedent
        `
        리스트 생성 후, 제일 끝에 "회복약" 저장하기
        itemList = []
        itemList.append("회복약")
        `
        
    },
    {
        'id' : 36,
        'stage' : '스테이지3',
        'code' : 'pass',
        'type' : '반복제어문',
        'description' : dedent 
        `
        pass문은 아무런 동작도 하지 않고, 코드 블록을 건너뜁니다. 

        보통 제어문이나 함수 등에서 블록을 채우는 것이 요구되지만, 구현은 나중에 할 때나 빈 블록을 일시적으로 만들고자 할 때 사용됩니다. 
        예를 들어, 함수를 정의할 때 내용을 나중에 추가할 경우가 있습니다.

        `,
        'example' : dedent
        `
        짝수가 입력되면 아무것도 하지 않고, 홀수가 입력되면 값 더하기 

        i = 1;
        while True:
            if i %2 == 0 :  
                    pass
            i += 1

        `
        
    },
    {
        'id' : 37,
        'stage' : '스테이지3',
        'code' : 'slicing 슬라이싱',
        'type' : '슬라이싱',
        'description' : dedent 
        `
        슬라이싱은 파이썬에서 시퀀스(문자열, 리스트, 튜플 등)의 일부를 추출하는 방법입니다. 
        시퀀스의 일부를 선택하여 새로운 시퀀스를 만들거나 해당 부분의 값을 검색할 때 사용됩니다. 

        `,
        'example' : dedent
        `
        text 변수에 저장된 문자열 중, 1~5번째 인덱스(2~6번째 문자) 출력하기 
        text = "Hello, World!"
        result = text[1:6]
        print(result)  # 출력: "ello," 

        문자열 "Hello, World!"를 슬라이싱하여 2개씩 건너뛰면서 추출하기 
        v = text[::2]
        print(v)  # 출력: "Hlo ol!"
        `
        
    },
    {
        'id' : 38,
        'stage' : '스테이지3',
        'code' : 'upper()',
        'type' : '함수/메서드',
        'description' : dedent 
        `
        upper()는 입력받은 문자를 모두 대문자로 변환해주는 문자열처리 함수입니다. 
        `,
        'example' : dedent
        `
        inputData = getInfo()
        result = inputData.upper()
        `
        
    }
]