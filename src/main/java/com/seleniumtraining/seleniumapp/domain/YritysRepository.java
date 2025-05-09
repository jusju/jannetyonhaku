package com.seleniumtraining.seleniumapp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface YritysRepository extends JpaRepository<Yritys, Long> {

    Page<Yritys> findByUsername(String username, Pageable pageable);
}
