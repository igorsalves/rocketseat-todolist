package br.com.igorsalves.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import lombok.*;

@Data
@Entity(name = "tb_users")
public class UserModel {
  @Id
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(unique = true)
  private String username;

  private String name;

  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
