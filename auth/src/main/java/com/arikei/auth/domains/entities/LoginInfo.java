package com.arikei.auth.domains.entities;

import lombok.Getter;

/**
 * ログイン情報エンティティ
 */
@Getter
public class LoginInfo {
  private String userId;
  private String password;
}
