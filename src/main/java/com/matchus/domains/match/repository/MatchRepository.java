package com.matchus.domains.match.repository;

import com.matchus.domains.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
