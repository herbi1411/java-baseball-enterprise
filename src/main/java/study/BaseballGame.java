package study;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BaseballGame {

    private final String answer;
    private final int DIGITS;

    public BaseballGame() {
        DIGITS = 3;
        answer = generateAnswer();
    }
    private String generateAnswer() {
        Set<Integer> hashSet = new HashSet<Integer>();
        String numbers = "";
        for (int i =0; i<DIGITS; i++) {
            numbers += generateNumber(hashSet);
        }

        return numbers;
    }

    private String generateNumber(Set<Integer> hashSet) {
        Random random = new Random();
        int phase = hashSet.size();
        int randomNum = -1;
        while (phase == hashSet.size()) {
            randomNum = random.nextInt(8) + 1; // 1 ~ 9까지의 숫자 1개 추출
            hashSet.add(randomNum);
        }
        return Integer.toString(randomNum);
    }

    public String getUserInput() {
        System.out.println("숫자를 입력해주세요: ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        validateUserInput(userInput);
        scanner.close();
        return userInput;
    }

    private boolean validateUserInput(String userInput) {
        if (!validate_length(userInput)) {
            throw new IllegalArgumentException("입력이 3자리가 아닙니다.");
        }
        else if (!validate_consistOfNumbers(userInput)) {
            throw new IllegalArgumentException("입력이 숫자로 구성돼있지 않습니다.");
        }
        else if (!validate_duplicateNumbers(userInput)) {
            throw new IllegalArgumentException("중복된 숫자가 입력됐습니다.");
        }
        return true;
    }

    private boolean validate_duplicateNumbers(String userInput) {
        Set<Character> hashSet = new HashSet<Character>();
        for(int i =0; i<userInput.length(); i++) {
            hashSet.add(userInput.charAt(i));
        }
        return hashSet.size() == DIGITS;
    }


    private boolean validate_length(String UserInput) {
        return UserInput.length() == DIGITS;
    }
    private boolean validate_consistOfNumbers(String userInput) {

        int flag = 1;
        for(int i = 0; i < userInput.length(); i++) {
            flag *= validate_number(userInput.charAt(i));
        }
        return flag == 1;
    }
    private int validate_number(char c) {
        if (c >= '1' && c <= '9')
            return 1;
        return 0;
    }


    public GameResultSet proceedRound() {
        String userInput = getUserInput();
        Set<Character> hashSet = new HashSet<Character>();
        GameResultSet resultSet = new GameResultSet();
        for (int i = 0; i < answer.length(); i++) {
            hashSet.add(answer.charAt(i));
        }
        for (int i = 0; i < userInput.length(); i++) {
            judgeNumber(userInput.charAt(i), answer.charAt(i), hashSet, resultSet);
        }
        printScore(resultSet);
        return resultSet;
    }

    private void judgeNumber(char ui, char aw, Set<Character> hashSet, GameResultSet resultSet) {
        if (ui == aw) {
            resultSet.strike += 1;
        }
        else if (hashSet.contains(ui)) {
            resultSet.ball += 1;
        }
    }

    private void printScore(GameResultSet resultSet) {
        if (resultSet.strike == 0 && resultSet.ball == 0) {
            System.out.println("낫싱");
        }
        else if (resultSet.strike == 0) {
            System.out.println(resultSet.ball + "볼");
        }
        else if (resultSet.ball == 0) {
            System.out.println(resultSet.strike + "스트라이크");
        }
        else if (resultSet.strike != 0 && resultSet.ball != 0) {
            System.out.println(resultSet.strike + "스트라이크 " + resultSet.ball + "볼");
        }
    }

    private boolean isCorrectAnswer(GameResultSet resultSet) {
        return resultSet.strike == DIGITS && resultSet.ball == 0;
    }
}
