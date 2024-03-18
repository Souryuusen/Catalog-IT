package com.soursoft.catalogit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

@Entity
@Table(name = "user_watchlist")
public class WatchlistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wid")
    private Long watchlistId;

    @ManyToOne
    @JoinColumn(name = "w_owner_id", referencedColumnName = "uid")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "w_movie_id", referencedColumnName = "movie_id")
    private Movie reviewedEntity;

    @Column(name = "w_movie_rating")
    @Range(min = 0, max = 100, message = "Rating should have value from range 0 - 100.")
    private Integer rating;
    @Column(name = "w_finished")
    private boolean finished;
    @OneToMany(mappedBy = "reviewId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Review> reviews;

}
