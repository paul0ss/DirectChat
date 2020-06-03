package com.example.chatwifi_direct.chatMemory;

import java.io.File;

public interface MemoryManagement<T> {

    /**
     * speichert das Objekt sowohl in ein Collection, als auch in Dateisystem
     * @param t - Entweder Contact oder Chat
     */
    void save(T t);

    /**
     * löscht ein Objekt sowohl aus Collection, als auch aus Dateisystem
     * @param t
     */
    void delete(T t);
}
