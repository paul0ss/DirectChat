package com.example.chatwifi_direct.chatMemory;

import java.util.Set;

public interface Chats {

    /**
     * Speichert eine Nachricht ab(im Chatspeicher und im File Teilnehmer@Chat.txt)
     * @param sender - Sender der Nachricht
     * @param message - Text der Nachricht
     * @param members - eindeutige identifizierung von Chat (die Teilnehmer eines Chats)
     */
    public void save(String sender, String message, Set<String> members);

    /**
     * Löscht die Nachricht(vom Chatspeicher und aus dem File Teilnehmer@Chat.txt)
     * @param sender - Sender der Nachricht
     * @param message - Text der Nachricht
     * @param members - eindeutige identifizierung von Chat (die Teilnehmer eines Chats)
     */
    public void delete(String sender, String message, Set<String> members);

    /**
     * Prüft ob ein Chat mit den Teilnehmern existiert. Wenn nicht, wird eine Instanz von Chat erzeugt
     * und dazugehäriget File, wo der Chatlog abgespeichert wird
     * @param members - eindeutige identifizierung von Chat (die Teilnehmer eines Chats)
     */
    public void setChat(Set<String> members);

    /**
     * Gibt die Absender von Nachrichten in der richtigen Reihenfolge zurück, die bereits eine
     * Nachricht gesendet haben. Dazu gibt es noch eine Methode getMessages(), die dazugehörende
     * Nachrichten zurückgibt.
     * @param members - eindeutige identifizierung von Chat (die Teilnehmer eines Chats)
     * @return Sendernamen in der chronologischer Reihenfolge von verschickten Nachrichten
     */
    public String[] getSenders(Set<String> members);

    /**
     * Gibt die Nachrichten in chronologischer Reihenfolge zurück von einem Chat. Dazu gibt es
     * noch eine Methode getSenders(), die dazugehörende Absendernamen liefert.
     * @param members - eindeutige identifizierung von Chat (die Teilnehmer eines Chats)
     * @return
     */
    public String[] getMessages(Set<String> members);

    /**
     * Liefert die Teilnehmer von allen Chats im form von [Teilnehmer1, Teilnehmer2, ...].
     * Das sind dann die Überschriften von Chats
     * @return [Teilnehmer vom Chat1, Teilnehmer vom Chat2, ...]
     */
    public String[] getParticipents();

    /**
     * Liefert die letzen Nachrichten von allen Chats.
     * @return [Nachricht vom Chat1, Nachricht vom Chat2, ...]
     */
    public String[] getLastMessages();

    /**
     * Liefert die zu Chats dazugehörende Bilder, damit sie im Fenster "Chats" angezeigt werden können
     * @return [Bild vom Chat1, Bild vom Chat2, ...]
     */
    public Integer[] getPictures();

}
