package src;

import src.product.Product;
import src.udp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Connection manager.
 */
public class ConnectionManager {
    private Client client;


    /**
     * Instantiates a new Connection manager.
     *
     * @throws IOException the io exception
     */
    public ConnectionManager() throws IOException {
        client = new Client();
    }

    /**
     * Send server command.
     *
     * @param command the command
     * @return the server command
     */
    public ServerCommand send(ServerCommand command) {
        try {
            byte[] response = client.sendMsg(Utils.serializeObject(command));
            if (response == null)
                return new ServerCommand(
                        ServerCommandType.ERROR,
                        Utils.serializeObject("Произошла ошибка во время получения пакетов с сервера."));
            return (ServerCommand) Utils.deserializeObject(response);
        } catch (IOException e) {
            return new ServerCommand(
                    ServerCommandType.ERROR,
                    Utils.serializeObject("Превышено время ожидания ответа от сервера."));
        }
    }

    /**
     * Clear.
     *
     * @return the boolean value of successful execution
     */
    public boolean clear() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.CLEAR, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Group counting by price array list.
     *
     * @return the array list
     */
    public ArrayList<String> groupCountingByPrice() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GROUP_COUNTING_BY_PRICE, null));
        if(response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось получить информацию.");
        return (ArrayList<String>) Utils.deserializeObject(response.data);
    }

    /**
     * Info array list.
     *
     * @return the array list with text result
     */
    public ArrayList<String> info() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.INFO, null));
        if(response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось получить информацию.");
        return (ArrayList<String>) Utils.deserializeObject(response.data);
    }

    /**
     * Insert.
     *
     * @param element the element
     * @return the boolean value of successful execution
     */
    public boolean insert(Element element) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.INSERT, Utils.serializeObject(element)));
        if(response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Min by name.
     *
     * @return the product
     */
    public Product minByName() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.MIN_BY_NAME, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return (Product) Utils.deserializeObject(response.data);
    }

    /**
     * Print unique manufacture cost array list from server.
     *
     * @return the array list with text result
     */
    public ArrayList<String> printUniqueManufactureCost() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.PRINT_UNIQUE_MANUFACTURE_COST, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось получить информацию.");
        return (ArrayList<String>) Utils.deserializeObject(response.data);
    }

    /**
     * Remove key boolean from server.
     *
     * @param key the key
     * @return the boolean value of successful execution
     */
    public boolean removeKey(Long key) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.REMOVE_KEY, Utils.serializeObject(key)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Remove lower boolean from server.
     *
     * @param id the id
     * @return the boolean value of successful execution
     */
    public boolean removeLower(Long id) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.REMOVE_LOWER, Utils.serializeObject(id)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Remove lower key boolean from server.
     *
     * @param key the key
     * @return the boolean value of successful execution
     */
    public boolean removeLowerKey(Long key) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.REMOVE_LOWER_KEY, Utils.serializeObject(key)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Replace if lowe.
     *
     * @param element the element
     * @return the boolean value of successful execution
     */
    public boolean replaceIfLowe(Element element) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.REPLACE_IF_LOWE, Utils.serializeObject(element)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Show array list from server.
     *
     * @return the array list with text result
     */
    public ArrayList<String> show() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.SHOW, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return (ArrayList<String>) Utils.deserializeObject(response.data);
    }

    /**
     * Update boolean from server..
     *
     * @param element the element
     * @return the boolean value of successful execution
     */
    public boolean update(Element element) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.UPDATE, Utils.serializeObject(element)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    }

    /**
     * Get product from server.
     *
     * @param id the id
     * @return the product
     */
    public Product get(Long id) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GET, Utils.serializeObject(id)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return (Product) Utils.deserializeObject(response.data);
    }

    /**
     * Gets by key from server.
     *
     * @param key the key
     * @return the by key
     */
    public Product getByKey(Long key) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GET_BY_KEY, Utils.serializeObject(key)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String)Utils.deserializeObject(response.data));
        return (Product) Utils.deserializeObject(response.data);
    }

    /**
     * Gets unique id from server.
     *
     * @return the unique id
     */
    public Long getUniqueID() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GET_UNIQUE_ID, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось получить информацию об уникальном id.");
        return (Long) Utils.deserializeObject(response.data);
    }
}
