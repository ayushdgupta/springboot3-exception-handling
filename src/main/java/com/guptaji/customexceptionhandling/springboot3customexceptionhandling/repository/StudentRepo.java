package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.repository;

import com.guptaji.customexceptionhandling.springboot3customexceptionhandling.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {}
