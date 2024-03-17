package com.memo.kart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.kart.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

}
