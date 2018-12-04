@SuppressWarnings("unused")
public class Viewer {

    private String name, location, credentials;
    private int age, publications, sightings;

    Viewer(String name, String location, String credentials, int age, int publications, int sightings) {
        this.name = name;
        this.location = location;
        this.credentials = credentials;
        this.age = age;
        this.publications = publications;
        this.sightings = sightings;
    }

    public Viewer() {

    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    int getPublications() {
        return publications;
    }

    public void setPublications(int publications) {
        this.publications = publications;
    }

    int getSightings() {
        return sightings;
    }

    public void setSightings(int sightings) {
        this.sightings = sightings;
    }
}
