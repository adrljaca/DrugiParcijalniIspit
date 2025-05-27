import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------ Izbornik ------");
            System.out.println("1. Unesi novog polaznika");
            System.out.println("2. Unesi novi program obrazovanja");
            System.out.println("3. Upiši polaznika na program");
            System.out.println("4. Prebaci polaznika u drugi program");
            System.out.println("5. Prikaži sve polaznike iz programa");
            System.out.println("0. Izlaz iz programa");
            System.out.print("Odaberi opciju: ");

            int opcija = sc.nextInt();
            sc.nextLine();

            switch (opcija) {
                case 1:
                    Operacije.insertPolaznik(sc);
                    break;
                case 2:
                    Operacije.insertProgram(sc);
                    break;
                case 3:
                    Operacije.upisiPolaznika(sc);
                    break;
                case 4:
                    Operacije.prebaciPolaznika(sc);
                    break;
                case 5:
                    Operacije.ispisiPolaznike(sc);
                    break;
                case 0:
                    System.out.println("Izlazak iz programa...");
                    sc.close();
                    return;
                default:
                    System.out.println("Nepoznata opcija. Pokušajte ponovno!");
                    break;
            }
        }
    }
}