package com.myexaminer.repository;

import com.myexaminer.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    Notebook findByAccountEmail(String email);
}
