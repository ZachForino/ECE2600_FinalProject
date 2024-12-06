public class Main {
    public static void main(String[] args) {
        double[] capacitors = {0.000000001, 0.00000001, 0.0000001, 0.00000047, 0.000001, 0.0000047};
        double[] resistors = {10000, 12000, 15000, 18000, 22000, 27000, 33000, 39000, 47000, 56000, 68000, 82000, 100000, 120000, 150000, 180000, 220000, 270000, 330000, 390000, 470000, 560000, 680000, 82000};
        lowPassSallenKey(capacitors, resistors);
        highPassSallenKey(capacitors, resistors);

        //finding r10 and r11
        double bias=0;
        double r10=0;
        double r11=0;
        for (int i = 0; i < resistors.length; i++) {
            for (int j = 0; j < resistors.length; j++) {
                double offset = offset(resistors[i], resistors[j]);
                if ( Math.abs(offset + 2.83) < Math.abs(bias + 2.83) ) { //1.67-4.5
                    bias = offset;
                    r10 = resistors[i];
                    r11 = resistors[j];
                }
            }
        }
        System.out.println("HP Bias");
        System.out.println("R10=" + r10);
        System.out.println("R11=" + r11);
        System.out.println("bias=" + bias);

        //finding r14 and r15
        double bias2=0;
        double r14=0;
        double r15=0;
        for (int i = 0; i < resistors.length; i++) {
            for (int j = 0; j < resistors.length; j++) {
                double offset = offset(resistors[i], resistors[j]);
                if ( Math.abs(offset + 1.1) < Math.abs(bias2 + 1.1) ) { //-1
                    bias2 = offset;
                    r14 = resistors[i];
                    r15 = resistors[j];
                }
            }
        }
        System.out.println("LP Bias");
        System.out.println("R14=" + r14);
        System.out.println("R15=" + r15);
        System.out.println("bias=" + bias2);

    }

    public static double offset(double r10, double r11) {
        return ((9*r11) / (r10+r11)) - 4.5;
    }


    public static void lowPassSallenKey(double[] capacitors, double[] resistors) {
        double qValue = 0;
        double fC = 0;
        double[] components = new double[4]; // {C7, C8, R6, R7}

        for (int i = 0; i < capacitors.length; i++) { // C7
            for (int j = 0; j < capacitors.length; j++) { // C8
                for (int k = 0; k < resistors.length; k++) { // R6
                    for (int l = 0; l < resistors.length; l++) { // R7
                        double tempQ = lPQ(resistors[k], resistors[l], capacitors[i], capacitors[j]);
                        double tempfC = lPFC(resistors[k], resistors[l], capacitors[i], capacitors[j]);
                        if (tempQ < 0.6) {
                            continue;
                        }

                        if ((Math.abs(tempfC - 250) < Math.abs(fC - 250)) && (tempQ < 0.8)) {
                            qValue = tempQ;
                            fC = tempfC;
                            components[0] = capacitors[i];
                            components[1] = capacitors[j];
                            components[2] = resistors[k];
                            components[3] = resistors[l];
                        }
                    }
                }
            }
        }
        System.out.println("Components for Low Pass Filter:");
        for (double component : components) {
            System.out.printf("%.10f\n", component);
        }
        System.out.println("Q value is: " + lPQ(components[2], components[3], components[0], components[1]));
        System.out.println("f_c value is: " + lPFC(components[2], components[3], components[0], components[1]));
    }

    public static double lPQ(double r6, double r7, double c7, double c8) {
        return Math.sqrt(r6 * r7 * c7 * c8) / (c7*(r6 + r7));
    }

    public static double lPFC(double r6, double r7, double c7, double c8) {
        double frequency = 1 / (Math.sqrt(r6 * r7 * c7 * c8));
        return frequency / (2 * Math.PI);
    }


    public static void highPassSallenKey(double[] capacitors, double[] resistors) {
        double qValue = 0;
        double fC = 0;
        double[] components = new double[4]; // {C9,C10,R8,R9}

        for (int i = 0; i < capacitors.length; i++) { // C9
            for (int j = 0; j < capacitors.length; j++) { // C10
                for (int k = 0; k < resistors.length; k++) { // R8
                    for (int l = 0; l < resistors.length; l++) { // R9
                        double tempQ = hPQ(resistors[k], resistors[l], capacitors[i], capacitors[j]);
                        double tempfC = hPFC(resistors[k], resistors[l], capacitors[i], capacitors[j]);
                        if (tempQ < 0.6) {
                            continue;
                        }

                        if ((Math.abs(tempfC - 500) < Math.abs(fC - 500)) && (tempQ < 0.75) && (tempQ > 0.65)) {
                            qValue = tempQ;
                            fC = tempfC;
                            components[0] = capacitors[i];
                            components[1] = capacitors[j];
                            components[2] = resistors[k];
                            components[3] = resistors[l];
                        }
                    }
                }
            }
        }
        System.out.println("Components for High Pass Filter:");
        for (double component : components) {
            System.out.printf("%.10f\n", component);
        }
        System.out.println("Q value is: " + hPQ(components[2], components[3], components[0], components[1]));
        System.out.println("f_c value is: " + hPFC(components[2], components[3], components[0], components[1]));
    }

    public static double hPQ(double r8, double r9, double c9, double c10) {
        return Math.sqrt(r8 * r9 * c9 * c10) / (r9 * (c9 + c10));
    }

    public static double hPFC(double r8, double r9, double c9, double c10) {
        double frequency = 1 / (Math.sqrt(r8 * r9 * c9 * c10));
        return frequency / (2 * Math.PI);
    }

}
