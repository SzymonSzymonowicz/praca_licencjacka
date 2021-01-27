package com.myexaminer.repository;

import com.myexaminer.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {
    Notebook findByAccountEmail(String email);
}
