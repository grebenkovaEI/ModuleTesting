package office;

import java.sql.*;

public class mainClass {
    public static void main(String[] args) {

        //1. Найдите ID сотрудника с именем Ann. Если такой сотрудник только один, то установите его департамент в HR.
        try (Connection con = DriverManager.getConnection("jdbc:h2:~\\Office")) {
            Service.createDB();
            System.out.println("Находим сотрудника с именем Ann:");
            PreparedStatement stm = con.prepareStatement("Select Employee.ID, Employee.NAME,Department.NAME as DepName from Employee join Department on Employee.DepartmentID=Department.ID Where Employee.name like ?");
            stm.setString(1, "Ann");
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                System.out.println(
                        rs.getInt("ID") + "\t"
                        + rs.getString("Name") + "\t"
                        + rs.getString("DepName")
                );
            }
            System.out.println("Устанавливаем департамент в HR.");
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE Employee SET DepartmentID = 3");
        } catch (Exception e) {
            System.out.println(e);
        }








    }
}
