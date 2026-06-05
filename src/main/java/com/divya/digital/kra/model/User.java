package com.divya.digital.kra.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails{
	private static final long serialVersionUID = 2179458220014395194L;


	@Id
	@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "user_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_seq")
	@Column(name ="user_id", nullable = false,updatable = false)
	private Long id;
    
    
    @Size(max = 50)
	@Column(length = 50)
	private String name;
    
    
    @Size(max = 50)
	@Column(length = 50)
	private String lastname;
    
    
    @Email
	@NotBlank(message = "Email is mandatory")
	@Size(max = 100)
	@Column(unique = true, nullable = false, length = 100)
	private String email;
    
    
    private String password;

    @Enumerated(EnumType.STRING)
    private  Role role;
    
    @Size(max = 10)
	@Column(unique = true,  length = 10)
    private String phonenumber;
    
	private boolean isActive;
	
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	

    public User() {

    }

    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
//        return this.name + " " + this.lastname;
    	return this.email;
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

	public User(Long id, @Size(max = 50) String name, @Size(max = 50) String lastname,
			@Email @NotBlank(message = "Email is mandatory") @Size(max = 100) String email, String password, Role role,
			@Size(max = 10) String phonenumber, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phonenumber = phonenumber;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	

}
