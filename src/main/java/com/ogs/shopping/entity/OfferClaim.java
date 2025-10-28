package com.ogs.shopping.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;

@Entity
@Table(name="offerClaims", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "offer_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerClaimId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId",nullable = false)
    private User user;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="offerId", nullable = false)
    private Offer offer;

    @Column(nullable = false)
    private LocalDate claimedAt;

    @Column(nullable = false)
    private boolean successful;

}
