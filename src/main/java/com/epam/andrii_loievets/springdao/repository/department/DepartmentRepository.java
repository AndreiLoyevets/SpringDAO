package com.epam.andrii_loievets.springdao.repository.department;

import com.epam.andrii_loievets.springdao.domain.Department;

/**
 *
 * @author Andrii_Loievets
 */
public interface DepartmentRepository {
    int insertDepartment(Department department);
    Department findById(int id);
    Department findByManagerId(int managerId);
    int updateDepartment(Department department);
    int deleteDepartment(Department department);
}
