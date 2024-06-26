package com.arikei.auth.drivers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.springframework.stereotype.Component;
import com.arikei.auth.config.KeyPathConfig;
import com.arikei.auth.domains.repositoryif.JwtKeyRepositoryIF;

@Component
public class JwtKeyRepositoryImpl implements JwtKeyRepositoryIF {

  public final KeyPathConfig keyPathConfig;

  public JwtKeyRepositoryImpl(KeyPathConfig keyPathConfig) {
    this.keyPathConfig = keyPathConfig;
  }

  public String getPrivateKey() {
    String privateKeyValue = "";
    try (FileInputStream fis = new FileInputStream(keyPathConfig.getPrivatekey());
        BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
      StringBuilder sb = new StringBuilder();
      String content;
      while ((content = br.readLine()) != null) {
        sb.append(content);
      }
      privateKeyValue = sb.toString();
    } catch (Exception e) {
      System.err.println(e);
    }
    return privateKeyValue;
  }

  public String getPublicKey() {
    String publicKeyValue = "";
    try (FileInputStream fis = new FileInputStream(keyPathConfig.getPublickey());
        BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
      StringBuilder sb = new StringBuilder();
      String content;
      while ((content = br.readLine()) != null) {
        sb.append(content);
      }
      publicKeyValue = sb.toString();
    } catch (Exception e) {
      System.err.println(e);
    }
    return publicKeyValue;
  }
}
