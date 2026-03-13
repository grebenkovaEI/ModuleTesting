package office;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.*;

@Slf4j
class TestOffice {

    @Test
    void testDeleteDepartment() {
        int departmentId = 1;
        Service.createDB();

        try (Connection con = DriverManager.getConnection("jdbc:h2:~\\Office")) {
            // кол-во сотрудников перед удалением
            int count = 0;
            try (PreparedStatement stm = con.prepareStatement("SELECT COUNT(*) FROM Employee WHERE DepartmentID = ?")) {
                stm.setInt(1, departmentId);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            System.out.println("Кол-во сотрудников в отделе 1 перед удалением: " + count);

            // удаляем отдел
            try (PreparedStatement pstm = con.prepareStatement("DELETE FROM Department WHERE ID = ?")) {
                pstm.setInt(1, departmentId);
                pstm.executeUpdate();
            }
//            System.out.println("отделы до удаления");
//            Statement stm1 = con.createStatement();
//            ResultSet rs1= stm1.executeQuery("Select ID, NAME as txt from Department");
//            while(rs1.next()){
//                System.out.println(rs1.getInt("ID")+"\t"+rs1.getString("name"));
//            }

            System.out.println("удаляем отдел");
            Service.removeDepartment(new Department(departmentId,""));
            System.out.println("отдел успешно удален");

//            System.out.println("отделы после удаления");
//            Statement stm2 = con.createStatement();
//            ResultSet rs2= stm2.executeQuery("Select ID, NAME as txt from Department");
//            while(rs2.next()){
//                System.out.println(rs2.getInt("ID")+"\t"+rs2.getString("name"));
//            }
//            System.out.println("вывести всех сотрудников после удаления");
//            Statement stm_ = con.createStatement();
//            ResultSet rs_= stm_.executeQuery("Select Employee.ID, Employee.Name,Department.Name as DepName from Employee join Department on Employee.DepartmentID=Department.ID");
//            ResultSetMetaData metaData= rs_.getMetaData();
//            while(rs_.next()){
//                System.out.println(rs_.getInt("ID")+"\t"+rs_.getString("NAME")+"\t"+rs_.getString("DepName"));
//            }
//            System.out.println("вывести сотрудников из отдела 1");
//            PreparedStatement prstm1 = con.prepareStatement("Select Employee.ID, Employee.Name,DepartmentID from Employee WHERE DepartmentID = ?");
//            prstm1.setInt(1, departmentId);
//            ResultSet res1 = prstm1.executeQuery();
//            while (res1.next()) {
//                System.out.println(res1.getInt("ID")+"\t"+res1.getString("NAME")+"\t"+res1.getString("DepartmentID"));
//            }



            // кол-во сотрудников после удаления
            int countEmp = -1;
            try (Connection con1 = DriverManager.getConnection("jdbc:h2:~\\Office")) {
                try (PreparedStatement prstm = con1.prepareStatement("SELECT COUNT(*) FROM Employee WHERE DepartmentID = ?")) {
                    prstm.setInt(1, departmentId);
                    ResultSet res = prstm.executeQuery();
                    if (res.next()) {
                        countEmp = res.getInt(1);
                    }
                }
            }
            Assertions.assertEquals(0, countEmp, "Сотрудники не были удалены. Требование не выполнено");
            System.out.println("Сотрудники успешно удалены");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
