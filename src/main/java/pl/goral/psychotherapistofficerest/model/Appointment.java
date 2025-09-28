package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
    @Table(name = "appointment")

    public class Appointment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "patient_id", referencedColumnName = "id")
        private Patient patient;
        @ManyToOne
        @JoinColumn(name = "therapy_id", referencedColumnName = "id")
        private Therapy therapy;
        @OneToOne
        @JoinColumn(name = "calender_id", referencedColumnName = "id")
        private CalenderSlot calenderSlot;

    @Entity
    @Table(name = "users")
    public static class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String email;
        private String password;
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
        )
        private Set<UserRole> roles = new HashSet<>();

        public User() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public String setPassword(String password) {
            this.password = password;
            return password;
        }

        public Set<UserRole> getRoles() {
            return roles;
        }

        public void setRoles(Set<UserRole> roles) {
            this.roles = roles;
        }

    }
}
