package com.example.chatwifi_direct.chatMemory;

public interface Contacts {
    /**
     * Speichert Kontakt ab.(sowohl im ContactsImpl als auch im File contactsFile.txt)
     * @param name - Name der Kontaktperson
     * @param mac - MAC-Adresse von Kontaktperson(eindeutige ID von Kontaktperson)
     */
    public void save(String name,  String mac);

    /**
     * Löscht Kontakt.(sowohl vom ContactsImpl, als auch aus dem File contactsFile.txt)
     * @param mac - MAC-Adresse von Kontaktperson(eindeutige ID von Kontaktperson)
     */
    public void delete(String mac);

    /**
     * Liefert alle abgespeicherte Namen von Kontaktpersonen in einem Array.
     * Es gibt noch dazugehörige Methoden getMacs() und getPictures(), die andere dazugehörige
     * Daten liefern.
     * @return [Kontaktname1, Kontaktname2, Kontaktname3, ...]
     */
    public String[] getNames();

    /**
     * Liefert alle abgespeicherte MAC-Adressen von Kontaktpersonen in einem Array.
     * Es gibt noch dazugehörige Methoden getNames() und getPictures(), die andere dazugehörige
     * Daten liefern.
     * @return [MAC-Adresse1, MAC-Adresse2, MAC-Adresse3, ...]
     */
    public String[] getMacs();

    /**
     * Liefert alle Bilder von Kontaktpersonen in einem Array.
     * Es gibt noch dazugehörige Methoden getNames() und getMacs(), die andere dazugehörige
     * Daten liefern.
     * @return [Bild1, Bild2, Bild3, ...]
     */
    public Integer[] getPictures();

    /**
     * Liefert das Bild von dem Kontakt mit der MAC-Adresse mac
     * @param mac - MAC-Adresse von Kontaktperson(eindeutige ID von Kontaktperson)
     * @return dazugehöriges BildID
     */
    public Integer getPicture(String mac);

    /**
     * wenn die Kontaktperson mit so eine MAC-Adresse noch nicht im System vorhanden ist,
     * wird ein Objekt der Klasse Contact erzeugt
     * @param mac
     */
    //public void setUpContact(String mac);
}
