import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * ネットワークインターフェースの取得
 */
public class InetAddressesInfo {
  private HashMap<NetworkInterface, ArrayList<InetAddress>> interfaceMap;

  public InetAddressesInfo() {
    interfaceMap = new HashMap<NetworkInterface, ArrayList<InetAddress>>();
  }

  public void getInterfaces() {
    interfaceMap.clear();
    try {
      Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
      if (interfaceList == null) {
        System.out.println("Message: No interfaces found");
      } else {
        while (interfaceList.hasMoreElements()) {
          NetworkInterface iface = interfaceList.nextElement();
          Enumeration<InetAddress> addrList = iface.getInetAddresses();
          if (!addrList.hasMoreElements()) continue;
          ArrayList<InetAddress> iaddress = new ArrayList<InetAddress>();
          while (addrList.hasMoreElements())
            iaddress.add(addrList.nextElement());
          interfaceMap.put(iface, iaddress);
        }
      }
    } catch (SocketException se) {
      System.out.println("Error getting network interfaces: " + se.getMessage());
    }
  }

  public void show() {
    for (NetworkInterface n : interfaceMap.keySet()) {
      System.out.println("Interface " + n.getName() + ": ");
      for (InetAddress a : interfaceMap.get(n)) {
        System.out.print("\tAddress " + ((a instanceof Inet4Address ? "(IPv4)"
            : (a instanceof Inet6Address ? "(IPv6)" : "(?)"))));
        System.out.println(": " + a.getHostAddress());
      }
    }
  }

  public HashMap<NetworkInterface, ArrayList<InetAddress>> getInterfaceMap() {
    return interfaceMap;
  }

  public void setInterfaceMap(
      HashMap<NetworkInterface, ArrayList<InetAddress>> interfaceMap) {
    this.interfaceMap = interfaceMap;
  }

  public static void main(String[] args) {
    InetAddressesInfo i =  new InetAddressesInfo();
    i.getInterfaces();
    i.show();
  }

}