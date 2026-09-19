package ge.tbc.soap.config;

import ge.tbc.soap.models.db.Employee;
import org.apache.ibatis.annotations.*;

public interface EmployeeMapper {

    @Insert("INSERT INTO employee (employee_id, name, department, phone, address, salary, email, birth_date) " +
            "VALUES (#{employeeId}, #{name}, #{department}, #{phone}, #{address}, #{salary}, #{email}, #{birthDate})")
    void insertEmployee(Employee employee);

    @Select("SELECT * FROM employee WHERE employee_id = #{id}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "birthDate", column = "birth_date")
    })
    Employee getEmployeeById(Long id);

    @Update("UPDATE employee SET department = #{department}, salary = #{salary}, email = #{email} WHERE employee_id = #{employeeId}")
    void updateEmployee(Employee employee);

    @Select("SELECT COUNT(*) FROM employee WHERE employee_id = #{id}")
    int checkEmployeeExists(Long id);
}