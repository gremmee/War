package nl.gremmee.war.test;

public class Test {
    public static void main(String[] args) {
        int max = 36;

        int total = 1;
        long side = Math.round(Math.sqrt(max));
        System.out.println("max " + max);
        System.out.println("side " + side);
        System.out.println();
        total = 1;
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                System.out.println("x = " + x);
                System.out.println("y = " + y);
                System.out.println("total = " + total);

                if ((y == 0) && (x == 0)) {
                    System.out.println("((y == 0) && (x == 0))");
                    System.out.println("Connection r " + (total + 1));
                    System.out.println("Connection d " + (total + side));
                } else if ((y == (side - 1)) && (x == (side - 1))) {
                    System.out.println("((y == side-1) && (x == side-1))");
                    System.out.println("Connection l " + (total - 1));
                    System.out.println("Connection u " + (total - side));
                } else if ((x == (side - 1)) && (y == 0)) {
                    System.out.println("((x == (side - 1)) && (y == 0))");
                    System.out.println("Connection d " + (total + side));
                    System.out.println("Connection l " + (total - 1));
                } else if ((x == 0) && (y == (side - 1))) {
                    System.out.println("(x == 0 && (y == (side - 1)))");
                    System.out.println("Connection r " + (total + 1));
                    System.out.println("Connection u " + (total - side));
                } else if (y == 0) {
                    System.out.println("(y == 0)");
                    System.out.println("Connection r " + (total + 1));
                    System.out.println("Connection d " + ((total % side) + side + y));
                    System.out.println("Connection l " + (total - 1));
                } else if (x == 0) {
                    System.out.println("(x == 0)");
                    System.out.println("Connection r " + (total + 1));
                    System.out.println("Connection d " + (total + side));
                    System.out.println("Connection u " + (total - side));
                } else if (y == (side - 1)) {
                    System.out.println("(y == side-1)");
                    System.out.println("Connection r " + (total + 1));
                    System.out.println("Connection l " + (total - 1));
                    System.out.println("Connection u " + (total - side));
                } else if (x == (side - 1)) {
                    System.out.println("(x == side-1)");
                    System.out.println("Connection d " + (total + side));
                    System.out.println("Connection l " + (total - 1));
                    System.out.println("Connection u " + (total - side));
                } else {
                    System.out.println("else");
                    System.out.println("Connection r " + (total + 1));
                    System.out.println("Connection d " + (total + side));
                    System.out.println("Connection l " + (total - 1));
                    System.out.println("Connection u " + (total - side));
                }

                total++;
            }
        }
    }
}
