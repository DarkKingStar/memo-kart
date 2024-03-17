package com.memo.kart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.kart.model.Offer;
import com.memo.kart.repository.OfferRepository;

@Service
public class OfferService {
    @Autowired
    OfferRepository offerRepository;

    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public Optional<Offer> getOffersbyId(int id) {
        return offerRepository.findById(id);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public void deleteOffer(int id) {
        offerRepository.deleteById(id);
    }
}
