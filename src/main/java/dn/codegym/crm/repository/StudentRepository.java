package dn.codegym.crm.repository;

import dn.codegym.crm.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface StudentRepository extends JpaRepository<Student,String> {
    Page<Student> findAllByDeletedIsFalse(Pageable pageable);
    Page<Student> findAllByNameContaining(String name,Pageable pageable);
}