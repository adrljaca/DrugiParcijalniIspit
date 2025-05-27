import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Operacije {

    public static void insertPolaznik(Scanner sc) {
        System.out.print("Unesi ime: ");
        String ime = sc.nextLine();
        System.out.print("Unesi prezime: ");
        String prezime = sc.nextLine();

        try (Connection conn = Database.connect()) {
            String sql = "INSERT INTO Polaznik (Ime, Prezime) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ime);
            stmt.setString(2, prezime);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Polaznik uspješno dodan.");
            }
        } catch (SQLException e) {
            System.out.println("Greška kod dodavanja polaznika.");
            e.printStackTrace();
        }
    }

    public static void insertProgram(Scanner sc) {
        System.out.print("Unesi naziv programa: ");
        String naziv = sc.nextLine();
        System.out.print("Unesi CSVET bodove: ");
        int csvet = sc.nextInt();
        sc.nextLine();

        try (Connection conn = Database.connect()) {
            String sql = "INSERT INTO ProgramObrazovanja (Naziv, CSVET) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, naziv);
            stmt.setInt(2, csvet);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Program uspješno dodan.");
            }
        } catch (SQLException e) {
            System.out.println("Greška kod dodavanja programa.");
            e.printStackTrace();
        }
    }

    public static void upisiPolaznika(Scanner sc) {
        System.out.print("Unesi ID polaznika: ");
        int polaznikID = sc.nextInt();
        System.out.print("Unesi ID programa obrazovanja: ");
        int programID = sc.nextInt();
        sc.nextLine();

        try (Connection conn = Database.connect()) {
            String sql = "INSERT INTO Upis (IDProgramObrazovanja, IDPolaznik) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, programID);
            stmt.setInt(2, polaznikID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Polaznik uspješno upisan.");
            }
        } catch (SQLException e) {
            System.out.println("Greška kod upisa.");
            e.printStackTrace();
        }
    }

    public static void prebaciPolaznika(Scanner sc) {
        System.out.print("Unesi ID polaznika: ");
        int polaznikID = sc.nextInt();
        System.out.print("Unesi novi ID programa obrazovanja: ");
        int noviProgramID = sc.nextInt();
        sc.nextLine();

        try (Connection conn = Database.connect()) {
            conn.setAutoCommit(false);

            String deleteSQL = "DELETE FROM Upis WHERE IDPolaznik = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, polaznikID);
            deleteStmt.executeUpdate();

            String insertSQL = "INSERT INTO Upis (IDProgramObrazovanja, IDPolaznik) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setInt(1, noviProgramID);
            insertStmt.setInt(2, polaznikID);
            insertStmt.executeUpdate();

            conn.commit();
            System.out.println("Polaznik je prebačen u novi program.");
        } catch (SQLException e) {
            System.out.println("Greška kod prebacivanja.");
            e.printStackTrace();
        }
    }

    public static void ispisiPolaznike(Scanner sc) {
        System.out.print("Unesi ID programa obrazovanja: ");
        int programID = sc.nextInt();
        sc.nextLine();

        try (Connection conn = Database.connect()) {
            String sql = """
                SELECT p.PolaznikID, p.Ime, p.Prezime, po.Naziv, po.CSVET
                FROM Upis u
                JOIN Polaznik p ON u.IDPolaznik = p.PolaznikID
                JOIN ProgramObrazovanja po ON u.IDProgramObrazovanja = po.ProgramObrazovanjaID
                WHERE po.ProgramObrazovanjaID = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, programID);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Polaznici u programu ---");
            while (rs.next()) {
                System.out.printf("ID: %d, Ime: %s, Prezime: %s, Program: %s, CSVET: %d\n",
                        rs.getInt("PolaznikID"),
                        rs.getString("Ime"),
                        rs.getString("Prezime"),
                        rs.getString("Naziv"),
                        rs.getInt("CSVET"));
            }

        } catch (SQLException e) {
            System.out.println("Greška kod ispisa polaznika.");
            e.printStackTrace();
        }
    }
}