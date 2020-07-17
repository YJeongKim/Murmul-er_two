package space.yjeong.util;

public class ViewHelper {

    public static int count = 0;

    public static String convertMoneyToString(int money) {
        int convertMoney = money / 10000;
        String result = "";

        if (convertMoney == 0) {
            result = "없음";
        } else if (convertMoney > 9999) {
            result += (convertMoney / 10000) + "억 ";
            if (convertMoney % 10000 != 0) {
                result += (convertMoney % 10000) + "만 원";
            }
        } else {
            result += convertMoney + "만 원";
        }
        return result;
    }
}
