package it.almawave.serviceline.ToBeNamed.repository;

import it.almawave.serviceline.ToBeNamed.entity.CupEntity;
import it.almawave.serviceline.ToBeNamed.entity.ElencoCup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CupRepository extends MongoRepository<ElencoCup, String> {

    @Query(value = "{ 'elencoCUP': { $elemMatch: { 'codiceCUP': ?0 }}}", fields = "{ 'elencoCUP': 1 }")
    Optional<ElencoCup> findByElencoCUP(String codiceCUP);

}
