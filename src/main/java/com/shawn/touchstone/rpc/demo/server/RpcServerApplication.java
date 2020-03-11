package com.shawn.touchstone.rpc.demo.server;

import com.shawn.touchstone.rpc.RpcServer;
import java.io.IOException;

public class RpcServerApplication {

  public static void main(String[] args) throws IOException {
    CalculatorService service = new CalculatorServiceImpl();
    RpcServer server = new RpcServer();
    server.export(service, 1234);
  }
}
