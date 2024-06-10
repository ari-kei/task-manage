package com.arikei.auth.domains.entities;

import lombok.Getter;

/**
 * ユーザエンティティ
 */
@Getter
public class User {
  private String userId;
  private String name;
  private String hashedPassword;
  private String role;
}
