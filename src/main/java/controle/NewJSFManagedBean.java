/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import br.com.ligueTelecom.Bean.Cliente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import org.primefaces.json.JSONException;

/**
 *
 * @author popovicz
 */
@ManagedBean
@ViewScoped
public class NewJSFManagedBean {

    private Cliente cliente;
    private ArrayList<Cliente> clientes;
    //pega o Json de outra aplicação
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target("http://localhost:8081/ServidorTesteLigueTelecom");
    WebTarget resourceWebTarget = webTarget.path("rest");
    WebTarget pathdWebTarget = resourceWebTarget.path("cliente");
    //WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("id", 1);
    Invocation.Builder invocationBuilder = pathdWebTarget.request(MediaType.APPLICATION_JSON_TYPE);
    Response response = invocationBuilder.get();
    ObjectMapper mapper = new ObjectMapper();

    public NewJSFManagedBean() throws JSONException, MalformedURLException, IOException {
        cliente = new Cliente();
        clientes = new ArrayList<Cliente>();
    }

    public void consultar() {
        System.out.println(response.getStatus());
        cliente = response.readEntity(Cliente.class);
        System.out.println(cliente.getName());
        System.out.println(cliente.getEmail());
        System.out.println(cliente.getRg());
        System.out.println(cliente.getCpf());

    }

    public void listar() {
        clientes.addAll(response.readEntity(new GenericType<List<Cliente>>() {}));
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void alterar(Cliente c) {
        System.out.println(cliente.getName());
        cliente = c;
    }
    public void update() {        
        System.out.println(cliente.getName());
        Client client = ClientBuilder.newClient();
        Invocation.Builder updatadeInvocationBuilder = pathdWebTarget.request();
        Response putResponse = updatadeInvocationBuilder.put(Entity.entity(cliente, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(putResponse.getStatus());
        System.out.println(putResponse.readEntity(Cliente.class));
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void inserirCliente() {
        clientes.add(cliente);
        cliente = new Cliente();
    }

    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
