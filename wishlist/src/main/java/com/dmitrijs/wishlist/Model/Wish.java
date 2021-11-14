package com.dmitrijs.wishlist.Model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "wish")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Wish implements Serializable {

    private static final long serialVersionUID = 4048798961366546485L;

    @Id
    @Column(name="id", nullable = false)
    @SequenceGenerator(name = "wish_seq", sequenceName = "wish_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wish_seq")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="matter", nullable = false)
    private String matter;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="insert_date")
    private Date insertDate;

    public boolean Valid(){
        boolean result = true;
        if (matter == null) result = false;
        else if (matter.trim() == "") result = false;
        if (userId == null) result = false;
        else if (userId <= 0) result = false;
        return result;
    }
}
