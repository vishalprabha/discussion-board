package com.discussion.board.models;
// User details with getters and setters

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
// Model in the MVC
// Creating the table
// reference code : https://prateek-ashtikar512.medium.com/spring-boot-jpa-entity-listener-fa759e5b73a9
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 16)
    private String introduction;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "user")
    private List<Thread> threads;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional getIntroduction() {
        return Optional.ofNullable(introduction);
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String displayContentOfOptional() {
        if (Optional.ofNullable(introduction).isPresent())
            return Optional.ofNullable(introduction).get();
        else
            return "";
    }

    public String displayParsedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }
}
