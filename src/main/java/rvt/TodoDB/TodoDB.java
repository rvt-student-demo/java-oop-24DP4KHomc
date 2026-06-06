package rvt.TodoDB; // Šeit ieraksti savas jaunās mapes nosaukumu!

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoDB {

    // Datubāzes faila nosaukums. Tas automātiski izveidosies tava projekta mapē.
    private static final String DB_URL = "jdbc:sqlite:todo.db";

    // Konstruktors - kad uztaisīs `new TodoDB()`, uzreiz palaidīsies tabulas izveide
    public TodoDB() {
        initSchema();
    }

    // Izveidojam savienojumu ar SQLite datubāzi
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Izveido tabulu, ja tāda vēl neekstistē (tieši kā tavā bildē)
    private void initSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS todo ("
                   + "id INTEGER PRIMARY KEY,"
                   + "task TEXT NOT NULL) STRICT";
        try (
            Connection conn = connect();
            Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Schema init failed: " + e.getMessage());
        }
    }

    // --- ŠEIT SĀKAS PIEVIENOTĀS METODES ---

    // add(String task) -> Pievieno jaunu rindiņu ar Prepared Statement
    public void add(String task) {
        String sql = "INSERT INTO todo (task) VALUES (?)";

        try (
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, task); // Droši ieliekam tekstu jautājuma zīmes vietā
            pstmt.executeUpdate();
            System.out.println("[DB] Pievienots: " + task);
        } catch (SQLException e) {
            System.out.println("Kļūda pievienojot uzdevumu: " + e.getMessage());
        }
    }

    // findAll() -> Izmanto SELECT un parāda / atgriež visus ierakstus kā parastus tekstus
    public List<String> findAll() {
        List<String> visiUzdevumi = new ArrayList<>();
        String sql = "SELECT id, task FROM todo";

        try (
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql) // Šis izpilda SELECT un paņem rezultātus
        ) {
            // Kamēr datubāzē ir nākamā rindiņa, lasām to ārā
            while (rs.next()) {
                int id = rs.getInt("id");
                String task = rs.getString("task");
                
                // Smuki noformējam kā tekstu un iemetam sarakstā
                visiUzdevumi.add(id + ". " + task);
            }
        } catch (SQLException e) {
            System.out.println("Kļūda lasot datus: " + e.getMessage());
        }

        return visiUzdevumi;
    }

    // removeById(int id) -> Izdzēš rindiņu pēc ID (bildē bija kļūda ar '=', te ir izlabots)
    public void removeById(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";

        try (
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id); // Ieliekam skaitli jautājuma zīmes vietā
            int izdzēstāsRindas = pstmt.executeUpdate();
            
            if (izdzēstāsRindas > 0) {
                System.out.println("[DB] Uzdevums ar ID " + id + " ir izdzēsts!");
            } else {
                System.out.println("[DB] ID " + id + " nemaz neatradās datubāzē.");
            }
        } catch (SQLException e) {
            System.out.println("Kļūda dzēšot uzdevumu: " + e.getMessage());
        }
    }
}