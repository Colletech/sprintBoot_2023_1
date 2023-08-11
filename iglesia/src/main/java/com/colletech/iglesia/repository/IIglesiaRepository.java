package com.colletech.iglesia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colletech.iglesia.dao.Church;

@Repository
public interface IIglesiaRepository extends JpaRepository<Church, Long> {

}
