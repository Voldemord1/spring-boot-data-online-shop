package com.example.onlineshop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "code", schema = "spring_shop")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", length = 10, nullable = false)
    private String value;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Code() {
    }

    public Code(User user) {
        this.user = user;
    }

    public void generateCode() {
        value = String.valueOf(1000 + (int) (Math.random() * 9000));
    }
}
