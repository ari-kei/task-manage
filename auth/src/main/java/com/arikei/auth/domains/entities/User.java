package com.arikei.auth.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ユーザエンティティ
 */
@AllArgsConstructor
@Getter
public class User {
  private String userId;
  private String name;
  private String hashedPassword;
  private String role;
}
