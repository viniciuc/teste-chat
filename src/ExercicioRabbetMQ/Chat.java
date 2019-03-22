package ExercicioRabbetMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;


/**
 *
 * @author Claudemiro, Vinicius
 * 
 */

public class Chat {

    static Scanner sc = new Scanner(System.in);
    static String destinatario="";
    static Channel channel;
    static String usuario="";
    
    static byte[] xmlByte;

    static   String msg = "";
    static String grupo ="";    
    static MontaXml xml = new MontaXml();
    
        
    //private final static String QUEUE_NAME = "vinicius";
    public static void main(String[] args) throws IOException, TimeoutException {

        System.out.print("User: ");

       usuario = sc.nextLine();

        System.out.println("Voce esta logado com: " + usuario);
//-----------------CONEXÃO COM SERVIDOR-----------------------------
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("reindeer.rmq.cloudamqp.com");
        factory.setUsername("jmodzuaw");
        factory.setPassword("Kwuy7kd81ED1fIj9gxEti1J4FTPBj2Jz");
        factory.setVirtualHost("jmodzuaw");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        //-----------------------------RECEIVE------------------------------
        channel.queueDeclare(usuario, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                
                try {
                    xml.exibeMensagem(body);
                } catch (JDOMException ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                }
//                
//                String message = new String(body, "UTF-8");
//                System.out.println();
//                System.out.println(message);
//                
            if (!grupo.equals("")){ 
               System.out.print(grupo + " (grupo)>");
            }else if (!destinatario.equals("")){ 
               System.out.print(destinatario+">");
           }else {
               System.out.print(">");
           }

                

            }
        };
        channel.basicConsume(usuario, true, consumer);

        //-------------------------SEND-----------------------------
        chat();

//           StringTokenizer msgformat = new StringTokenizer(msg, "@");
//           String destinatario = msgformat.nextToken();
//           String msg2 = msgformat.nextToken();
        channel.close();

        connection.close();

    }

    private static void chat() throws IOException {

      if (msg.trim().equalsIgnoreCase("#sair")){
          System.exit(0);
      }
       
        if(msg.trim().equals("") || msg.trim().charAt(0) == '@' || msg.trim().charAt(0) == '!' ){
           
            if (!grupo.equals("")){ 
               System.out.print(grupo + "(grupo)>");
            }else if (!destinatario.equals("")){ 
               System.out.print(destinatario+">");
           }else {
               System.out.print(">");
           }
            msg = sc.nextLine();
        }
//           StringTokenizer msgformat = new StringTokenizer(msg, "@");
//           String destinatario = msgformat.nextToken();
//           String msg2 = msgformat.nextToken();
        do {

            

            //Esse if se faz necessário porque sem ele, ao tentar acessar o charAt(1) em um comando que só tenha o @ ele dá erro.
            if (msg.length() == 0 || msg.length() == 1) {
    
                msg += " ";

            }

            //Verifica se o comando é de entrar em mensagens do grupo
            if (msg.charAt(0) == '@' && msg.charAt(1) == '@') {

                //identifica o grupo que deseja enviar a mensagem
                grupo = msg.substring(2, msg.length()).trim();

                //verifica se foi informado o nome do grupo no comando
                if (grupo.equals("")) {

                    System.out.println("Necessário informar o grupo");

                    
                    
                } else {

//                   System.out.print(grupo + " (grupo)>");
                   
                   chat();
                }
                
//verifica se o comando é de enviar mensagem para um usuário
            } else if (msg.charAt(0) == '@') {

                //identifica quem é o destinatário
                destinatario = msg.substring(1, msg.length()).trim();

                //verifica se o destinatário foi informado
                if (destinatario.equals("")) {

                    System.out.println("Necessário informar o destinatário");

                } else {
                    
                    grupo = "";

//                    System.out.print(destinatario + ">");
//
//                   
//                        msg = sc.nextLine();
                        chat();

                       
                    

                }

                //verifica se o comando começa com !
            } else if (msg.charAt(0) == '!') {
                
                msg += "              ";

                //fechar canal + criar grupo
                //verifica se o comando associado é o de add
                if ((msg.substring(1, 7)).equals("group+")) {
                    
                    //verificar se tem o usuário e grupo
                    //Verifica se foi digitado o nome do grupo após o comando, o nome do grupo tem que ter pelo menos 1 caracter                  
                    String[] comando = msg.split(" ");

                    //comando[0] = "!add"
                    //comando[1] = "usuario"
                    //comando[2] + comando[3] + comando[n] = "nome do grupo"
                    //verifica se foi passado o comando, usuário e nome do grupo no comando
                    if (comando.length >= 3) {

                        //adicionar o usuario ao grupo
                        
                        
                        
                        
                        
                        
                        //
                        
//                                                
//                        
//                                xmlByte = null;
//                                try {
//                                    xmlByte = xml.montaXml(comando[2]+"/"+comando[1], ("O usuário " + comando[1] + " foi adicionado ao grupo " + comando[2]));
//                                } catch (URISyntaxException ex) {
//                                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
//                                } catch (JDOMException ex) {
//                                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//
//        //                        channel.basicPublish("", destinatario, null, message.getBytes("UTF-8"));
//                                channel.basicPublish(comando[2],"", null, xmlByte);
//
//                        
//                        //
//                        
                        
                        
                        
                        
                        
                        
                        channel.queueBind(comando[1],comando[2], "");
                        System.out.println("O usuário " + comando[1] + " foi adicionado ao grupo " +comando[2]+".");
//                        
//                        
//                        
//                        xmlByte = null;
//                                try {
//                                    xmlByte = xml.montaXml(comando[2]+"/"+comando[1], ("O usuário " + comando[1] + " foi adicionado ao grupo " + comando[2]));
//                                } catch (URISyntaxException ex) {
//                                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
//                                } catch (JDOMException ex) {
//                                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//
//                                                
//                        channel.basicPublish("", comando[1], null, xml);

                        
//                        System.out.println("O usuário " + comando[1] + " foi adicionado ao grupo " + comando[2]);
                       
                        //se houver usuário no canal, fechar.
                        System.out.print(">");

                     } else {
                        
                        System.out.println("O comando deve seguir a seguinte sintaxe: !group+ usuario nome do grupo");
                    }
                    msg="";
                    //verifica se o comando é de criar um grupo
                } else if ((msg.substring(1, 12)).equals("creategroup")) {

                    //Verifica se foi digitado o nome do grupo após o comando, o nome do grupo tem que ter pelo menos 1 caracter
                    //verificar se tem o nome do grupo
                    String nomeGrupo = msg.substring(12, msg.length()).trim();
                    if (nomeGrupo.length() >= 1) {
                        
                        try{
                        channel.exchangeDeclare(nomeGrupo,"fanout", true);
                        channel.queueBind(usuario, nomeGrupo, "");                       
                        System.out.println("Grupo " + nomeGrupo + " criado com sucesso.");
                        }catch(Exception e){
                        
                            System.out.println("Erro ao tentar criar o grupo.");
                            
                        }

                        grupo = "";
                        
                        //Efetuar verificação de grupo já existente;                        
                        //Criar canal do grupo
                        //Adicionar o usuário criador ao grupo
                        System.out.print(">");
                        
                        

                    } else {
                           
                        System.out.println("O nome do grupo não foi informado.");
                        
                        grupo = "";
                    }
                    
                    
                    msg="";
                }else if ((msg.substring(1, 7)).equals("group-")) {
                                        
                    //Verifica se foi digitado o nome do grupo após o comando, o nome do grupo tem que ter pelo menos 1 caracter                  
                    String[] comando = msg.split(" ");

                    //comando[0] = "!add"
                    //comando[1] = "usuario"
                    //comando[2] + comando[3] + comando[n] = "nome do grupo"
                    //verifica se foi passado o comando, usuário e nome do grupo no comando
                    if (comando.length >= 3) {

                        //remover o usuario do grupo
                        
//                        channel.basicPublish("", comando[1], null, ("O usuário "+usuario+" removeu você do grupo "+comando[2]+".").getBytes("UTF-8"));
                        
                        channel.queueUnbind(comando[1],comando[2], "");
                        
                        System.out.println("O usuário " + comando[1] + " foi removido do grupo com sucesso.");

//                        channel.basicPublish(comando[2],"", null, ("O usuário " + comando[1] + " foi removido do grupo " + comando[2]).getBytes("UTF-8"));                        
                        
                        

                        
//                        System.out.println("O usuário " + comando[1] + " foi adicionado ao grupo " + comando[2]);
                                               
                        System.out.print(">");

                     } else {
                        
                        System.out.println("O comando deve seguir a seguinte sintaxe: !remove usuario nome_do_grupo");
                    }
                    msg="";
                    //verifica se o comando é de criar um grupo
                }else if ((msg.substring(1, 12)).equals("removegroup")) {
                                        
                    
                    //Verifica se foi digitado o nome do grupo após o comando, o nome do grupo tem que ter pelo menos 1 caracter
                    //verificar se tem o nome do grupo
                    String nomeGrupo = msg.substring(12, msg.length()).trim();
                    if (nomeGrupo.length() >= 1) {
                        
                                                
//                        channel.basicPublish(nomeGrupo,"", null, ("O grupo " + nomeGrupo + " foi excluido.").getBytes("UTF-8"));                        
                        //Exclui o canal do grupo
                        channel.exchangeDelete(nomeGrupo);                        
                                                                        
                        System.out.println("Grupo " + nomeGrupo + " foi excluido com sucesso.");
                        

                        
                        grupo = "";
                                                
                        System.out.print(">");
                        
                        

                    } else {
                           
                        System.out.println("O nome do grupo não foi informado.");
                        
                        grupo = "";
                    }
                    
                    
                    msg="";
                    
                    
                    
                    
                }else{
                
                    
                    System.out.println("Comando não encontrado");
                    System.out.print(">");
                
                }

//                destinatario = msg.substring(1, msg.length());                
            } else {
                
                String message ="";
                
                if (!grupo.equals("")) {
                    if (msg.trim().equalsIgnoreCase("#sair")){
                        System.exit(0);
                     }
                    
                    
                    if (!msg.equals("")) {
                    
                        message = msg;

                        
                        xmlByte = null;
                        try {
                            xmlByte = xml.montaXml(grupo+"/"+usuario, msg);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JDOMException ex) {
                            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        
                        try {
                            
                            channel.basicPublish(grupo,"", null, xmlByte);
                        } catch (Exception e) {
                            System.out.println("Este grupo foi excluido ou não existe");
                        }

                        

                    }
                msg = "";
                
                chat();

                }else
                
                if (!destinatario.equals("")) {
                    if (msg.trim().equalsIgnoreCase("#sair")){
                        System.exit(0);
                     }
                    
                    if (!msg.equals("")) {
                    
                        message = msg;
                        
                        xmlByte = null;
                        try {
                            xmlByte = xml.montaXml(usuario, message);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JDOMException ex) {
                            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
//                        channel.basicPublish("", destinatario, null, message.getBytes("UTF-8"));

    try{
                        channel.basicPublish("", destinatario, null, xmlByte);
    }catch(Exception e){
    
        System.out.println("Erro ao enviar a mensagem. Tente novamente.");
        
    }
                        

                        
                    }
                    
                msg = "";
                chat();

                }else{
                
                    System.out.print(">");
                
                }
            
            }
            
            msg = sc.nextLine();

        } while (!msg.equalsIgnoreCase("#sair"));

    }
}
