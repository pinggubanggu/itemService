package hello.itemservice2.web.typeconverter;

import lombok.Getter;

@Getter
public class IpPort {
  private String ip;
  private int port;

  public IpPort(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }
}
