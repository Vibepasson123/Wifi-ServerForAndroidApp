/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;

import org.w3c.dom.Document;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Text;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author vivek
 */
public class Server2 {
    private static Object watchKey;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args)throws IOException,InterruptedException, ParserConfigurationException, SAXException {
        // TODO code application logic here
      Path ClientFile = Paths.get("C:\\test");
      //System.out.println(ClientFile);
   
		WatchService watchService = FileSystems.getDefault().newWatchService();
		ClientFile.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					//System.out.println("File:" + fileName);
                                         //File file = new File("fileName");//full file path URL
                                     //   String absolutePath = fileName.getAbsolutePath();
                                       
                                 BufferedReader br = new BufferedReader(new FileReader("C:\\test\\"+fileName));
                                 
                                            try {
                                          // FileWriter fw = new FileWriter(fileName);
                                           //BufferedWriter out = new BufferedWriter(fw);
                                            DocumentBuilderFactory dFactory = DocumentBuilderFactory .newInstance();
                                            DocumentBuilder documentBuilder = dFactory.newDocumentBuilder();
                                            Document document = documentBuilder.parse("C:\\test");
                                           
                                            document.getDocumentElement().normalize();
                                            NodeList nList = document.getElementsByTagName("xml");
         
                                            for (int temp = 0; temp < nList.getLength(); temp++) {
                                               Node nNode = nList.item(temp);
                                               
                                               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element eElement = (Element) nNode;
                                                    System.out.println("" + eElement.getAttribute("rollno"));
                                               }
                                            }
                                            
                                        String  customerid = document.getElementsByTagName("CustomerID").item(0).getTextContent();
                                        String customertaxid = document.getElementsByTagName("CustomerTaxID").item(0).getTextContent();
                                        String customername =document.getElementsByTagName("CustomerName").item(0).getTextContent();
                                        String customeremail = document.getElementsByTagName("CustomerEmail").item(0).getTextContent();
                                        String addressdetail = document.getElementsByTagName("Addressdetail").item(0).getTextContent();
                                        String city = document.getElementsByTagName("City").item(0).getTextContent();
                                        String postalcode = document.getElementsByTagName("PostalCode").item(0).getTextContent();
                                        String Country = document.getElementsByTagName("country").item(0).getTextContent();
                                        String phone = document.getElementsByTagName("Phone").item(0).getTextContent();
                                        String mobile = document.getElementsByTagName("mobile").item(0).getTextContent();
                                        System.out.println(customerid );
                                                } finally {  
                                                br.close();
                                                    }
                                               try{
                                          Socket s = new Socket("172.22.255.145",7891);
                                          PrintWriter ss = new PrintWriter(s.getOutputStream());
                                          ss.write("customerid");
                                          ss.flush();
                                          ss.close();
                                          s.close();
                                           }catch(IOException e)
                                            {
                                        e.printStackTrace();
    }   
                                            br.close();
                                      
				}
			}
			valid = watchKey.reset();

		} while (valid);
                
    }
   
   
}
