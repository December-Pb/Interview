class StringTest {
    public static void main(String[] args) {
        String s = "1.2.3.4";
        String[]sArray = s.split("\\.");
        String ss = s.replace(".", "p");
        for(String temp : sArray) {
            System.out.println(temp);
        }
        System.out.println(ss);
        "aaa".replace("aa", "b");
    }
}