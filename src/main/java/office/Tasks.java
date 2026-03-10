package office;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tasks {
    public static void main(String[] args) {
        Tasks task = new Tasks();
        System.out.println("1. Найдите ID сотрудника с именем Ann. Если такой сотрудник только один, то установите его департамент в HR.");
        task.findAnn();
        System.out.println("2. Проверьте имена всех сотрудников. Если чьё-то имя написано с маленькой буквы, исправьте её на большую." +
                "Выведите на экран количество исправленных имён");
        task.checkNames();
        System.out.println("3. Выведите на экран количество сотрудников в IT-отделе");
        task.countItEmployees();



    }
    public void findAnn() {
        List<Integer> annIds = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:h2:~\\Office")) {
            Service.createDB();
            System.out.println("Находим id сотрудника с именем Ann:");
            PreparedStatement stm = con.prepareStatement("Select ID from Employee Where name like ?");
            stm.setString(1, "Ann");
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                annIds.add(rs.getInt("ID"));
            }
            if (annIds.size() == 1) {
                int annId = annIds.getFirst();
                System.out.println("Найден один сотрудник с именем Ann, его id = " + annId);

                PreparedStatement updateDep = con.prepareStatement("UPDATE Employee SET DepartmentID = 3 WHERE id = ?");
                updateDep.setInt(1, annId);
                System.out.println("Департамент установлен в HR");
            } else {
                System.out.println("Департамент не изменен, так как сотрудников с именем Ann не найдено или >1");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void checkNames() {
        int updatedNamesCount = 0;

        try (Connection con = DriverManager.getConnection("jdbc:h2:~\\Office")) {
            Service.createDB();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID, NAME FROM Employee");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String currentName = rs.getString("NAME");

                if (Character.isLowerCase(currentName.charAt(0))) {
                    String updatedName = Character.toUpperCase(currentName.charAt(0)) + currentName.substring(1);
                    System.out.println("id " + id + ": " + currentName + " исправлено на " + updatedName);

                    PreparedStatement stm = con.prepareStatement("UPDATE Employee SET NAME = ? WHERE ID = ?");
                    stm.setString(1, updatedName);
                    stm.setInt(2, id);
                    updatedNamesCount++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Количество исправленных имён: " + updatedNamesCount);
    }

    public void countItEmployees() {
        int countItEmployee = 0;

        try (Connection con = DriverManager.getConnection("jdbc:h2:~\\Office")) {
            Service.createDB();
            PreparedStatement stm = con.prepareStatement("SELECT COUNT(Employee.ID) FROM Employee JOIN Department ON Employee.DepartmentID = Department.ID WHERE Department.NAME = ?");
            stm.setString(1, "IT");
            ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                 countItEmployee = rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Количество сотрудников в IT-отделе: " + countItEmployee);
    }

}
