public class StrikeOut {

    public static void main(String[] args) {
        System.out.println(toStrikeOut("0123456789"));
        System.out.println(toStrikeOut("RIAN SPENT TOO LONG"));
        System.out.println(toStrikeOut("TRYING TO GET THIS TO WORK"));
    }

    private static String toStrikeOut(String str) {
        final char strike = '\u0336';
        final StringBuilder builder = new StringBuilder();
        for (char c : str.toCharArray()) {
            builder.append(c).append(strike);
        }
        return builder.toString();
    }

}
